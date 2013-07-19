package owasp.ejsf.unifreiburg.csrf;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpSession;

import owasp.ejsf.unifreiburg.util.EsapiConstant;


/**
 * OwaspCSRFTokenInput class places page token inside hidden field of the form page.
 * As well as it checks the page token, which was sent in last request and compare with one,which was stored
 * inside session.If it finds that last sent token is not the same as stored token inside session then
 * it will throw an token exception. It means that someone like the middle man or attacker has sent a request
 * without right token information so this will save disclosing important information even though cookie might 
 * be stolen.
 * 
 * @author      Rakesh
 * @author      Dr.Prof.Emmanuel
 * @version     1.0
 * @since       1.0
 */

@FacesComponent(value = "owaspCsrfTokenComponent")
public class OwaspCSRFTokenInput extends UIComponentBase  
{
	/**
	 * Display hidden field with generated token id
	 * @param context FacesContext object passes as argument
	 * @return null
	 */
	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		System.out.println("******* Owasp CSRF Token Input :encode End :");
		// Get new http session object.  
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	
		// Get token value from Http Session.
		String token = (String) session.getAttribute(EsapiConstant.CSRFTOKEN_NAME);
		
		
		//String token = generateRandomId();
		//System.out.println("**************OwaspCSRFTokenInput :encodeEnd Random key1 :****************"+token);
		//session.setAttribute(EsapiConstant.CSRFTOKEN_NAME, token);
	
		// Write hidden input field inside the form with token value.
		System.out.println("*******encode id :"+(getClientId(context)));
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("input", null);
		responseWriter.writeAttribute("type", "hidden", null);
		responseWriter.writeAttribute("name", (getClientId(context)), "clientId");
		responseWriter.writeAttribute("value", token, "CSRFTOKEN_NAME");
		responseWriter.endElement("input");

	}
	
	/**
	 * It will compare hidden token id which was sent in previous request
	 * and token which is stored in same session for that particular user
	 * if it is different then it will throw an exception.
	 * @param context FacesContext object passes as argument
	 * @return null
	 */
	public void decode(FacesContext context) 
	{
		// Get clientId of this Input Component
		String clientId = getClientId(context);
		System.out.println("**************OwaspCSRFTokenInput :decode value :****************"+clientId);
		
		// Get hidden field value from input request.
		ExternalContext external = context.getExternalContext();
		Map requestMap = external.getRequestParameterMap();
		String value = String.valueOf(requestMap.get(clientId));
		System.out.println("**************OwaspCSRFTokenInput :decode value :****************"+value);

		// Access http session and get token id from it. 
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		String token = (String) session.getAttribute(EsapiConstant.CSRFTOKEN_NAME);
		System.out.println("**************OwaspCSRFTokenInput :decode token :****************"+token);

		// throw exception.If token with incoming request is empty.
		if (value == null || "".equals(value)) 
		{
			throw new RuntimeException("CSRFToken is missing!");
		} 

		// compare of token from session and request.
		if (!value.equalsIgnoreCase(token)) 
		{
			throw new RuntimeException("CSRFToken does not match!");
		}

	}
	
	
	/** private String generateRandomId()
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
	} **/
	
	/**
	 * Get family information of this tag.
	 * @return "csapi.csrfTokenInput"
	 */
	@Override
	public String getFamily() 
	{
		// TODO Auto-generated method stub
		return "csapi.csrfTokenInput";
	}
	
	
}
