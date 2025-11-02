package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test class for the {@link IntegerSet} class.
 * This class tests all public methods, including edge cases such as
 * empty sets, duplicate additions, and operations on disjoint sets.
 */
public class IntegerSetTest {

    private IntegerSet setA;
    private IntegerSet setB;

    /**
     * Sets up two new, empty {@link IntegerSet} objects before each test.
     */
    @BeforeEach
    void setUp() {
        setA = new IntegerSet();
        setB = new IntegerSet();
    }

    /**
     * Tests the {@link IntegerSet#clear()} method.
     * Verifies that a populated set is empty after clear() is called.
     */
    @Test
    @DisplayName("Test clear()")
    void testClear() {
        setA.add(1);
        setA.add(2);
        assertFalse(setA.isEmpty());
        setA.clear();
        assertTrue(setA.isEmpty());
        assertEquals(0, setA.length());
    }

    /**
     * Tests the {@link IntegerSet#length()} method.
     * Verifies that length is 0 for a new set, and increments
     * correctly as unique elements are added.
     */
    @Test
    @DisplayName("Test length()")
    void testLength() {
        assertEquals(0, setA.length());
        setA.add(1);
        assertEquals(1, setA.length());
        setA.add(2);
        assertEquals(2, setA.length());
        setA.add(2); // Adding duplicate
        assertEquals(2, setA.length());
    }

    /**
     * Tests the {@link IntegerSet#equals(Object)} method.
     * Verifies equality for sets with the same elements regardless of order,
     * and inequality for sets with different lengths or elements, or for
     * different object types.
     */
    @Test
    @DisplayName("Test equals(Object)")
    void testEquals() {
        setA.add(1);
        setA.add(2);
        setA.add(3);

        setB.add(3);
        setB.add(2);
        setB.add(1);

        IntegerSet setC = new IntegerSet();
        setC.add(1);
        setC.add(2);
        
        IntegerSet setD = new IntegerSet();
        setD.add(1);
        setD.add(4);

        assertTrue(setA.equals(setB)); // Same elements, different order
        assertTrue(setB.equals(setA)); // Symmetric property
        assertTrue(setA.equals(setA)); // Reflexive property
        assertFalse(setA.equals(setC)); // Different length
        assertFalse(setC.equals(setD)); // Same length, different elements
        assertFalse(setA.equals(null)); // Test against null
        assertFalse(setA.equals(new String("test"))); // Test against different type
        
        IntegerSet emptySet1 = new IntegerSet();
        IntegerSet emptySet2 = new IntegerSet();
        assertTrue(emptySet1.equals(emptySet2)); // Test two empty sets
    }

    /**
     * Tests the {@link IntegerSet#contains(int)} method.
     * Verifies that contains() returns true for present elements
     * and false for absent elements.
     */
    @Test
    @DisplayName("Test contains(int)")
    void testContains() {
        setA.add(10);
        assertTrue(setA.contains(10));
        assertFalse(setA.contains(99));
        assertFalse(new IntegerSet().contains(1)); // Test on empty set
    }

    /**
     * Tests the {@link IntegerSet#largest()} method.
     * Verifies it returns the correct maximum value.
     */
    @Test
    @DisplayName("Test largest()")
    void testLargest() {
        setA.add(1);
        setA.add(50);
        setA.add(10);
        setA.add(-5);
        assertEquals(50, setA.largest());
    }

