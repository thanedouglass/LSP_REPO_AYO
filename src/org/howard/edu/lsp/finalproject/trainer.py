#!/usr/bin/env python

"""
trainer.py: A production-ready script to train a 5-stage sleep classification
model from raw EDF files for a Vertex AI Custom Training Job.

This script implements the goal from the project proposal:
- Reads raw EDF files using MNE
- Parses sleep stage annotations
- Creates 30-second epochs
- Trains a multi-class deep learning model
- V2: Includes class weighting to handle imbalanced data
"""

import os
import argparse
import glob
import numpy as np
import mne
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import (
    Conv1D,
    MaxPooling1D,
    Flatten,
    Dense,
    Dropout,
    BatchNormalization,
    LSTM
)
from tensorflow.keras.optimizers import Adam
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import classification_report, cohen_kappa_score, accuracy_score
from tensorflow.keras.utils import to_categorical
from sklearn.utils.class_weight import compute_class_weight

# Suppress MNE logging noise
mne.set_log_level('WARNING')

# Define the sleep stage mapping as per the project proposal
# This maps annotation descriptions to integer labels
SLEEP_STAGE_MAP = {
    "Wake": 0,
    "N1": 1,
    "N2": 2,
    "N3": 3,
    "REM": 4
}
# We will ignore 'Sleep stage ?' and 'Movement time'
ANNOTATION_MAP = {
    'Sleep stage W': 'Wake',
    'Sleep stage 1': 'N1',
    'Sleep stage 2': 'N2',
    'Sleep stage 3': 'N3',
    'Sleep stage 4': 'N3', # Map N4 to N3 (common practice)
    'Sleep stage R': 'REM',
}
SAMPLING_RATE = 100  # Hz
EPOCH_DURATION = 30 # seconds
N_SAMPLES = SAMPLING_RATE * EPOCH_DURATION

def load_and_preprocess_data(data_path):
    """
    Loads and preprocesses data from the provided directory path.
    This function reads all .edf files, extracts epochs, and returns
    X, y arrays suitable for training.
    """
    print(f"--- Loading data from {data_path} ---")
    
    # Find all EDF files in the data path
    psg_files = sorted(tf.io.gfile.glob(os.path.join(data_path, "SC*PSG.edf")))
    ann_files = sorted(tf.io.gfile.glob(os.path.join(data_path, "SC*Hypnogram.edf")))

    if not psg_files or not ann_files:
        raise FileNotFoundError(f"No EDF files found in {data_path}. "
                                "Expected SC*PSG.edf and SC*Hypnogram.edf files.")
    if len(psg_files) != len(ann_files):
        print(f"Warning: Mismatch in PSG ({len(psg_files)}) and "
              f"Annotation ({len(ann_files)}) file counts.")

    all_epochs_data = []
    all_epochs_labels = []

    # Iterate over each subject's files
    for psg_file, ann_file in zip(psg_files, ann_files):
        print(f"Processing: {os.path.basename(psg_file)}")
        try:
            # 1. Load raw PSG data and resample
            raw = mne.io.read_raw_edf(psg_file, preload=True, verbose=False)
            raw.resample(SAMPLING_RATE, npad='auto')
            
            # Select a single, common EEG channel.
            raw.pick_channels(['EEG Fpz-Cz']) 

            # 2. Load annotations
            annot = mne.read_annotations(ann_file)
            
            # Map annotations to our classes and set them on the raw object
            # We do this manually to be more robust than annot.map()
            new_onset = []
            new_duration = []
            new_description = []
            
            for i, desc in enumerate(annot.description):
                if desc in ANNOTATION_MAP:
                    new_onset.append(annot.onset[i])
                    new_duration.append(annot.duration[i])
                    new_description.append(ANNOTATION_MAP[desc])

            # Create new, cleaned annotations
            new_annot = mne.Annotations(onset=new_onset,
                                        duration=new_duration,
                                        description=new_description,
                                        orig_time=annot.orig_time)
            
            raw.set_annotations(new_annot) # Removed emit_events=True

            # 3. Create events from annotations
            events, event_id = mne.events_from_annotations(
                raw, event_id=SLEEP_STAGE_MAP, chunk_duration=EPOCH_DURATION
            )

            # 4. Create fixed-length 30s epochs
            epochs = mne.Epochs(
                raw=raw,
                events=events,
                event_id=event_id,
                tmin=0.,
                tmax=EPOCH_DURATION - 1/SAMPLING_RATE, # 30s duration
                baseline=None,
                preload=True
            )
            
            all_epochs_data.append(epochs.get_data(picks='eeg'))
            all_epochs_labels.append(epochs.events[:, -1])

        except Exception as e:
            print(f"Error processing {psg_file}: {e}")
            continue

    # 5. Combine data from all files
    X = np.concatenate(all_epochs_data, axis=0)
    y = np.concatenate(all_epochs_labels, axis=0)

    # 6. Scale features
    scaler = StandardScaler()
    X_reshaped = X.reshape(-1, X.shape[-1])
    X_scaled = scaler.fit_transform(X_reshaped)
    X_scaled = X_scaled.reshape(X.shape)
    
    # Transpose to (n_epochs, n_samples, n_channels)
    X_final = X_scaled.transpose(0, 2, 1) # Shape: (n_epochs, 3000, 1)

    print(f"--- Data loaded. X shape: {X_final.shape}, y shape: {y.shape} ---")

    # 7. Split data
    X_train, X_test, y_train, y_test = train_test_split(
        X_final, y, test_size=0.2, stratify=y, random_state=42
    )
    
    return X_train, X_test, y_train, y_test


