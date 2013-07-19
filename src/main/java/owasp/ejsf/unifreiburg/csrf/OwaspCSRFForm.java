package owasp.ejsf.unifreiburg.csrf;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * OwaspCSRFForm extendes HtmlForm class in order to store new created session token id in
 * HTML Form. 
 * 
 * @author      Rakesh
 * @author      Dr.Prof.Emmanuel
 * @version     1.0
 * @since       1.0
 *
 */

//@FacesComponent(value = "owaspCsrfTokenComponent")
public class OwaspCSRFForm extends HtmlForm 
{
	/**
	 * Display session token inside .xhtml page.
	 * 
	 * @param context FacesContext objects passes as parameter.
	 * @return null
	 */
	@Override
	public void encodeBegin(FacesContext context) throws IOException 
	{
		System.out.println("**************OwaspCSRFForm****************");
		// initialize the new TokenInput 
		OwaspCSRFTokenInput owaspCSRFToken = new OwaspCSRFTokenInput();
		// set the clientId
		System.out.println(" id:"+this.getClientId());
		owaspCSRFToken.setId("token1" + "CSRFToken");

		// add the component to the form
		getChildren().add(owaspCSRFToken);
		super.encodeBegin(context);
	}
	
	public void decode(FacesContext context) 
	{
		this.getChildren();
		java.util.List<UIComponent> uiComponents = this.getChildren();
		
		//UIComponent uiComponent = (UIComponent)uiComponents.get(0);
				
		//OwaspCSRFTokenInput owaspCSRFTokenInput = (OwaspCSRFTokenInput)uiComponent;
		//owaspCSRFTokenInput.
		
		//String clientId = owaspCSRFTokenInput.getClientId();
		
		// Get hidden field value from input request.
		/**ExternalContext external = context.getExternalContext();
		Map requestMap = external.getRequestParameterMap();
		String value = String.valueOf(requestMap.get(clientId));**/
		//System.out.println("******Client Id :"+value);
		
		//System.out.println("**************OwaspCSRFForm decode****************"+uiComponents.size());
		// initialize the new TokenInput 
		//OwaspCSRFTokenInput owaspCSRFToken = new OwaspCSRFTokenInput();
		// set the clientId
		//System.out.println(" id:"+this.getClientId());
		//owaspCSRFToken.setId("token1" + "CSRFToken");

		// add the component to the form
		//getChildren().add(owaspCSRFToken);
		//super.decode(context);
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
