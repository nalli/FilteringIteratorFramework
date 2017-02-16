package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import framework.FilteringIterator;
import framework.ObjectTester;

/**
 * 
 * @author nalli
 *
 */
public class FilteringIteratorTest {
	
	private static ObjectTester tester;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tester = new ObjectTester() {
			@Override
			public boolean test(Object o) {
				String string = (String) o;
				return string.length() < 3;
			}
		};
	}

	/**
	 * Elements with length greater than 2 should be filtered out.
	 */
	@Test
	public void testValidIteratorAndTester() {
		List<String> strings = new ArrayList<>();
		strings.add("a");
		strings.add("ab");
		strings.add("abc");
		
		Iterator<String> myIterator = strings.iterator();
		FilteringIterator<String> iterator = new FilteringIterator<>(myIterator, tester);
		
		assertTrue(iterator.hasNext());
		assertEquals("a", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("ab", iterator.next());
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
	}
	
	/**
	 * Set tester to null. 
	 * All the elements in the given iterator should be valid.
	 */
	@Test
	public void testValidIteratorWithoutTester() {
		List<String> strings = new ArrayList<>();
		strings.add("a");
		strings.add("ab");
		strings.add("abc");
		
		Iterator<String> myIterator = strings.iterator();
		FilteringIterator<String> iterator = new FilteringIterator<>(myIterator, null);
		
		assertTrue(iterator.hasNext());
		assertEquals("a", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("ab", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("abc", iterator.next());
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
	}

	/**
	 * Set iterator to null. 
	 * hasNext() should return false and next() should return null.
	 */
	@Test
	public void testWithoutIterator() {		
		FilteringIterator<String> iterator = new FilteringIterator<>(null, tester);
		
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
	}

	/**
	 * Set iterator to an empty iterator. 
	 * hasNext() should return false and next() should return null.
	 */
	@Test
	public void testWithEmptyIterator() {
		List<String> strings = new ArrayList<>();
		Iterator<String> myIterator = strings.iterator();
	
		FilteringIterator<String> iterator = new FilteringIterator<>(myIterator, tester);
		
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
	}
	
	/**
	 * Test remove
	 */
	@Test
	public void testRemove() {
		List<String> strings = new ArrayList<>();
		strings.add("a");
		strings.add("ab");
		strings.add("abc");
		strings.add("aa");
		
		Iterator<String> myIterator = strings.iterator();
		FilteringIterator<String> iterator = new FilteringIterator<>(myIterator, tester);
		
		iterator.remove(); // next() doesn't need to be called here because it was called during instantiation.
		assertFalse(strings.contains("a"));
		iterator.remove(); // next() doesn't need to be called here because remove() indirectly invoke next().
		assertFalse(strings.contains("ab"));
		iterator.remove();
		assertFalse(strings.contains("aa"));
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
	}

	/**
	 * Test remove when no more element is available to remove
	 */
	@Test(expected = IllegalStateException.class)
	public void testRemoveWithException() {
		List<String> strings = new ArrayList<>();
		strings.add("a");
		strings.add("ab");
		strings.add("abc");
		strings.add("aa");
		
		Iterator<String> myIterator = strings.iterator();
		FilteringIterator<String> iterator = new FilteringIterator<>(myIterator, tester);
		
		iterator.remove(); // next() doesn't need to be called here because it was called during instantiation.
		assertFalse(strings.contains("a"));
		iterator.remove(); // next() doesn't need to be called here because remove() indirectly invoke next().
		assertFalse(strings.contains("ab"));
		iterator.remove();
		assertFalse(strings.contains("aa"));
		iterator.remove(); // no more element to remove at this point so expection is thrown.
	}
}
