package ch.security4web.esapi.csrf;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

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

//@FacesComponent(value = "owaspCsrfTokenComponent")
public class OwaspCSRFForm extends HtmlForm 
{
	@Override
	public void encodeBegin(FacesContext context) throws IOException 
	{
		OwaspCSRFTokenInput owaspCSRFToken = new OwaspCSRFTokenInput();
		owaspCSRFToken.setId(this.getClientId());
		getChildren().add(owaspCSRFToken);
		super.encodeBegin(context);
	}
	
}
