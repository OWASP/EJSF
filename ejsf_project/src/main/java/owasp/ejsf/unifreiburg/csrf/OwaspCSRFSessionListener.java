package owasp.ejsf.unifreiburg.csrf;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * OwaspCSRFSessionListener class is configured inside listener tag in web.xml file as shown in below.
 * <p>
 * {@code <listener>} <br>
 * {@code <description>OwaspCSRFSessionListener</description>}<br>
 * {@code <listener-class>esapi.unifreiburg.csrf.OwaspCSRFSessionListener</listener-class>}<br>
 * {@code <listener>} <br>
 * </p>
 * <p>
 * All the requests are passed through this CSRFSession Listener,once it is registered in web.xml file.
 * all the .xhtml pages are assigned temporary random generated session id as a CSRF Token.If attacker
 * hack session id or cookie information but does not have right session token then attacker gets exception 
 * page.  
 * </p>
 * @author      Rakesh
 * @author      Dr.Prof.Emmanuel
 * @version     1.0
 * @since       1.0
 */

public class OwaspCSRFSessionListener implements HttpSessionListener 
{
	private final static String CSRFTOKEN_NAME = "CSRFTOKEN_NAME";
	private final static String OLD_TOKEN = "OLD_TOKEN";
	private final static String NEW_TOKEN = "NEW_TOKEN";
	private final static String FLAG = "FLAG";
	
	
	/**
	* Create session and assign new random token by using SHA-512 algorithm.<br>
	* @param event input pass as httpSessionEvent 
	* @return null
	* @since 1.0 
	*/
	@Override
	public void sessionCreated(HttpSessionEvent event) 
	{
		/**System.out.println("**************Listener****************");	
		HttpSession session = event.getSession();
		String randomId = generateRandomId();
		System.out.println("**********Random key:"+randomId);
		session.setAttribute(CSRFTOKEN_NAME, randomId); **/
		
		HttpSession session = event.getSession();
		String randomId = generateRandomId();
		System.out.println("**********Session Listener Random key:"+randomId);
		session.setAttribute(CSRFTOKEN_NAME, randomId);
		session.setAttribute(NEW_TOKEN, randomId);
		session.setAttribute(OLD_TOKEN, randomId);
		session.setAttribute(FLAG, true);
		
	}
	
	/**
	* Destroy session<br>
	* @param event input pass as httpSessionEvent 
	* @return null
	* @since 1.0 
	*/
	@Override
	public void sessionDestroyed(HttpSessionEvent event) 
	{
		event.getSession().invalidate();
	}
	
	/**
	* Get new random Id base on SHA-512 Algorithm.<br>
	*  
	* @return new random token
	* @since 1.0 
	*/
	private String generateRandomId()
	{
		String hexToken = "";
		try
		{
		  SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			
		  //generate a random number
		  String randomNum = new Integer(sr.nextInt()).toString();
		
		  //get its digest
		  MessageDigest sha = MessageDigest.getInstance("SHA-512");
		  byte[] result =  sha.digest(randomNum.getBytes());
		  
		  hexToken = hexEncode(result);
	      
		}
		catch(NoSuchAlgorithmException ex)
		{
			 System.err.println(ex);
		}
		
		return hexToken;
	}
	
	/**
	 * Convert input string into Hex Encode.
	 * @param aInput pass input as byte array.
	 * @return string with hex encode
	 * @since 1.0 
	 */
	static private String hexEncode( byte[] aInput)
	{
	    StringBuilder result = new StringBuilder();
	    char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
	    for ( int idx = 0; idx < aInput.length; ++idx) 
	    {
	    	byte b = aInput[idx];
	    	result.append( digits[ (b&0xf0) >> 4 ] );
	    	result.append( digits[ b&0x0f] );
	    }
	    return result.toString();
	}
	
}
