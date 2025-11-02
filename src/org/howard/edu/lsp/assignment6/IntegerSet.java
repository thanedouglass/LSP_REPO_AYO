package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * The {@code IntegerSet} class represents a set of integers.
 * It uses an {@code ArrayList<Integer>} for internal storage and ensures
 * that no duplicate elements are stored. It provides standard set operations
 * such as union, intersection, and difference.
 */
public class IntegerSet {
    /**
     * The internal list to store the unique elements of the set.
     */
    private List<Integer> set = new ArrayList<Integer>();

    /**
     * Clears the internal representation of the set, removing all elements.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the number of elements in the set (its cardinality).
     */
    public int length() {
        return set.size();
    }

    /**
     * Compares this IntegerSet with the specified object for equality.
     * Returns true if the specified object is also an IntegerSet and
     * the two sets contain all of the same values, regardless of order.
     *
     * @param o the object to be compared for equality with this set.
     * @return true if the specified object is equal to this set.
     */
    @Override
    public boolean equals(Object o) {
        // 1. Check if it's the same object
        if (this == o) {
            return true;
        }

        // 2. Check if the object is null or of a different class
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // 3. Cast the object to IntegerSet
        IntegerSet otherSet = (IntegerSet) o;

        // 4. Optimization: Check if lengths are different
        if (this.length() != otherSet.length()) {
            return false;
        }

        // 5. Check for element containment (order-independent)
        // Since duplicates aren't allowed and sizes are equal,
        // this.containsAll(other) is sufficient.
        return this.set.containsAll(otherSet.set);
    }

    /**
     * Returns true if the set contains the specified value.
     *
     * @param value the value to check for presence in the set.
     * @return true if the set contains the value, false otherwise.
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest item in the set.
     *
     * @return the largest integer in the set.
     * @throws IllegalStateException if the set is empty.
     */
    public int largest() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot find largest in an empty set");
        }
        return Collections.max(set);
    }

    /**
     * Returns the smallest item in the set.
     *
     * @return the smallest integer in the set.
     * @throws IllegalStateException if the set is empty.
     */
    public int smallest() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot find smallest in an empty set");
        }
        return Collections.min(set);
    }

    /**
     * Adds an item to the set if it is not already present.
     *
     * @param item the integer to add to the set.
     */
    public void add(int item) {
        if (!contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes an item from the set if it is present.
     *
     * @param item the integer to remove from the set.
     */
    public void remove(int item) {
        // Use Integer.valueOf() to ensure Java calls remove(Object)
        // not remove(int index).
        set.remove(Integer.valueOf(item));
    }

    /**
     * Modifies this set to be the union of this set and the specified set.
     * The resulting set contains all unique elements from both sets.
     *
     * @param other the IntegerSet to union with this set.
     */
    public void union(IntegerSet other) {
        // Private members are accessible to other instances of the same class.
        for (int item : other.set) {
            this.add(item); // The add() method handles duplicate checking
        }
    }

    /**
     * Modifies this set to be the intersection of this set and the specified set.
     * The resulting set contains only elements that are present in both sets.
     *
     * @param other the IntegerSet to intersect with this set.
     */
    public void intersect(IntegerSet other) {
        // retainAll(c) keeps only the elements in this list
        // that are also contained in the specified collection.
        set.retainAll(other.set);
    }

    /**
     * Modifies this set to be the set difference of this set and the specified set.
     * The resulting set (this \ other) contains elements that are in this set
     * but not in the specified set.
     *
     * @param other the IntegerSet to difference with this set.
     */
    public void diff(IntegerSet other) {
        // removeAll(c) removes all of this list's elements that
        // are also contained in the specified collection.
        set.removeAll(other.set);
    }

    /**
     * Modifies this set to be the set complement of this set with respect to
     * the specified set. The resulting set (other \ this) contains elements
     * that are in the specified set but not in this set.
     *
     * @param other the IntegerSet to complement against.
     */
    public void complement(IntegerSet other) {
        // Create a temporary list based on 'other.set'
        List<Integer> tempSet = new ArrayList<>(other.set);

        // Perform the difference (other \ this)
        tempSet.removeAll(this.set);

        // Assign the resulting complement set to 'this.set'
        this.set = tempSet;
    }

    /**
     * Returns true if the set is empty (contains no elements).
     *
     * @return true if the set is empty, false otherwise.
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a string representation of the set, with elements
     * enclosed in square brackets and separated by commas.
     * Example: [1, 2, 3]
     *
     * @return a string representation of the set.
     */
    @Override
    public String toString() {
        // The default ArrayList.toString() method provides
        // the exact format required: [1, 2, 3]
        return set.toString();
    }
}