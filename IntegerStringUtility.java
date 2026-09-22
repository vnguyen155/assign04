package assign04;

import java.util.Comparator;

/**
 * Constructs a program that compares positive integer values represented as strings and groups integers by similarity.
 *
 * @author Vany Nguyen and Brooke Griffin
 * @version 09-24-2026
 */
public class IntegerStringUtility {
    /**
     * Sorts the given array in-place using the insertion sort and the provided comparator.
     *
     * @param array - the array to be sorted
     * @param cmp   - the comparator defining the ordering of elements
     * @param <E>   - the type of elements in the array
     */
    public static <E> void insertionSort(E[] array, Comparator<? super E> cmp) {
        // Base case: Check if the array is null or contains 1 or less than 1 element
        if (array == null || array.length <= 1) {
            return;
        }
        // Iterate through the array starting from the second element (index 1)
        for (int i = 1; i < array.length; i++) {
            E current = array[i]; // The element to be inserted into the sorted sub-array
            int j = i - 1;

            // Shift elements of the sorted portion that are greater than 'current' to the right
            while (j >= 0 && cmp.compare(array[j], current) > 0) {
                array[i + 1] = array[j]; // shift element rightward
                j--; // move to the previous element on the left
            }

            // Place 'current' at its correct position in the sorted sub-array
            array[j + 1] = current;
        }
    }
}
