package owasp.ejsf.unifreiburg.xss;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

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