    /**
     * Tests that {@link IntegerSet#largest()} throws
     * {@link IllegalStateException} when called on an empty set.
     */
    @Test
    @DisplayName("Test largest() throws exception on empty set")
    void testLargestOnEmptySet() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            setA.largest();
        });
        assertEquals("Cannot find largest in an empty set", exception.getMessage());
    }

    /**
     * Tests the {@link IntegerSet#smallest()} method.
     * Verifies it returns the correct minimum value.
     */
    @Test
    @DisplayName("Test smallest()")
    void testSmallest() {
        setA.add(1);
        setA.add(50);
        setA.add(10);
        setA.add(-5);
        assertEquals(-5, setA.smallest());
    }

    /**
     * Tests that {@link IntegerSet#smallest()} throws
     * {@link IllegalStateException} when called on an empty set.
     */
    @Test
    @DisplayName("Test smallest() throws exception on empty set")
    void testSmallestOnEmptySet() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            setA.smallest();
        });
        assertEquals("Cannot find smallest in an empty set", exception.getMessage());
    }

    /**
     * Tests the {@link IntegerSet#add(int)} method.
     * Verifies that elements are added and that duplicates are ignored.
     */
    @Test
    @DisplayName("Test add(int)")
    void testAdd() {
        setA.add(1);
        assertTrue(setA.contains(1));
        assertEquals(1, setA.length());
        
        setA.add(1); // Add duplicate
        assertTrue(setA.contains(1));
        assertEquals(1, setA.length());
        
        setA.add(2);
        assertTrue(setA.contains(2));
        assertEquals(2, setA.length());
    }

    /**
     * Tests the {@link IntegerSet#remove(int)} method.
     * Verifies that elements are correctly removed, and that
     * no error occurs when removing non-existent elements.
     */
    @Test
    @DisplayName("Test remove(int)")
    void testRemove() {
        setA.add(1);
        setA.add(2);
        
        setA.remove(1); // Remove existing
        assertFalse(setA.contains(1));
        assertTrue(setA.contains(2));
        assertEquals(1, setA.length());

        setA.remove(99); // Remove non-existent
        assertEquals(1, setA.length());
        
        setA.remove(2); // Remove last element
        assertTrue(setA.isEmpty());
        
        setA.remove(1); // Remove from empty set
        assertTrue(setA.isEmpty());
    }

    /**
     * Tests the {@link IntegerSet#union(IntegerSet)} method.
     * Verifies that 'this' set is modified to contain all unique
     * elements from both sets.
     */
    @Test
    @DisplayName("Test union(IntegerSet)")
    void testUnion() {
        setA.add(1);
        setA.add(2);
        
        setB.add(2);
        setB.add(3);

        setA.union(setB); // setA should become [1, 2, 3]

        assertEquals(3, setA.length());
        assertTrue(setA.contains(1));
        assertTrue(setA.contains(2));
        assertTrue(setA.contains(3));
        
        // Test union with self (should not change)
        IntegerSet expected = new IntegerSet();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        
        setA.union(setA);
        assertEquals(expected, setA);
        
        // Test union with empty set
        setA.union(new IntegerSet());
        assertEquals(expected, setA);
    }

    /**
     * Tests the {@link IntegerSet#intersect(IntegerSet)} method.
     * Verifies that 'this' set is modified to contain only elements
     * present in both sets.
     */
    @Test
    @DisplayName("Test intersect(IntegerSet)")
    void testIntersect() {
        setA.add(1);
        setA.add(2);
        setA.add(3);

        setB.add(2);
        setB.add(3);
        setB.add(4);

        setA.intersect(setB); // setA should become [2, 3]

        assertEquals(2, setA.length());
        assertTrue(setA.contains(2));
        assertTrue(setA.contains(3));
        assertFalse(setA.contains(1));
        
        // Test intersect with disjoint set
        setA.clear();
        setA.add(1);
        setA.add(2);
        setB.clear();
        setB.add(3);
        setB.add(4);
        
        setA.intersect(setB);
        assertTrue(setA.isEmpty());
    }

    /**
     * Tests the {@link IntegerSet#diff(IntegerSet)} method (this \ other).
     * Verifies that 'this' set is modified to remove elements
     * that are also found in the 'other' set.
     */
    @Test
    @DisplayName("Test diff(IntegerSet)")
    void testDiff() {
        setA.add(1);
        setA.add(2);
        setA.add(3);

        setB.add(2);
        setB.add(3);
        setB.add(4);

        setA.diff(setB); // setA should become [1]

        assertEquals(1, setA.length());
        assertTrue(setA.contains(1));
        assertFalse(setA.contains(2));
        assertFalse(setA.contains(3));
        
        // Test diff with self (should become empty)
        setA.clear();
        setA.add(1);
        setA.add(2);
        setA.diff(setA);
        assertTrue(setA.isEmpty());
    }

    /**
     * Tests the {@link IntegerSet#complement(IntegerSet)} method (other \ this).
     * Verifies that 'this' set is modified to become the elements
     * in 'other' that are not in 'this'.
     */
    @Test
    @DisplayName("Test complement(IntegerSet)")
    void testComplement() {
        setA.add(1);
        setA.add(2);
        setA.add(3);

        setB.add(2);
        setB.add(3);
        setB.add(4);

        setA.complement(setB); // setA should become [4]

        assertEquals(1, setA.length());
        assertTrue(setA.contains(4));
        assertFalse(setA.contains(1));
        assertFalse(setA.contains(2));
        assertFalse(setA.contains(3));
        
        // Test complement with self (should become empty)
        setA.clear();
        setA.add(1);
        setA.add(2);
        setA.complement(setA);
        assertTrue(setA.isEmpty());
    }

    /**
     * Tests the {@link IntegerSet#isEmpty()} method.
     * Verifies it returns true for a new or cleared set and
     * false for a populated set.
     */
    @Test
    @DisplayName("Test isEmpty()")
    void testIsEmpty() {
        assertTrue(setA.isEmpty());
        setA.add(1);
        assertFalse(setA.isEmpty());
        setA.clear();
        assertTrue(setA.isEmpty());
    }

    /**
     * Tests the {@link IntegerSet#toString()} method.
     * Verifies the string representation is in the correct format "[]".
     */
    @Test
    @DisplayName("Test toString()")
    void testToString() {
        assertEquals("[]", setA.toString());
        setA.add(1);
        assertEquals("[1]", setA.toString());
        setA.add(2);
        setA.add(3);
        // ArrayList.toString() guarantees order, which is fine
        // for this test as long as it's consistent.
        assertEquals("[1, 2, 3]", setA.toString());
    }
}