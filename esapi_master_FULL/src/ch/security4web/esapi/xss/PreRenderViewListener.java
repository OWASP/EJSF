package ch.security4web.esapi.xss;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

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

public class PreRenderViewListener implements SystemEventListener 
{

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException 
    {
       /** UIViewRoot root = (UIViewRoot) event.getSource();
        FacesContext ctx = FacesContext.getCurrentInstance();
		String path = ctx.getExternalContext().getRequestContextPath();
		
        ResponseWriter writer = ctx.getResponseWriter();
        
        try 
        {
        	writer = new CsapiHtmlResponseWriterImpl(((ServletResponse) ctx.getExternalContext().getResponse()).getWriter(), null, null);
        }
        catch (IOException ex) 
        {
        	ex.printStackTrace();
        }
        ctx.setResponseWriter(writer);
        **/
    }

    @Override
    public boolean isListenerForSource(Object source) 
    {
        return true;
    }

}
