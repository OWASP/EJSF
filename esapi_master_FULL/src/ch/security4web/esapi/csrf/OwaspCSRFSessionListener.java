package ch.security4web.esapi.csrf;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;


import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;

/**
 *  * OWASP Enterprise Security API (ESAPI)
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * Enterprise Security API (ESAPI) project. For details, please see
 * <a href="http://www.owasp.org/index.php/ESAPI">http://www.owasp.org/index.php/ESAPI</a>.
 *
 * Copyright (c) 2007 - The OWASP Foundation
 * 
 * The ESAPI is published by OWASP under the BSD license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author      Rakesh
 * @author      Dr.Prof.Emmanuel
 * @author 		Matthey Samuel
 * @version     2.0
 * @date		07/05/2013
 * @since       1.0
 */

public class OwaspCSRFSessionListener implements HttpSessionListener 
{

	private final static String CSRFTOKEN_NAME = "CSRFTOKEN_NAME";
	
	@Override
	public void sessionCreated(HttpSessionEvent event) 
	{
		HttpSession session = event.getSession();
		String randomId = generateRandomId();
		session.setAttribute(CSRFTOKEN_NAME, randomId);
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public String generateRandomId()
	{
		String hexToken = "";
		try
		{
		
		  SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			
	      //generate a random number
	      String randomNum = new Integer( sr.nextInt() ).toString();

	      //get its digest
	      MessageDigest sha = MessageDigest.getInstance("SHA-512");
	      byte[] result =  sha.digest( randomNum.getBytes() );
	      
	      System.out.println("***Byte []"+result);
	      hexToken = hexEncode(result);
	      System.out.println("***hex Token"+hexToken);
	      
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
	}

	
	
}
