# Use a standard Python 3.10 base image
FROM python:3.10-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the requirements file in first to leverage Docker caching
COPY requirements.txt .

# Install the Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy your trainer script into the container
COPY trainer.py .

# Set the entrypoint for the container
# This is the command that Vertex AI will run
ENTRYPOINT ["python", "trainer.py"]