def build_model(input_shape=(N_SAMPLES, 1)):
    """
    Builds a 1D-CNN + LSTM hybrid model suitable for raw EEG signals.
    """
    print(f"--- Building model with input shape {input_shape} ---")
    
    model = Sequential([
        Conv1D(filters=64, kernel_size=5, activation='relu', padding='same',
               input_shape=input_shape),
        BatchNormalization(),
        MaxPooling1D(pool_size=2),
        
        Conv1D(filters=128, kernel_size=3, activation='relu', padding='same'),
        BatchNormalization(),
        MaxPooling1D(pool_size=2),
        Dropout(0.3),
        
        LSTM(units=128, return_sequences=True),
        LSTM(units=64),
        Dropout(0.5),

        Dense(units=128, activation='relu'),
        Dense(5, activation='softmax') # 5 classes
    ])

    # Compile the model
    model.compile(
        optimizer=Adam(learning_rate=0.0001),
        loss='sparse_categorical_crossentropy', # For integer labels
        metrics=['accuracy']
    )
    
    model.summary()
    return model

def main(args):
    """
    Main training and evaluation routine.
    """
    # 1. Load Data
    X_train, X_test, y_train, y_test = load_and_preprocess_data(args.data_path)
    
    input_shape = (X_train.shape[1], X_train.shape[2])

    # 2. Build Model
    model = build_model(input_shape)

    # 3. NEW: Calculate Class Weights to handle imbalance
    print("\n--- Calculating class weights to handle imbalance ---")
    class_labels = np.unique(y_train)
    weights = compute_class_weight(
        class_weight='balanced',
        classes=class_labels,
        y=y_train
    )
    class_weight_dict = dict(zip(class_labels, weights))
    print(f"Class weights: {class_weight_dict}")
    print("These weights will force the model to pay more attention to rare classes (like N1 and REM).")

    # 4. Train Model
    print("\n--- Starting model training ---")
    history = model.fit(
        X_train,
        y_train,
        epochs=50,
        batch_size=64,
        validation_split=0.2,
        verbose=1,
        class_weight=class_weight_dict  # <-- NEW ARGUMENT
    )
    print("--- Model training complete ---")

    # 5. Evaluate Model
    print("\n--- Evaluating model performance ---")
    loss, accuracy = model.evaluate(X_test, y_test)
    y_pred_prob = model.predict(X_test)
    y_pred = np.argmax(y_pred_prob, axis=1)

    kappa = cohen_kappa_score(y_test, y_pred)
    
    print(f"Test Accuracy: {accuracy:.4f}")
    print(f"Test Loss: {loss:.4f}")
    print(f"Cohen's Kappa: {kappa:.4f}")
    
    print("\nClassification Report:")
    class_names = list(SLEEP_STAGE_MAP.keys())
    # Add zero_division=0 to suppress the warnings
    print(classification_report(y_test, y_pred, target_names=class_names, zero_division=0))

    # 6. Save the Model
    # We must join the path and a filename (e.g., "sleep_model.keras")
    model_save_path = os.path.join(args.model_output_dir, "sleep_model.keras")
    print(f"--- Saving trained model to {model_save_path} ---")
    model.save(model_save_path)
    print("--- Model saved successfully ---")


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '--data-path',
        type=str,
        required=True,
        help='Directory path containing the dataset EDF files (e.g., gs://my-bucket/data/)'
    )
    parser.add_argument(
        '--model-output-dir',
        type=str,
        required=True,
        help='Directory path to save the trained model (e.g., gs://my-bucket/model/)'
    )
    
    parsed_args = parser.parse_args()
    main(parsed_args)
