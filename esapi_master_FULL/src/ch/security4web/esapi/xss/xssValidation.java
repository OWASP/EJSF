package ch.security4web.esapi.xss;

import java.io.IOException;

import javax.faces.bean.SessionScoped;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.owasp.esapi.ESAPI;

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

@FacesComponent(value = "xssValidationComponent")
//@SessionScoped
public class xssValidation extends UIInput
{
    public void encodeBegin(FacesContext context) throws IOException 
    {
    	/**UIComponentBase uiComponentBase = (UIComponentBase)getParent();
    	System.out.println("*******This is Parent Component :"+uiComponentBase.getClass());
    	
    	String propertyValue = (String)getAttributes().get("property");
    	
		ResponseWriter writer = context.getResponseWriter();
		StringBuffer stringBuffer = new StringBuffer();
	     
	    char[] chars = propertyValue.toCharArray();
	    for (int i = 0; i < chars.length; i++)
	    { 
	    	stringBuffer.append("&#" + (int) chars[i]);
	    }
	    
	    propertyValue = ESAPI.encoder().encodeForHTML(propertyValue);
	    
		writer.startElement("property", this);
		writer.write(propertyValue);
		writer.endElement("property");
		writer.flush();
		**/
    }
    
   	public String getFamily()
   	{
   		return "simple.Label";
    }
   	
   	public void encodeEnd(FacesContext context) throws IOException 
	{
   		return;
	}

   	public void decode(FacesContext context) 
   	{
   		return;
   	}
   	
}
