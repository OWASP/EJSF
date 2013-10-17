package ch.security4web.esapi.authentication;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/*public class EsapiPOSTFilter extends HttpServlet {

	private String id;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.getSession(true).setAttribute("id", req.getParameter("id"));
       req.getRequestDispatcher("main.xhtml").forward(req, resp);
     }
}*/

	public class EsapiPOSTFilter implements Filter {
	
		
		
		private FilterConfig fc;
		List<UIComponent> children;
	
		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			// TODO Auto-generated method stub
			this.fc = filterConfig;
			
			//recuperer la liste des ID
			//children = EsapiAuthorization.getChildrenList();
		}
		
		@Override
		public void destroy() {
			// TODO Auto-generated method stub
					
			this.fc = null;
	
		}
	
		@Override
		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain filterChain) throws IOException, ServletException {
			// TODO Auto-generated method stub
	
			System.out.println("**************POST Filter****************");
	
			HttpServletRequest req = (HttpServletRequest)request;
			HttpServletResponse res = (HttpServletResponse)response;
	
			// Get blacklist in session
			List<String> blackList = (List<String>) req.getSession().getAttribute("blackList");
			
			System.out.println("**************BlackList****************");
	
			
			//affiche la blacklist si differente de null
			if(blackList != null){
				for(String black : blackList){
					System.out.print(black+", ");
				}
			}
			
			if(blackList==null){
				blackList = new LinkedList<String>();
			}
			
			Enumeration<String> enumParam = req.getParameterNames();
		    while(enumParam.hasMoreElements()){
		    	
		    	String id = enumParam.nextElement(); 
		    
			// if the ID is in the blacklist send error
			if ( blackList.contains(id) ) {
				
				System.out.println("**************id is on blacklist****************");
				res.sendError(javax.servlet.http.HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		    }
		    // clear the blacklist
		    blackList.clear();
		    req.setAttribute("blackList", blackList);
			System.out.println("************** Blacklist removed****************");
			filterChain.doFilter(req, res);
		}
	
	}