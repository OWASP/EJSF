package ch.security4web.esapi.authentication;

import java.io.IOException;

import javax.management.relation.Role;
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

import org.owasp.esapi.Authenticator;
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
 * @author      Rakesh
 * @author      Dr.Prof.Emmanuel
 * @author 		Matthey Samuel
 * @version     2.0
 * @date		07/05/2013
 * @since       1.0
 */

@WebFilter(servletNames="facesServlet")
public class EsapiAuthorizationFilter implements Filter {
	
	private FilterConfig fc;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("**************Authorization Filter****************");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        HttpSession session = httpServletRequest.getSession();
        System.out.println("***Filter Flag :"+session.getAttribute("FLAG"));
        

        // check si un user est logue
        Role currentUser = (Role) session.getAttribute("firstLastName");
        
        if (currentUser != null) {
        	
        	filterChain.doFilter(httpServletRequest, httpServletResponse);
        	
        }
        
        // a enlever
        System.out.println("role de l'user:"+  currentUser);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    	
    	//((HttpServletResponse) response).sendRedirect("index.xhtml");
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.fc = filterConfig;
		
	}

}
