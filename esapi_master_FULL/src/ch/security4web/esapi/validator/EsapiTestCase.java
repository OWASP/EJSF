/**
 * 
 */
package ch.security4web.esapi.validator;


import junit.framework.TestCase;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.ValidationErrorList;
import org.owasp.esapi.Validator;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.owasp.esapi.reference.validation.HTMLValidationRule;

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

public class EsapiTestCase extends TestCase
{
	 private static final String PREFERRED_ENCODING = "UTF-8";

	    public static Test suite() 
	    {
	        return new TestSuite(EsapiTestCase.class);
	    }

	    /**
	     * Instantiates a new HTTP utilities test.
	     *
	     * @param testName the test name
	     */
	    public EsapiTestCase(String testName) 
	    {
	        super(testName);
	    }

	    /**
	     * {@inheritDoc}
	     *
	     * @throws Exception
	     */
	    protected void setUp() throws Exception
	    {
	        // none
	    }

	    /**
	     * {@inheritDoc}
	     *
	     * @throws Exception
	     */
	    protected void tearDown() throws Exception 
	    {
	        // none
	    }
	    public void testGetValidSafeHTML() throws Exception {
	        System.out.println("getValidSafeHTML");
	        Validator instance = ESAPI.validator();
	        ValidationErrorList errors = new ValidationErrorList();

	        // new school test case setup
	        HTMLValidationRule rule = new HTMLValidationRule("test");
	        ESAPI.validator().addRule(rule);

	        assertEquals("Test.", ESAPI.validator().getRule("test").getValid("test", "Test. <script>alert(document.cookie)</script>"));

	        String test1 = "<b>Jeff</b>";
	        String result1 = instance.getValidSafeHTML("test", test1, 100, false, errors);
	        assertEquals(test1, result1);

	        String test2 = "<a href=\"http://www.aspectsecurity.com\">Aspect Security</a>";
	        String result2 = instance.getValidSafeHTML("test", test2, 100, false, errors);
	        assertEquals(test2, result2);

	        String test3 = "Test. <script>alert(document.cookie)</script>";
	        assertEquals("Test.", rule.getSafe("test", test3));

	        assertEquals("Test. &lt;<div>load=alert()</div>", rule.getSafe("test", "Test. <<div on<script></script>load=alert()"));
	        assertEquals("Test. <div>b</div>", rule.getSafe("test", "Test. <div style={xss:expression(xss)}>b</div>"));
	        assertEquals("Test.", rule.getSafe("test", "Test. <s%00cript>alert(document.cookie)</script>"));
	        assertEquals("Test. alert(document.cookie)", rule.getSafe("test", "Test. <s\tcript>alert(document.cookie)</script>"));
	        assertEquals("Test. alert(document.cookie)", rule.getSafe("test", "Test. <s\tcript>alert(document.cookie)</script>"));
	    }
	    
}
