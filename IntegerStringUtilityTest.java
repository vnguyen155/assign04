package assign04;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Arrays;

/**
 * For testing the IntegerStringUtility class.
 *
 * @author Vany Nguyen and Brooke Griffin
 * @version 09-24-2026
 */
public class IntegerStringUtilityTest {
	// Comparators used across the test
	private Comparator<String> numericalCmp;
	private Comparator<String> similarityCmp;
	private Comparator<String[]> groupCmp;

	// Sample inputs
	private Character[] unsortedChars;
	private String[] unsortedStrings;
	private int[] sampleInts;

	/**
	 * Sets up test fixtures before each test method runs
	 */
	@BeforeEach
	public void setUp() {
		numericalCmp = new IntegerStringUtility.StringNumericalValueComparator();
		similarityCmp = new IntegerStringUtility.StringSimilarityComparator();
		groupCmp = new IntegerStringUtility.StringSimilarityGroupComparator();

		unsortedChars = new Character[] { '8', '6', '1', '0', '4' };
		unsortedStrings = new String[] { "2341", "123", "2134", "2431", "312", "2143" };
		sampleInts = new int[] { 123, 987, 321, 789, 132, 879 };
	}

	// -------------------------------------------------------------------------
	// Test the examples from Assignment 4
	// -------------------------------------------------------------------------

	@Test
	public void stringNumericalValueComparatorSameLengthLargerReturnsPositive() {
		assertTrue(numericalCmp.compare("1234", "1233") > 0);
	}

	@Test
	public void stringNumericalValueComparatorDifferentLengthLargerReturnsPositive() {
		assertTrue(numericalCmp.compare("13", "012") > 0);
	}

	@Test
	public void stringNumericalValueComparatorDifferentLengthSmallerReturnsNegative() {
		assertTrue(numericalCmp.compare("987", "1231") < 0);
	}

	@Test
	public void stringNumericalValueComparatorSameLengthSmallerReturnsNegative() {
		assertTrue(numericalCmp.compare("9223372036854775808", "9223372036854775809") < 0);
	}

	@Test
	public void insertionSortCharacters() {
		IntegerStringUtility.insertionSort(unsortedChars, (char1, char2) -> char1.compareTo(char2));
		assertArrayEquals(new Character[] { '0', '1', '4', '6', '8' }, unsortedChars);
	}

	@Test
	public void findMaxNumerical() {
		String[] group = { "2341", "2134", "2431", "2143" };
		assertEquals("2431", IntegerStringUtility.findMax(group, numericalCmp));
	}

	@Test
	public void getSimilarityGroupsExampleReturnsTwoGroups() {
		String[][] groups = IntegerStringUtility.getSimilarityGroups(unsortedStrings);

		// There are 2 similarity groups
		assertEquals(2, groups.length);

		// Shorter strings come first and insertion sort keeps the original order within
		// a group
		assertArrayEquals(new String[] { "123", "312" }, groups[0]);
		assertArrayEquals(new String[] { "2341", "2134", "2431", "2143" }, groups[1]);
	}

	@Test
	public void findMaximumSimilarityGroupSameSizeLargerValueWins() {
		String[] group = IntegerStringUtility.findMaximumSimilarityGroup(sampleInts);

		// The largest group has 3 elements
		assertEquals(3, group.length);

		// Both groups have size 3, but 987 is greater than 321, so the group containing
		// 987 is the largest
		assertArrayEquals(new String[] { "987", "789", "879" }, group);
	}

	@Test
	public void insertionSortLeadingZerosBecomesSortedNumerically() {
		String[] arr = { "5", "3", "02", "1", "04" };
		String[] expected = { "1", "02", "3", "04", "5" };
		IntegerStringUtility.insertionSort(arr, numericalCmp);
		assertTrue(Arrays.equals(arr, expected));
	}

	@Test
	public void stringNumericalValueComparatorEqualValuesReturnsZero() {
		String n1 = "5432";
		String n2 = "5432";
		int result = numericalCmp.compare(n1, n2);
		assertTrue((result == 0));
	}

	@Test
	public void stringSimilarityComparatorLargerValuesReturnsPositive() {
		String n1 = "1235";
		String n2 = "3421";
		int result = similarityCmp.compare(n1, n2);
		assertTrue((result > 0));
	}

	@Test
	public void stringSimilarityComparatorSimilarStringsReturnsZero() {
		String n1 = "1234";
		String n2 = "3421";
		int result = similarityCmp.compare(n1, n2);
		assertTrue((result == 0));
	}

	@Test
	public void stringSimilarityComparatorShorterStringsReturnsNegative() {
		String n1 = "3421";
		String n2 = "11111";
		int result = similarityCmp.compare(n1, n2);
		assertTrue((result < 0));
	}

