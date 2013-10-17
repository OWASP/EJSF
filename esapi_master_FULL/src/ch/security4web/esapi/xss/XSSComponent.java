package ch.security4web.esapi.xss;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
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

@FacesComponent(value = "xssEncodedComponent")
//@SessionScoped
public class XSSComponent extends UIOutput
{
	private String value;
	
	public XSSComponent()
	{
		
	}
	
	public String getValue() 
	{
		return value;
	}

	public void setValue(String value) 
	{
		this.value = value;
	}

	@Override
    public Object saveState(FacesContext context) 
	{
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = value;
        return ((Object) (values));
    }

    @Override
    public void restoreState(FacesContext context, Object state) 
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        value = (String)values[1];
    }
    
    public void encodeBegin(FacesContext context) throws IOException 
    {
        ResponseWriter writer = context.getResponseWriter();
		System.out.println("***********Response Writer*********:-"+writer.getClass().getName());
		ValueExpression _ve = getValueExpression("value");
    	String propertyValue = (String) _ve.getValue(getFacesContext().getELContext());
    	
    	/**String encode = (String)getAttributes().get("encode");
    	if(encode != null && !encode.trim().equals("") 
    			&& encode.equals("htmlEncode"))
    	{
    		try
    		{
    				propertyValue = ESAPI.encoder().encodeForHTML(propertyValue);	
    		}
    		catch(Exception exception)
    		{
    			throw new IOException("HtmlEncode Error");
    		}
    	}
    	else if(encode != null && !encode.trim().equals("") 
    			&& encode.equals("attributeEncode"))
    	{
    		try
    		{
   				propertyValue = ESAPI.encoder().encodeForHTMLAttribute(propertyValue);
    		}
    		catch(Exception exception)
    		{
    			throw new IOException("AttributeEncode Error");
    		}
    	}
    	else if(encode != null && !encode.trim().equals("") 
    			&& encode.equals("javaScriptEncoded"))
    	{
    		try
    		{
   				propertyValue = ESAPI.encoder().encodeForJavaScript(propertyValue);	
    		}
    		catch(Exception exception)
    		{
    			throw new IOException("JavaScriptEncoded Error");
    		}
    	}
    	else if(encode != null && !encode.trim().equals("") 
    			&& encode.equals("cssEncoded"))
    	{
    		try
    		{
   				propertyValue = ESAPI.encoder().encodeForCSS(propertyValue);	
    		}
    		catch(Exception exception)
    		{
    			throw new IOException("CssEncoded Error");
    		}
    	}
    	else if(encode != null && !encode.trim().equals("") 
    			&& encode.equals("urlEncoded"))
    	{
    		try
    		{
    			propertyValue = ESAPI.encoder().encodeForURL(propertyValue);	
    		}
    		catch(Exception exception)
    		{
    			throw new IOException("UrlEncoded Error");
    		}
    	}
    	else
    	{
    		throw new IOException("property value is not matching");
    	}
		**/
    	
		writer.startElement("property", this);
		writer.write(propertyValue);
		writer.endElement("property");
		writer.flush();

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

