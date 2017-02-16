package framework;

import java.util.Iterator;

/**
 * 
 * @author nalli
 *
 * @param <E>
 */
public final class FilteringIterator<E> implements Iterator<E> {
	
	private Iterator<E> myIterator;
	private IObjectTester tester;
	// Track next element in the given iterator
	private E nextElement;
	
	public FilteringIterator() {
		super();
	}
	
	public FilteringIterator(Iterator<E> myIterator, IObjectTester tester) {
		this.myIterator = myIterator;
		this.tester = tester;
		
		initalize();
	}

	/**
	 * Set the initial value for nextElement property
	 * If the input tester is null, create one.
	 * If the input iterator has next element and the element passes the tester
	 * 		set nextElement to the iterator's next element;
	 * otherwise,
	 * 		set nextElement to null
	 */
	private void initalize() {
		
		if (tester == null) {
			// the default tester.test(Object o) always return true,
			// which means all the element in the given iterator are valid
			tester = new ObjectTester();
		}
		
		findNextElement();
	}

	private void findNextElement() {
		while (myIterator != null && myIterator.hasNext()) {
			E next = myIterator.next();
			if (tester.test(next)) {
				this.nextElement = next;
				return;
			}
		}
		
		this.nextElement = null;
	}

	/**
	 * Check if the iterator has next element
	 * @return true if the next element of the given iterator is not null; 
	 * 			otherwise, false.
	 */
	@Override
	public boolean hasNext() {
		return this.nextElement != null;
	}

	/**
	 * Return the next element 
	 * and update nextElement value to next valid element or null
	 * if next valid element doesn't exist.
	 * @return nextElement
	 */
	@Override
	public E next() {
		E next = this.nextElement;
		findNextElement();
		return next;
	}

	/**
	 * Remove the last element returned by the given iterator
	 * and update nextElement value to next valid element or null if
	 * next valid element doesn't exist.
	 */
	@Override
	public void remove() {
		myIterator.remove();
		findNextElement();
	}
}
