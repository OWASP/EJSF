package ch.security4web.esapi.csrf;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
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

@FacesComponent(value = "owaspCsrfTokenComponent")
@ResourceDependency(name = "csrf")
public class OwaspCSRFTokenInput extends UIComponentBase  
{
	private static final String CSRFTOKEN_NAME = "CSRFTOKEN_NAME";
	
	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{

		// get the session (don't create a new one!)
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	
		// get the token from the session
		String token = (String) session.getAttribute(CSRFTOKEN_NAME);
	
		// write the component html to the response
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("input", null);
		responseWriter.writeAttribute("type", "hidden", null);
		responseWriter.writeAttribute("name", (getClientId(context)), "clientId");
		responseWriter.writeAttribute("value", token, "CSRFTOKEN_NAME");
		responseWriter.endElement("input");

	}
	
	public void decode(FacesContext context) 
	{
		// get the client id of the component
		String clientId = getClientId(context);
		// access the hidden input field value
		ExternalContext external = context.getExternalContext();
		Map requestMap = external.getRequestParameterMap();
		String value = String.valueOf(requestMap.get(clientId));

		// access the session and get the token
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		String token = (String) session.getAttribute(CSRFTOKEN_NAME);

		// check if the token exists
		if (value == null || "".equals(value)) 
		{
			throw new RuntimeException("CSRFToken is missing!");
		} 

		// check the values for equality 
		if (!value.equalsIgnoreCase(token)) 
		{
			throw new RuntimeException("CSRFToken does not match!");
		}

	}

	@Override
	public String getFamily() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
