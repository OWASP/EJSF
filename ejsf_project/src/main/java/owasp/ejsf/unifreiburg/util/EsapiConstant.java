package owasp.ejsf.unifreiburg.util;

/**
 * EsapiConstant is utility file which keeps variable and associated value.
 * This all variables are used in custom validation at client side.
 * For Example :- <br>
 * {@code <h:inputText id="creditcard" value="#{user.creditcard}" required="#{true}" label="Credit Card">} <br>
 * {@code <esapi:validation name="CREDITCARD" />} <br>	
 * {@code </h:inputText>} <br>
 * 
 * In above tag validation name is "CREDITCARD" and it is declared inside this constant file.
 * @author      Rakesh
 * @author      Dr.Prof.Emmanuel
 * @version     1.0
 * @since       1.0
 *
 */
public final class EsapiConstant 
{
	/**
	 * validate simple input date 
	 */
	public static final String DATE = "DATE";
	/**
	 * validate creditcard
	 */
	public static final String CREDITCARD = "CREDITCARD";
	/**
	 * filtered xss code from input value.
	 */ 
	public static final String HTMLVALIDATION = "HTMLVALIDATION";
	/**
	 * validate Lenient date.
	 */ 
	public static final String LENIENTDATE = "LENIENTDATE";
	/**
	 * validate short value.
	 */
	public static final String SHORT = "SHORT";
	/**
	 * validate medium value.
	 */
	public static final String MEDIUM = "MEDIUM";
	/**
	 * validate long value.
	 */
	public static final String LONG = "LONG";
	/**
	 * validate full value.
	 */
	public static final String FULL = "FULL";
	/**
	 * validate file value.
	 */
	public static final String FILE = "FILE";
	/**
	 * validate file content value.
	 */
	public static final String FILECONTENT = "FILECONTENT";
	/**
	 * validate file content value.
	 */
	public static final String VALIDFILENAME = "VALIDFILENAME";
	/**
	 * validate email id
	 */
	public static final String EMAIL = "EMAIL";
	/**
	 * validate IP Address.
	 */
	public static final String IPADDRESS = "IPADDRESS";
	/**
	 * validate URL.
	 */
	public static final String URL = "URL";
	/**
	 * validate SSN value.
	 */
	public static final String SSNVALIDATION = "SSNVALIDATION";
	
	/********************************************* Messages ****************************************************/
	
	/** public static final String VALIDATION_FAILED = Date validation failed.;
	public static final String INVALID_DATE = "Invalid Date format."; **/
	
	public static final String DATE_VALIDATION_FAILED = "Date validation failed.";
	public static final String INVALID_DATE_FORMAT = "Invalid Date format.";
	
	public static final String CREDIT_CARD_VALIDATION_FAILED = "Credit Card validation failed.";
	public static final String INVALID_CREDIT_CARD_FORMAT = "Invalid Credit Card format.";
	
	public static final String HTML_VALIDATION_FAILED = "XSS validation failed.";
	public static final String HTML_VALIDATION_FORMAT = "Error in XSS validation.";
	
	/**************************************************************************************************/
	
	public static final String CSRFTOKEN_NAME = "CSRFTOKEN_NAME";
	
}
