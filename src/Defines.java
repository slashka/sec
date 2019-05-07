/**
 * Some defines...
 * @author SlaSh
 *
 */
public class Defines {
	public static enum SOURCE {
		file,
		url
	}
	public static SOURCE mSource;
	
	public static enum TESTS {
		PHP_MOBILE,
		PHP_DESKTOP,
		PHP_ALL,
		JS_MOBILE,
		JS_DESKTOP,
		JS_ALL
	}
	public static TESTS mPHPTests, mJSTests;
	
	public static String mURL;
}
