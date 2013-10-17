package ch.security4web.esapi.validator;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.shared.taglib.UIComponentTagUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.validator.html.PolicyException;

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

@FacesComponent(value = "csapiValidationComponent")

public class EsapiValidationComponent extends UIComponentBase 
{
	private String value;
	private String name;
	
	public EsapiValidationComponent()
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
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
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
		/**ValueExpression _ve = getValueExpression("name");
		System.out.println("**************UIComponent*************"+_ve);
    	String propertyValue = (String) _ve.getValue(getFacesContext().getELContext());
    		
    	UIComponent cc = this.getParent();
    	
    	System.out.println("**************UIComponent*************"+cc);
    		
    	**/
        
    	/**if(propertyValue != null && propertyValue.toUpperCase().equals("EMAIL"))
    	{
    		
    	}
    	else if(propertyValue != null && propertyValue.toLowerCase().endsWith("DATE"))
    	{
    		ValueExpression format = getValueExpression("formate");
        	String strFormat = (String) format.getValue(getFacesContext().getELContext());
        	System.out.println("**********Date Formate**********"+strFormat);
    	}
    	else 
    	{
    		
    	}
    	**/
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
    	
		/**writer.startElement("property", this);
		writer.write(propertyValue);
		writer.endElement("property");
		writer.flush();**/

    }
    
   	public String getFamily()
   	{
   		return "simple.Label";
    }
   	
   	public void encodeEnd(FacesContext context) throws IOException 
	{
   		System.out.println("encodeEnd CsapiValidation Comp");
   		return;
	}

   	public void decode(FacesContext context) 
   	{
        ResponseWriter writer = context.getResponseWriter();
        
        String propetyName = (String)getAttributes().get("name");
        String tmpValue = (String)getAttributes().get("value");
        
        if(propetyName != null && propetyName.toUpperCase().equals("DATE"))
        {
        	String format = (String)getAttributes().get("format");
        	
        	if(format != null)
        	{
        		Application application = context.getApplication();
        		
        		ValueExpression ve = application.getExpressionFactory().createValueExpression(context.getELContext(), "#{currentUser.status.qualified}",Boolean.class);
        		
        		UIComponent uiComponent = getParent();
        		
        		//String value = (String)uiComponent.getAttributes().get("value");
        		String value = (String)uiComponent.getAttributes().get("value");
        		Boolean required = (Boolean)uiComponent.getAttributes().get("required");
        		
        		System.out.println("*********Value:-"+value);
        		
        		//Map map = uiComponent.getAttributes();
        		ValueExpression _ve = uiComponent.getValueExpression("value");
            	String propertyValue = (String) _ve.getValue(getFacesContext().getELContext());
        		System.out.println("***propertyValue :"+propertyValue);
        		
        		if(value != null && !value.trim().equals(""))
        		{
            		//ESAPI.validator().isValidDate("", "", "", new Boolean(false));
            		org.owasp.esapi.Validator instance = ESAPI.validator();
            		
                    DateFormat dformat = new SimpleDateFormat(format);
                    
                    if(!instance.isValidDate("datetest1", value, dformat, true))
                    {
                        FacesMessage msg = new FacesMessage("Date validation failed.","Invalid Date format.");
            			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            			throw new ValidatorException(msg);
                    }
                    //boolean flag = ESAPI.validator().isValidDate(new String(""),value, format, false);
        		}
        	}
        	else
        	{
        		
        	}
        }
        else if(propetyName != null && propetyName.toUpperCase().equals("CREDITCARD"))
        {
    		UIComponent uiComponent = getParent();
    		String value = (String)uiComponent.getAttributes().get("value");
    		if(value != null && !value.trim().equals(""))
    		{
    			org.owasp.esapi.Validator instance = ESAPI.validator();
    			if(!instance.isValidCreditCard("cctest1", value, true))
                {
                    FacesMessage msg = new FacesMessage("Credit Card validation failed.","Invalid Credit Card format.");
        			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        			throw new ValidatorException(msg);
                }
    		}
    		else
    		{
    			FacesMessage msg = new FacesMessage("Credit Card validation failed.","Enter Credit Card Number.");
    			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    			throw new ValidatorException(msg);
    		}

        }
        
        
        return;
	}

}
