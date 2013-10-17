package ch.security4web.esapi.testing;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@FacesComponent(value = "CSRFTokenInput")
public class CSRFTokenInput extends UIComponentBase {

    private final static String CSRFTOKEN_NAME = "CSRFTOKEN_NAME_FOR_APP";

    @Override
    public String getFamily() {
        return "csrfpt";
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        Logger.getLogger(CSRFTokenInput.class.getName()).log(Level.INFO, "Enter encodeEnd");
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String token = (String) session.getAttribute(CSRFTOKEN_NAME);
        ResponseWriter responseWriter = context.getResponseWriter();

        responseWriter.startElement("input", null);
        responseWriter.writeAttribute("type", "hidden", null);
        responseWriter.writeAttribute("name", (getClientId(context)), "clientId");

        responseWriter.writeAttribute("value", token, "CSRFTOKEN_NAME");
        responseWriter.endElement("input");
        Logger.getLogger(CSRFTokenInput.class.getName()).log(Level.INFO, "CSRFTokenEncode Token: {0}", token);

    }

    @Override
    public void decode(FacesContext context) {
        Logger.getLogger(CSRFTokenInput.class.getName()).log(Level.FINEST, "Enter Decode");

        String clientId = getClientId(context);

        ExternalContext external = context.getExternalContext();
        Map<String, String> requestMap = external.getRequestParameterMap();

        Logger.getLogger(CSRFTokenInput.class.getName()).log(Level.FINEST, "ClientId {0}", clientId);

        String value = String.valueOf(requestMap.get(clientId));

        Logger.getLogger(CSRFTokenInput.class.getName()).log(Level.FINEST, "CSRFTokenDecode Token: {0}", value);

        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        String token = (String) session.getAttribute(CSRFTOKEN_NAME);

        if (!value.equalsIgnoreCase(token)) {
            Logger.getLogger(CSRFTokenInput.class.getName()).log(Level.SEVERE, "CSRFToken does not match!");
            throw new RuntimeException("CSRFToken does not match!");
        }

        if (value == null || "".equals(value)) {
            Logger.getLogger(CSRFTokenInput.class.getName()).log(Level.SEVERE, "CSRFToken is missing!");
            throw new RuntimeException("CSRFToken is missing!");
        }



    }
}
