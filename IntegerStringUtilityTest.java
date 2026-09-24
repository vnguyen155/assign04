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
	public void testStringNumericalValueComparatorSameLengthLargerReturnsPositive() {
		assertTrue(numericalCmp.compare("1234", "1233") > 0);
	}

	@Test
	public void testStringNumericalValueComparatorDifferentLengthLargerReturnsPositive() {
		assertTrue(numericalCmp.compare("13", "012") > 0);
	}

	@Test
	public void testStringNumericalValueComparatorDifferentLengthSmallerReturnsNegative() {
		assertTrue(numericalCmp.compare("987", "1231") < 0);
	}

	@Test
	public void testStringNumericalValueComparatorSameLengthSmallerReturnsNegative() {
		assertTrue(numericalCmp.compare("9223372036854775808", "9223372036854775809") < 0);
	}

	@Test
	public void testInsertionSortCharacters() {
		IntegerStringUtility.insertionSort(unsortedChars, (char1, char2) -> char1.compareTo(char2));
		assertArrayEquals(new Character[] { '0', '1', '4', '6', '8' }, unsortedChars);
	}

	@Test
	public void testFindMaxNumerical() {
		String[] group = { "2341", "2134", "2431", "2143" };
		assertEquals("2431", IntegerStringUtility.findMax(group, numericalCmp));
	}

	@Test
	public void testGetSimilarityGroupsExampleReturnsTwoGroups() {
		String[][] groups = IntegerStringUtility.getSimilarityGroups(unsortedStrings);

		// There are 2 similarity groups
		assertEquals(2, groups.length);

		// Shorter strings come first and insertion sort keeps the original order within
		// a group
		assertArrayEquals(new String[] { "123", "312" }, groups[0]);
		assertArrayEquals(new String[] { "2341", "2134", "2431", "2143" }, groups[1]);
	}

	@Test
	public void testFindMaximumSimilarityGroupSameSizeLargerValueWins() {
		String[] group = IntegerStringUtility.findMaximumSimilarityGroup(sampleInts);

		// The largest group has 3 elements
		assertEquals(3, group.length);

		// Both groups have size 3, but 987 is greater than 321, so the group containing
		// 987 is the largest
		assertArrayEquals(new String[] { "987", "789", "879" }, group);
	}
}
