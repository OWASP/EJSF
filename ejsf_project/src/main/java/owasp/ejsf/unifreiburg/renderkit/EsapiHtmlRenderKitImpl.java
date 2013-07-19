package owasp.ejsf.unifreiburg.renderkit;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.ClientBehaviorRenderer;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderKit;
import org.apache.myfaces.renderkit.html.HtmlRenderKitImpl;
import org.apache.myfaces.renderkit.html.HtmlResponseStateManager;
import org.apache.myfaces.shared.config.MyfacesConfig;
import org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils;

/**
 * It configures the Response writer, which is now responsible for rendering (after removing XSS attacked code)
 * HTML output after consulting Owasp Esapi library.
 *
 * @author Rakesh
 * @version $Revision: 1.0 $ $Date: 2012-03-15 21:56:39 -0500 (Wed, 03 Dec 2008) $
 */

@JSFRenderKit(renderKitId = "HTML_BASIC")
public class EsapiHtmlRenderKitImpl extends HtmlRenderKitImpl
{
    private static final Logger log = Logger.getLogger(EsapiHtmlRenderKitImpl.class.getName());

    private Map<String, Map<String, Renderer>> _renderers;
    private ResponseStateManager _responseStateManager;
    private Map<String,Set<String>> _families;
    private Map<String, ClientBehaviorRenderer> _clientBehaviorRenderers;

    /**
     * Create the instance of the EsapiHtmlRenderKitImp Class
     * 
     */
    public EsapiHtmlRenderKitImpl()
    {
        _renderers = new ConcurrentHashMap<String, Map<String, Renderer>>(64, 0.75f, 1);
        _responseStateManager = new HtmlResponseStateManager();
        _families = new HashMap<String, Set<String> >();
        _clientBehaviorRenderers = new HashMap<String, ClientBehaviorRenderer>();
    }
    
    /**
     * It overrides the original response writer with the customized EsapiHtmlResponseWriter
     * 
     * @return EsapiHtmlResponseWriterImpl object
     * 
     */
    @Override
    public ResponseWriter createResponseWriter(Writer writer, String contentTypeListString, String characterEncoding)
    {
        String selectedContentType = HtmlRendererUtils.selectContentType(contentTypeListString);

        if (characterEncoding == null)
        {
            characterEncoding = HtmlRendererUtils.DEFAULT_CHAR_ENCODING;
        }
        
        // Create new EsapiHtmlResponseWriterImpl object which is integrated with ESAPI API.  
        return new EsapiHtmlResponseWriterImpl(writer, selectedContentType, characterEncoding, 
        MyfacesConfig.getCurrentInstance(FacesContext.getCurrentInstance().getExternalContext()).isWrapScriptContentWithXmlCommentTag());
    }

}

