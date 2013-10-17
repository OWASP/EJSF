package ch.security4web.esapi.user;
import org.owasp.esapi.errors.AuthenticationException;
import org.owasp.esapi.reference.FileBasedAuthenticator;

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
 * @author 		Matthey Samuel
 * @version     2.0
 * @date		07/05/2013
 * @since       1.0
 */

public class CreateUser {

	 public static void main (String[] args){

			FileBasedAuthenticator auth = (FileBasedAuthenticator)FileBasedAuthenticator.getInstance();
			
			try {
				auth.createUser("admin", "Test1234", "Test1234");
				System.out.println(auth.getUser("admin").getAccountId());
				auth.getUser(auth.getUser("admin").getAccountId()).addRole("admin");
				auth.getUser(auth.getUser("admin").getAccountId()).enable();
				auth.getUser(auth.getUser("admin").getAccountId()).unlock();
				System.out.println("getrole :"+auth.getUser(auth.getUser("admin").getAccountId()).getRoles());

				
				auth.createUser("user", "Test12345", "Test12345");
				System.out.println(auth.getUser("user").getAccountId());
				auth.getUser(auth.getUser("user").getAccountId()).addRole("user");
				auth.getUser(auth.getUser("user").getAccountId()).enable();
				auth.getUser(auth.getUser("user").getAccountId()).unlock();
				System.out.println("getrole :"+auth.getUser(auth.getUser("user").getAccountId()).getRoles());


			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	 }
}
