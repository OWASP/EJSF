package ch.security4web.esapi.validator;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.ValidationErrorList;
import org.owasp.esapi.errors.ValidationException;
import org.owasp.esapi.reference.validation.HTMLValidationRule;
import org.owasp.esapi.*;

import com.sun.el.parser.ParseException;

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

public class EsapiTestValidation 
{
	public static void main(String []args)
	{
	        /**org.owasp.esapi.Validator instance = ESAPI.validator();
	        //DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        DateFormat format = SimpleDateFormat.getDateInstance();
	        System.out.println(instance.isValidDate("datetest1", "September 11, 2001", format, false));
	        System.out.println(instance.isValidDate("datetest2", null, format, false));
	        System.out.println(instance.isValidDate("datetest3", "", format, false));
			**/
		
	        /**ValidationErrorList errors = new ValidationErrorList();
	        assertTrue(instance.isValidDate("datetest1", "September 11, 2001", format, true, errors));
	        assertTrue(errors.size()==0);
	        assertFalse(instance.isValidDate("datetest2", null, format, false, errors));
	        assertTrue(errors.size()==1);
	        assertFalse(instance.isValidDate("datetest3", "", format, false, errors));
	        assertTrue(errors.size()==2);**/
			
			try
			{
		        System.out.println("getValidSafeHTML");
		        Validator instance = ESAPI.validator();
		        ValidationErrorList errors = new ValidationErrorList();
		
		        // new school test case setup
		        HTMLValidationRule rule = new HTMLValidationRule("test");
		        ESAPI.validator().addRule(rule);
		
		        System.out.println(ESAPI.validator().getRule("test").getValid("test", "Test. <script>alert(document.cookie)</script>"));
		
		        String test1 = "<b>Jeff</b>";
		        String result1 = instance.getValidSafeHTML("test", test1, 100, false, errors);
		        System.out.println("Test-1 :-"+result1);
		
		        //String test2 = "<a href=\"http://www.aspectsecurity.com\">Aspect Security</a>";
		        String test2 = "<input type=\"text\" name=\"lname\" />";
		        String result2 = instance.getValidSafeHTML("test", test2, 100, false, errors);
		        System.out.println("Test-2 :-"+result2);
		        
		        String test21 = "<a href=\"http://www.aspectsecurity.com\">Aspect Security</a> 4";
		        String result21 = instance.getValidSafeHTML("test", test21, 100, false, errors);
		        System.out.println("Test-21 :-"+result21);
		
		        String test3 = "Test. <script>alert(document.cookie)</script>";
		        System.out.println("Test-3 :-"+rule.getSafe("test", test3));
		
		        System.out.println("Test-4 :-"+ rule.getSafe("test", "Test. <<div on<script></script>load=alert()"));
		        System.out.println("Test-5 :-"+ rule.getSafe("test", "Test. <div style={xss:expression(xss)}>b</div>"));
		        System.out.println("Test-6 :-"+rule.getSafe("test", "Test. <s%00cript>alert(document.cookie)</script>"));
		        System.out.println("Test-7 :-"+rule.getSafe("test", "Test. <s\tcript>alert(document.cookie)</script>"));
		        System.out.println("Test-8 :-"+rule.getSafe("test", "Test. <s\tcript>alert(document.cookie)</script>"));
		        System.out.println("Test-9 :-"+rule.getSafe("test", "Test. <input type='text' value='better' />"));
		        
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			
		
			// testLenientDate();
			//testGetValidDirectoryPath();
			//testIsInvalidFilename();

	}
	
	public static void testIsInvalidFilename() 
	{
		 Validator instance = ESAPI.validator();
	     System.out.println(instance.isValidInput("test", "jeff.williams@aspectsecurity.com", "Email", 100, false));
	     System.out.println(instance.isValidInput("test", "jeff.williams@@aspectsecurity.com", "Email", 100, false));
	     System.out.println(instance.isValidInput("test", "jeff.williams@aspectsecurity", "Email", 100, false));
	     System.out.println(instance.isValidInput("test", "jeff.wil'liams@aspectsecurity.com", "Email", 100, false));
	     System.out.println(instance.isValidInput("test", "jeff.wil''liams@aspectsecurity.com", "Email", 100, false));
	     
        
        //assertFalse("Files must have an extension", instance.isValidFileName("test", "", false));
        //assertFalse("Files must have a valid extension", instance.isValidFileName("test.invalidExtension", "", false));
        //assertFalse("Filennames cannot be the empty string", instance.isValidFileName("test", "", false));
    }

	
	 public static void testGetValidDirectoryPath()  
	 {
		 try
		 {
			 
		 
	        System.out.println("getValidDirectoryPath");
	        Validator instance = ESAPI.validator();
	        ValidationErrorList errors = new ValidationErrorList();
	        // find a directory that exists
	        File parent = new File("/4545666");
	        String path = ESAPI.securityConfiguration().getResourceFile("ESAPI.properties").getParentFile().getCanonicalPath();
	        System.out.println(instance.getValidDirectoryPath("dirtest1", path, parent, true, errors));
	        System.out.println(instance.getValidDirectoryPath("dirtest2", null, parent, false, errors));
	        System.out.println(instance.getValidDirectoryPath("dirtest3", "ridicul%00ous", parent, false, errors));
		 }
		 catch(Exception exception)
		 {
			 exception.printStackTrace();
		 }
	 }
	
	 public  static void testLenientDate()
	 {
		    DateFormat shortDf = DateFormat.getDateInstance(DateFormat.SHORT);

		    DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
		    DateFormat longDf = DateFormat.getDateInstance(DateFormat.LONG);
		    DateFormat fullDf = DateFormat.getDateInstance(DateFormat.FULL);
		    System.out.println(shortDf.format(new Date()));
		    System.out.println(mediumDf.format(new Date()));
		    System.out.println(longDf.format(new Date()));
		    System.out.println(fullDf.format(new Date()));

		    // parsing
		    try
		    {
		      //Date date = shortDf.parse("1/1/2005");
		      Date date = shortDf.parse("1/1/2005");
		      //Date date = shortDf.parse("Jan 32, 2005");
		    }
		    catch (java.text.ParseException e) 
		    {
		    	e.printStackTrace();
		    }
		    
		 
		 
	        System.out.println("testLenientDate");
	        boolean acceptLenientDates = ESAPI.securityConfiguration().getLenientDatesAccepted();
	        if ( acceptLenientDates ) 
	        {
	                //assertTrue("Lenient date test skipped because Validator.AcceptLenientDates set to true", true);
	                
	                return;
	        }

	        Date lenientDateTest = null;
	        
	        try 
	        {
	                // lenientDateTest will be null when Validator.AcceptLenientDates
	                // is set to false (the default).
	                Validator instance = ESAPI.validator();
	                lenientDateTest = instance.getValidDate("datatest3-lenient", "2/28/2009",DateFormat.getDateInstance(DateFormat.SHORT),false);
	                System.out.println("Test Vale:"+lenientDateTest);
	        } 
	        catch (Exception ve) 
	        {
	        	ve.printStackTrace();
	        }
	        
	    }
	 
}
