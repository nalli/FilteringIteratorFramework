package framework;

/**
 *
 * @author nalli
 *
 */
public interface IObjectTester {
	
	/**
	 * 
	 * @param o input object
	 * @return true if the object matches the filter criteria; otherwise, false.
	 */
	boolean test(Object o);
}
