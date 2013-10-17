package ch.security4web.esapi.xss;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

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

public class xssComponentRederer extends Renderer
{
	@Override
	public void decode(FacesContext context, UIComponent component) 
	{
		// TODO Auto-generated method stub
		super.decode(context, component);
	}
	
	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException 
	{
		// TODO Auto-generated method stub
		super.encodeEnd(context, component);
	}
	
}
