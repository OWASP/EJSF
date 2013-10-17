package ch.security4web.esapi.xss;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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

public class MySessionListener implements HttpSessionListener 
{
    // Constructor
    public MySessionListener() 
    {

    } //end of constructor

    @Override
    public void sessionCreated(HttpSessionEvent event) 
    {
        System.out.println("Current Session created : " + event.getSession().getCreationTime());

    } //end of sessionCreated()

    @Override
    public void sessionDestroyed(HttpSessionEvent event) 
    {
        // get the destroying session...
        HttpSession session = event.getSession();

        if (session != null) 
        {
            session.invalidate();
        }

        System.out.println();
    } //end of sessionDestroyed()

} //end of class MySessionListener
