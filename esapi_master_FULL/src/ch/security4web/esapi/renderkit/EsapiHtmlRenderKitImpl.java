package ch.security4web.esapi.renderkit;

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

//import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderKit;
import org.apache.myfaces.renderkit.html.HtmlRenderKitImpl;
import org.apache.myfaces.renderkit.html.HtmlResponseStateManager;
import org.apache.myfaces.shared.config.MyfacesConfig;
import org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils;

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

/**
 * This class configured Response writer,which is used to generates render filtered(removing XSS attacked code)
 * HTML Response from Esapi library.
 *
 * @author Rakesh
 * @version $Revision: 1.0 $ $Date: 2012-03-15 21:56:39 -0500 (Wed, 03 Dec 2008) $
 */

//@JSFRenderKit(renderKitId="HTML_BASIC")
//@JSFRenderKit(renderKitId="HTML_BASIC")
public class EsapiHtmlRenderKitImpl extends HtmlRenderKitImpl
{
    private static final Logger log = Logger.getLogger(EsapiHtmlRenderKitImpl.class.getName());

    private Map<String, Map<String, Renderer>> _renderers;
    private ResponseStateManager _responseStateManager;
    private Map<String,Set<String>> _families;
    private Map<String, ClientBehaviorRenderer> _clientBehaviorRenderers;

    /**
     * Create Instance of EsapiHtmlRenderKitImp Class
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
     * This method overrides original response writer with customized EsapiHtmlResponseWriter
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

