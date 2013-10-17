package ch.security4web.esapi.xss;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

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

public class xssTag extends UIComponentTag 
{
	protected void setProperties(UIComponent component) 
	{
		super.setProperties(component);
	}
	
	public String getComponentType()
	{
		return "xssEncodedComponent";
	}
	
	public String getRendererType()
	{
		return null;
	}
	
}


