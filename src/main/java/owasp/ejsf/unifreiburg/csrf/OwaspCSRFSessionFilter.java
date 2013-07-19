package owasp.ejsf.unifreiburg.csrf;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import owasp.ejsf.unifreiburg.util.EsapiConstant;


@WebFilter(servletNames="facesServlet")
public class OwaspCSRFSessionFilter implements Filter
{
    private FilterConfig fc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException 
    {
        this.fc = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,   FilterChain filterChain)
        throws IOException, ServletException 
        {
    		System.out.println("**************Filter****************");
    		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	        
	        HttpSession session = httpServletRequest.getSession();
	        System.out.println("***Filter Flag :"+session.getAttribute("FLAG"));
	        
	        /**HttpSession session = httpServletRequest.getSession();
			String randomId = generateRandomId();
			System.out.println("**********Random key:"+randomId);
			session.setAttribute(EsapiConstant.CSRFTOKEN_NAME, randomId); **/
			
	        filterChain.doFilter(httpServletRequest, httpServletResponse);
        } 

    @Override
    public void destroy() 
    {

    } //end of destroy()
    
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