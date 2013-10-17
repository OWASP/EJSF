package ch.security4web.esapi.xss;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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

@ManagedBean(name="login")
@SessionScoped
public class Login 
{
	private String userName;
	private String password;
	private Boolean logedin;
	private String firstLastName;
	
	public Login() 
	{
	    try 
	    {
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = facesContext.getExternalContext();
	        HttpServletRequest httpServletRequest = (HttpServletRequest)externalContext.getRequest();

	        //getSession(false), which returns null if no session already exists for the current client.
	        HttpSession session =(HttpSession)externalContext.getSession(false);

	        if (session == null) 
	        {
	            session = httpServletRequest.getSession(true);
	            session.setAttribute("logedin", "0");
	            session.setMaxInactiveInterval(-1);
	            System.out.println();

            }
	        else 
	        {
	                session.setAttribute("logedin", "0");
	                //No session timeout for public users
	                session.setMaxInactiveInterval(-1);  
	                Enumeration e = session.getAttributeNames();
            }

	    } 
	    catch (Exception e) 
	    {
	        System.out.println("Exception in session " + e.getMessage());
	    }

	} //end of constructor
	
	public String validUser() throws Exception 
	{
	    String returnString = null;

	    //ArrayList2d<Object> mainarray = new ArrayList2d<Object>();
	    //mainarray.addRow();
	    //mainarray.add(userName);
	    //mainarray.add(password);

	    //busBeans.usermanagement.users um = new busBeans.usermanagement.users();
	    //ArrayList retrieveList = um.getValidUser(mainarray);
	    
	    if (userName != null && (userName.equals("rakesh") || userName.equals("vijay"))) 
	    {
	        //ArrayList str = (ArrayList) retrieveList.get(1);

	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = facesContext.getExternalContext();

	        //getSession(false), which returns null if no session already exists for the current client.
	        HttpSession session =(HttpSession)externalContext.getSession(false);


	        if (session == null) 
	        {
	            System.out.println();

	        }
	        else 
	        {
	            Enumeration e = session.getAttributeNames();
	        }

	        System.out.println();

	        logedin=true;

	        //Set session attributes for login users
	        session.setAttribute("logedin", 1);
	        session.setAttribute("firstLastName", userName);
	        session.setAttribute("registeredUser", "true");

	        /**
	         * set session timeout for login user
	         * 1 min = 60 sec
	         * 5 min = 60 * 5 sec = 300 sec
	        */
	        session.setMaxInactiveInterval(300);   //5min

	        firstLastName = session.getAttribute("firstLastName").toString();
	        
	        return "viewPage";
	    }

	    return returnString=null;

	} //end of  validUser()

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public Boolean getLogedin() 
	{
		return logedin;
	}

	public void setLogedin(Boolean logedin) 
	{
		this.logedin = logedin;
	}

	public String getFirstLastName() 
	{
		return firstLastName;
	}

	public void setFirstLastName(String firstLastName) 
	{
		this.firstLastName = firstLastName;
	}
}
