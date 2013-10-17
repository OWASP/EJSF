package ch.security4web.esapi.validator;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

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

@FacesComponent(value = "esapiValidation")
public class EsapiValidation extends UIComponent 
{
	private String value;
	
	public EsapiValidation()
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
        //values[0] = super.saveState(context);
        values[1] = value;
        return ((Object) (values));
    }

    @Override
    public void restoreState(FacesContext context, Object state) 
    {
        Object values[] = (Object[])state;
        //super.restoreState(context, values[0]);
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

	@Override
	public boolean isTransient() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTransient(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addFacesListener(FacesListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcast(FacesEvent arg0) throws AbortProcessingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encodeChildren(FacesContext arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UIComponent findComponent(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UIComponent> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientId(FacesContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FacesContext getFacesContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FacesListener[] getFacesListeners(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UIComponent getFacet(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, UIComponent> getFacets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<UIComponent> getFacetsAndChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UIComponent getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Renderer getRenderer(FacesContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRendererType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValueBinding getValueBinding(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRendered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processDecodes(FacesContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processRestoreState(FacesContext arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object processSaveState(FacesContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processUpdates(FacesContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processValidators(FacesContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void queueEvent(FacesEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void removeFacesListener(FacesListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setId(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(UIComponent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRendered(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRendererType(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueBinding(String arg0, ValueBinding arg1) {
		// TODO Auto-generated method stub
		
	}
   	
}

