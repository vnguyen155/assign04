package assign04;

import java.util.Comparator;
import java.util.Arrays;

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
                array[j + 1] = array[j]; // shift element rightward
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

    /**
     * Comparator that defines the Similarity comparison of positive integer values (represented as Strings.)
     */
    public static class StringSimilarityComparator implements Comparator<String> {
        //compare the amount of same integer char values in each string

        /**
         * Compares two positive integer strings using Similarity (compares whether the same integer char values
         * can be found in both Strings).
         * If the two strings are different lengths,
         * they are not similar, and the shorter string comes before the longer one. If the two strings
         * are the same length, then their characters are sorted, then compared lexicographically to detemrine
         * their character similarity.
         *
         * @param n1 the first String to be compared.
         * @param n2 the second String to be compared.
         * @return -1 if n1 is shorter than n2,
         * or 1 if n1 is longer than n2,
         * or the comparison of n1 and n2s' Char[] values if n1 and n2 are the same length,
         * or 0 if n1 and n2 are the same length and equal.
         */
        @Override
        public int compare(String n1, String n2) {
            if (n1.length() < n2.length()) { //the shorter string comes before the longer string
                return -1;
            } else if (n1.length() == n2.length()) { //if the same size but NOT similar, break the tie with
                //lexicographical comparison (The sorted order of characters are compared lexicographically)

                //Make a sorted Char[] of each number's characters
                Comparator<Character> charComparator = Comparator.naturalOrder(); //using a comparator that invokes
                //the natural ordering for Character
                Character[] n1CharArr = new Character[n1.length()];
                Character[] n2CharArr = new Character[n2.length()];
                for (int i = 0; i < n1.length(); i++) {
                    n1CharArr[i] = n1.charAt(i);
                    n2CharArr[i] = n2.charAt(i);
                }
                insertionSort(n1CharArr, charComparator);
                insertionSort(n2CharArr, charComparator);

                //Compare the sorted Char[]
                for (int i = 0; i < n1CharArr.length; i++) {
                    int comparison = n1CharArr[i].compareTo(n2CharArr[i]);
                    if (comparison != 0) {
                        return comparison;
                    }
                }
                return 0; //getting here means that all the Char[] elements were equal, therefore making the Strings similar.
            } else { //the longer string comes after
                return 1;
            }
        }
    }

    /**
     * Comparator that defines the Similarity Group comparison of positive integer value groups (represented as String[].)
     */
    public static class StringSimilarityGroupComparator implements Comparator<String[]> {
        /**
         * Defines the comparison of Strings by similarity groups.
         *
         * @param n1 the first String[] to be compared
         * @param n2 the second String[] to be compared
         * @return 0 if both String[] are empty, 1 if n1 is longer than n2, -1 if n1 is
         * shorter than n2, Comparison of n1 and n2's largest values if they are the same length.
         *
         */
        @Override
        public int compare(String[] n1, String[] n2) {

            if ((n1.length == 0) && (n2.length == 0)) { //if both groups are empty, they are deemed equal
                return 0;
            } else if (n1.length == n2.length) { //if two groups have the same size, the group with the largest integer value
                //(represented as a String) is deemed the largest group.
                StringNumericalValueComparator compareNumerical = new StringNumericalValueComparator();
                String n1LargestVal = findMax(n1, compareNumerical);
                String n2LargestVal = findMax(n2, compareNumerical);

                StringNumericalValueComparator compareLargests = new StringNumericalValueComparator();
                return compareLargests.compare(n1LargestVal, n2LargestVal);
            } else if (n1.length > n2.length) { // Check if we need these comparisons
                return 1;
            } else {
                return -1;
            }
        }
    }


    /**
     * Computes and returns the similarity groups for the given array of integer strings.
     *
     * @param array - the input array of positive integer strings
     * @return a 2D string array where each row is a similarity group, or an empty 2D array if input is null or empty
     */
    public static String[][] getSimilarityGroups(String[] array) {
        // Base case: Check for null or empty input array
        if (array == null || array.length == 0) {
            return new String[0][];
        }

        // Create a copy of the array
        String[] copyArray = array.clone();

        // Sort using insertionSort and StringSimilarityComparator
        StringSimilarityComparator cmp = new StringSimilarityComparator();
        insertionSort(copyArray, cmp);

        // Count the number of similarity groups
        int groupCount = 1;
        for (int i = 1; i < copyArray.length; i++) {
            if (cmp.compare(copyArray[i - 1], copyArray[i]) != 0) {
                groupCount++;
            }
        }

        // Allocate the 2D array with the total number of unique groups (rows)
        String[][] groups = new String[groupCount][];
        int groupIndex = 0; // track the current row in 2D result array
        int start = 0; // track the starting index of current group in copyArray

        // Partition sorted array into similarity group rows
        for (int i = 1; i <= copyArray.length; i++) {
            // Detect group boundary: end of array or adjacent elements are not similar
            if (i == copyArray.length || cmp.compare(copyArray[i - 1], copyArray[i]) != 0) {
                int groupSize = i - start; // calculate number of elements in this group
                groups[groupIndex] = new String[groupSize]; // create a new row of exact group size

                // Copy elements belonging to this group into the 2D array row
                for (int j = 0; j < groupSize; j++) {
                    groups[groupIndex][j] = copyArray[start + j];
                }

                groupIndex++; // move to the next row in 2D array
                start = i; // update start index for the next group
            }
        }

        return groups;
    }

    /**
     * Returns the largest similarity group in the input array.
     *
     * @param intArr - the input array of primitive integers
     * @return a String array containing the largest similarity group, or an empty array if input is null or empty
     */
    public static String[] findMaximumSimilarityGroup(int[] intArr) {
        // Base case: Check for null or empty input array
        if (intArr == null || intArr.length == 0) {
            return new String[0];
        }

        // Convert the primitive int array into a String array
        String[] strArray = new String[intArr.length];
        for (int i = 0; i < intArr.length; i++) {
            strArray[i] = String.valueOf(intArr[i]); // convert each int to String
        }

        // Obtain all similarity groups from the coverted String array
        String[][] groups = getSimilarityGroups(strArray);

        // Find and return the maximum similarity group using findMax and StringSimilarityComparator
        StringSimilarityGroupComparator groupCmp = new StringSimilarityGroupComparator();
        return findMax(groups, groupCmp);
    }

}
