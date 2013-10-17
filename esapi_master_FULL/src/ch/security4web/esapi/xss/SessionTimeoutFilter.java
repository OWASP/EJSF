package ch.security4web.esapi.xss;

import java.io.IOException;
import java.util.Enumeration;

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
public class SessionTimeoutFilter implements Filter
{
		/**private FilterConfig fc;
	 	@Override
	    public void init(FilterConfig filterConfig) throws ServletException 
	    {

	        this.fc = filterConfig;

	    }
		    @Override
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
	    {
	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
	        HttpSession session = request.getSession(false);

	        if (!request.getRequestURI().endsWith("/login.xhtml") && (session == null || session.getAttribute("yourSessionBean") == null)) 
	        {
	            response.sendRedirect(request.getContextPath() + "/login.xhtml"); // Redirect to home page.
	        }
	        else 
	        {
	            chain.doFilter(req, res); // Bean is present in session, so just continue request.
	        }
	    }
		**/
	    // Add/generate init() and destroy() with empty bodies.
	
	
    private String timeoutPage = "faces/SessionExpire.xhtml";
    private String welcomePage = "faces/index.xhtml";
    private String loginPage = "faces/login.xhtml";
    
    public static Boolean expirePage = false;
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
    	
    	System.out.println("**********Session Request :");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        /**HttpSession session = httpServletRequest.getSession(false);
        
        if (!httpServletRequest.getRequestURI().endsWith("/login.xhtml") && (session == null || session.getAttribute("mySessionBean") == null)) 
        {
        	httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.xhtml"); // Redirect to home page.
        }
        else 
        {
        	filterChain.doFilter(httpServletRequest, httpServletResponse); // Bean is present in session, so just continue request.
        }
        **/
        
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        

        /**if (!httpServletRequest.getRequestURI().endsWith("/login.xhtml") && session == null) 
        {
        	 //session timeout check.
            if (httpServletRequest.getRequestedSessionId() != null && !httpServletRequest.isRequestedSessionIdValid()) 
            {
                System.out.println("Session has expired");

               session = httpServletRequest.getSession(true);
                session.setAttribute("logedin", "0");    // public user

                 //httpServletResponse.sendRedirect("http://www.google.com");
                 httpServletResponse.sendRedirect(timeoutPage);
            }
            else 
            {
            	
        	
                session = httpServletRequest.getSession(true);
                session.setAttribute("logedin", "0");
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                
            // }
            
        }
        else 
        {
        	 String isRegisteredUser = session.getAttribute("logedin").toString();

             if (isRegisteredUser.equalsIgnoreCase(("1"))) 
             {
            	 Login login = (Login)session.getAttribute("login");

                 Enumeration e = session.getAttributeNames();

                 while (e.hasMoreElements()) 
                 {
                     String attr = (String)e.nextElement();
                     System.err.println("attr  = "+ attr);
                     Object value = session.getAttribute(attr);
                     System.err.println("value = "+ value);

                 } //end of while

                 long sessionCreationTime = session.getCreationTime();
                 int sessionCreationTimeInSec = (int)(sessionCreationTime / 1000) % 60;
                 int sessionCreationTimeInMinutes = (int)((sessionCreationTime / (1000*60)) % 60);

                 long sessionLastAccessTime = session.getLastAccessedTime();
                 int sessionLastAccessTimeInSec  = (int)(sessionLastAccessTime / 1000) % 60 ;
                 int sessionLastAccessTimeInMinutes  = (int)((sessionLastAccessTime / (1000*60)) % 60 );

                 int sessionMaxTime = session.getMaxInactiveInterval();
                 int sessionMaxTimeInMinute = sessionMaxTime / 60 ;

                 if ((sessionCreationTimeInMinutes - sessionLastAccessTimeInMinutes) - 1 > sessionMaxTimeInMinute) 
                 {
                     System.out.println("Session is expiring in one minute");
                 }

                 System.out.println("");

                 filterChain.doFilter(httpServletRequest, httpServletResponse);

             }
             else if (isRegisteredUser.equalsIgnoreCase(("0")))  
             {
                  Enumeration e = session.getAttributeNames();
                  filterChain.doFilter(httpServletRequest, httpServletResponse);
             } //end of else if (isRegisteredUser.equalsIgnoreCase(("0")))
        }
        	
        **/
        
        // HttpSession session = httpServletRequest.getSession();

        /**
         * The session objects have a built-in data structure (a hash table) in which you can store
         * any number of keys and associated values. You use session.getAttribute("key") to look up
         * a previously stored value. The return type is Object, so you must do a typecast to
         * whatever more specific type of data was associated with that attribute name in the session.
         * The return value is null if there is no such attribute, so you need to check for null
         * before calling methods on objects associated with sessions.
         *
         * Note:
         *     JSF session scoped managed beans are under the covers stored as a HttpSession
         *     attribute with the managed bean name as key.
         */
        
        /**Login login = (Login)session.getAttribute("login");

        if (login == null) 
        {  // No such object already in session

            filterChain.doFilter(request, response);

        } else 
        {
         **/
            /**
             * If you use a RequestDispatcher, the target servlet/JSP receives the same
             * request/response objects as the original servlet/JSP. Therefore, you can pass
             * data between them using request.setAttribute(). With a sendRedirect(), it is a
             * new request from the client, and the only way to pass data is through the session or
             * with web parameters (url?name=value).
             */
         //   filterChain.doFilter(request, response);

        //}

        //System.out.println();

    } //end of doFilter()

    @Override
    public void destroy() 
    {

    } //end of destroy()
}