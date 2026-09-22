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

    /**
     * Find and returns the maximum element in the given array according to the order defined by the provided comparator.
     *
     * @param array - the input array from which to find the maximum element
     * @param cmp   - the comparator defining the ordering of elements
     * @param <E>   - the type of elements in the array
     * @return the maximum element in the array, or null if the array is null or empty
     */
    public static <E> E findMax(E[] array, Comparator<? super E> cmp) {
        // Check if the array is null or contains no elements
        if (array == null || array.length == 0) {
            return null;
        }

        // Create a copy of the array
        E[] arrayCopy = array.clone();
        insertionSort(arrayCopy, cmp);

        // Return the last object in the array, which should already be sorted from smallest to largest.
        return arrayCopy[arrayCopy.length - 1];

    }

    /**
     * Comparator that defines the numerical comparison of positive integer values represented as strings.
     */
    public static class StringNumericalValueComparator implements Comparator<String> {
        /**
         * Compares two positive integer strings numerically.
         *
         * @param n1 the first string to be compared.
         * @param n2 the second string to be compared.
         * @return a negative integer if n1 is less than n2 numerically,
         * or a positive integer if n1 is greater than n2 numerically,
         * or 0 if n1 equals to n2 numerically
         */
        @Override
        public int compare(String n1, String n2) {
            // Remove leading zeros for the first string
            int startN1 = 0;
            while (startN1 < n1.length() - 1 && n1.charAt(startN1) == '0') {
                startN1++;
            }
            String cleanN1 = n1.substring(startN1);

            // Remove leading zeros for the second string
            int startN2 = 0;
            while (startN2 < n2.length() - 1 && n2.charAt(startN2) == '0') {
                startN2++;
            }
            String cleanN2 = n2.substring(startN2);

            // Compare string lengths of two strings after removing leading zeros
            // The string with more digits represents a larger number
            if (cleanN1.length() != cleanN2.length()) {
                return cleanN1.length() - cleanN2.length();
            }

            // If lengths are equal, lexicographical comparison matches numerical value
            return cleanN1.compareTo(cleanN2);
        }

    }

    public static class StringSimilarityComparator implements Comparator<String> {
        //define the comparison of integer Strings by SIMILARITY
        @Override
        public int compare(String n1, String n2) {
            if (n1.length() < n2.length()) { //the shorter string comes before the longer string
                return -1;
            } else if (n1.length() == n2.length()) { //if the same size but NOT similar, break the tie with
                //lexicographical comparison
                if (!n1.equals(n2)) { //Placeholder - define similarity further (using InsertionSort Character[])
                    return n1.compareTo(n2);
                } else {
                    return 0;
                }
            } else { //the longer string comes after
                return 1;
            }
        }
    }

    public static class StringSimilarityGroupComparator implements Comparator<String[]> {
        // Define the comparison of similarity groups by group size (array length)
        @Override
        public int compare(String[] n1, String[] n2) {
            if ((n1.length == 0) && (n2.length == 0)) { //if both groups are empty, they are deemed equal
                return 0;
            } else if (n1.length == n2.length) { //if two groups have the same size, thr=e group with the largest integer value
                //(represented as a String) is deemed the largest group.
                String n1LargestVal = n1.findMax(n1, cmp);
                String n2LargestVal = n2.findMax(n2, cmp);
                return StringSimilarityComparator.compare(n1LargestVal, n2LargestVal);
            } else if (n1.length > n2.length) { // Check if we need these comparisons
                return 1;
            } else {
                return -1;
            }
        }
    }


    public static String[][] getSimilarityGroups(String[]) {
        // Create a copy of the array
        
    }

    public static String[] findMaximumSimilarityGroup(int[]){
        // Create a copy of the array
        int[] arrayCopy = int[].clone();
        insertionSort(arrayCopy, cmp);
    }

}