	@Test
	public void stringSimilarityGroupComparatorLargerGroupReturnsPositive() {
		int result = groupCmp.compare(new String[] { "3421", "1234", "4321" }, new String[] { "987", "789" });
		assertTrue((result > 0));
	}

	@Test
	public void stringSimilarityGroupComparatorSmallerGroupReturnsNegative() {
		int result = groupCmp.compare(new String[] { "3421", "1234", "4321" }, new String[] { "1235", "5321", "2153" });
		assertTrue((result < 0));
	}

	@Test
	public void stringSimilarityGroupComparatorSameLargestValueReturnsZero() {
		int result = groupCmp.compare(new String[] { "3421", "1234", "4321" }, new String[] { "1234", "4321", "3421" });
		assertTrue((result == 0));
	}

	// -------------------------------------------------------------------------
	// ADDITIONAL TESTS
	// -------------------------------------------------------------------------
	@Test
	public void insertionSortEmptyArrayStaysEmpty() {
		String[] array = {};
		IntegerStringUtility.insertionSort(array, numericalCmp);

		// Nothing to sort, the array is still empty
		assertEquals(0, array.length);
	}

	@Test
	public void insertionSortSingleElementUnchanged() {
		String[] arr = { "64" };
		IntegerStringUtility.insertionSort(arr, numericalCmp);
		assertArrayEquals(new String[] { "64" }, arr);
	}

	@Test
	public void insertionSortDuplicatedValuesAdjacent() {
		String[] arr = { "23", "45", "12", "23" };
		IntegerStringUtility.insertionSort(arr, numericalCmp);
		assertArrayEquals(new String[] { "12", "23", "23", "45" }, arr);
	}

	@Test
	public void findMaxNullArrayReturnsNull() {
		String[] arr = null;
		assertNull(IntegerStringUtility.findMax(arr, similarityCmp));
	}

	@Test
	public void findMaxCopyArraySuccessfully() {
		String[] expectedOriginal = new String[] { "2341", "123", "2134", "2431", "312", "2143" };
		IntegerStringUtility.findMax(unsortedStrings, numericalCmp);
		assertArrayEquals(expectedOriginal, unsortedStrings);
	}

	@Test
	public void findMaxLeadingZerosReturnsLargestValue() {
		String[] arr = new String[] { "003464", "0112", "005780", "000043" };
		String maxValue = IntegerStringUtility.findMax(arr, numericalCmp);
		assertEquals("005780", maxValue);
	}

	@Test
	public void StringNumericalValueComparatorAllZeroValuesReturnsZero() {
		String[] arr = new String[] { "00000", "00" };
		int result = numericalCmp.compare(arr[0], arr[1]);
		assertEquals(0, result);
	}

	@Test
	public void stringSimilarityGroupComparatorBothEmptyReturnsZero() {
		assertEquals(0, groupCmp.compare(new String[0], new String[0]));
	}

	@Test
	public void getSimilarityGroupsEmptyArrayReturnsEmpty() {
		String[] emptyArr = {};
		assertArrayEquals(new String[0][], IntegerStringUtility.getSimilarityGroups(emptyArr));
	}

	@Test
	public void getSimilarityGroupsDoesNotAlterArray() {
		String[] expectedOriginal = new String[] { "2341", "123", "2134", "2431", "312", "2143" };
		IntegerStringUtility.getSimilarityGroups(unsortedStrings);
		assertArrayEquals(expectedOriginal, unsortedStrings);
	}

	@Test
	public void getSimilarityGroupsAllElementsDifferentReturnsOneGroupEach() {
		String[][] groups = IntegerStringUtility
				.getSimilarityGroups(new String[] { "233", "758", "0634", "15", "0002" });

		// No strings are similar, so each strings is its own group
		assertEquals(5, groups.length);
		assertEquals(1, groups[0].length);
		assertEquals(1, groups[1].length);
		assertEquals(1, groups[2].length);
		assertEquals(1, groups[3].length);
		assertEquals(1, groups[4].length);
	}

	@Test
	public void findMaximumSimilarityGroupNullArrayReturnsEmptyArray() {
		assertArrayEquals(new String[0], IntegerStringUtility.findMaximumSimilarityGroup(null));
	}

	@Test
	public void findMaximumSimilarityGroupDoesNotAlterOriginalArray() {
		int[] original = sampleInts;
		int[] expectedOriginal = original.clone();

		IntegerStringUtility.findMaximumSimilarityGroup(original);
		assertArrayEquals(expectedOriginal, original);
	}

	@Test
	public void findMaximumSimilarityGroupLargerSizeReturnsLargerGroup() {
		int[] inputArr = new int[] { 77, 92, 29 };
		String[] result = IntegerStringUtility.findMaximumSimilarityGroup(inputArr);

		// Check the size of the largest group
		assertEquals(2, result.length);

		// Check the correct elements in similarity group
		String[] expected = new String[] { "92", "29" };
		assertArrayEquals(expected, result);
	}

}
