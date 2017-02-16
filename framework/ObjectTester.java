package framework;

/**
 * <code>ObjectTester</code> is the default implementation of <code>IObjectTester</code> 
 * and its test method always returns true. test method can be overridden for customized filter.
 */
public class ObjectTester implements IObjectTester {

	@Override
	public boolean test(Object o) {
		return true;
	}

}
