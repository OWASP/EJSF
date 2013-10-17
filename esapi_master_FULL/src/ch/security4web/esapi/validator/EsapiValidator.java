package ch.security4web.esapi.validator;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.ValidationErrorList;
import org.owasp.esapi.errors.ValidationException;
import org.owasp.esapi.reference.validation.HTMLValidationRule;

import ch.security4web.esapi.util.EsapiConstant;

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

@FacesValidator(value="esapiValidator")
public class EsapiValidator implements Validator
{
	private String name;
	private String format;
	private String encoding;
	
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String textValue = (String) value;
        
        if(name != null && name.toUpperCase().equals(EsapiConstant.DATE))
        {
        	Boolean flag = isValidateDate(textValue);
        	 if(flag == null || !flag)
             {
                FacesMessage msg = new FacesMessage(EsapiConstant.DATE_VALIDATION_FAILED,EsapiConstant.INVALID_DATE_FORMAT);
     			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
     			throw new ValidatorException(msg);
             }
        }
        else if(name != null && name.toUpperCase().equals(EsapiConstant.CREDITCARD))
        {
        	Boolean flag = isValidateCreditCard(textValue);
        	if(flag == null || flag)
            {
                FacesMessage msg = new FacesMessage(EsapiConstant.CREDIT_CARD_VALIDATION_FAILED,EsapiConstant.INVALID_CREDIT_CARD_FORMAT);
    			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    			throw new ValidatorException(msg);
            }
        }

        else if(name != null && name.toUpperCase().equals(EsapiConstant.HTMLVALIDATION))
        {
    		if(textValue != null && !textValue.trim().equals(""))
    		{
	    			try
	    			{
	    				String validHtml = getValidHtmlOutput(component,textValue);
	    			}
	    			catch(Exception exception)
	    			{
	    				FacesMessage msg = new FacesMessage("Html validation failed.","Html validation error.");
	        			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        			throw new ValidatorException(msg);
	    			}
    		}
    		else
    		{
   				FacesMessage msg = new FacesMessage("Html validation failed.","Enter Value.");
       			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
       			throw new ValidatorException(msg);
   			}
    	}
    	else if(name != null && name.toUpperCase().equals(EsapiConstant.LENIENTDATE))
        {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
        			{
        				checkLenientDate(context, component,textValue);
        			}
        			catch(Exception exception)
        			{
        				((UIInput) component).setValid(false);
   		              	FacesMessage msg = new FacesMessage("Invalidation Date Format");
   		              	context.addMessage(component.getClientId(), msg);
        			}
        			
        		}
        		else
        		{
        			((UIInput) component).setValid(false);
		            FacesMessage msg = new FacesMessage("Enter Date.");
		            context.addMessage(component.getClientId(), msg);
        		}
        }
    	else if(name != null && name.toUpperCase().equals(EsapiConstant.FILE))
        {
    		if(textValue != null && !textValue.trim().equals(""))
        	{
        			try
        			{
        				checkFileValidation(textValue);
      		        } 
       		        catch (Exception e) 
       		        {
       		        	  ((UIInput) component).setValid(false);
       		              FacesMessage msg = new FacesMessage("Invalidation File Path");
       		              context.addMessage(component.getClientId(), msg);
       		        }
       		}
       		else
       		{
        			((UIInput) component).setValid(false);
		            FacesMessage msg = new FacesMessage("Enter File Name.");
		            context.addMessage(component.getClientId(), msg);
        	}
       }
       else if(name != null && name.toUpperCase().equals(EsapiConstant.FILECONTENT))
       {
       		if(textValue != null && !textValue.trim().equals(""))
       		{
       			if(encoding != null && !encoding.trim().equals(""))
       			{
       				try
	            	{
       					valideFileContent(context, component, textValue);
	           		} 
	           	    catch (Exception e) 
	           	    {
	           	    	((UIInput) component).setValid(false);
	           	        FacesMessage msg = new FacesMessage("Invalidation File Content");
	                    context.addMessage(component.getClientId(), msg);
	           	    }
       				
        		  }
        		  else
        		  {
        				((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalid Encoding.");
           		        context.addMessage(component.getClientId(), msg);
        		   }
        		}
        		else
        		{
        			((UIInput) component).setValid(false);
		            FacesMessage msg = new FacesMessage("Enter File Name.");
		            context.addMessage(component.getClientId(), msg);
        		}
        		
            }
    		else if(name != null && name.toUpperCase().equals(EsapiConstant.VALIDFILENAME))
            {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
	            	{
        				int size = validFileName(textValue);
        				
        				if(size > 0)
        			    {
        			    	((UIInput) component).setValid(false);
               		        FacesMessage msg = new FacesMessage("Invalidation File Content");
               		        context.addMessage(component.getClientId(), msg);
        			    }
	           		} 
           		    catch (Exception e) 
           		    {
           		    	((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalidation File Content");
           		        context.addMessage(component.getClientId(), msg);
           		    }
        		}
        		else
        		{
        			((UIInput) component).setValid(false);
		            FacesMessage msg = new FacesMessage("Enter File Name.");
		            context.addMessage(component.getClientId(), msg);
        		}
        		
            }
    		else if(name != null && name.toUpperCase().equals(EsapiConstant.EMAIL))
            {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
	            	{
        				org.owasp.esapi.Validator instance = ESAPI.validator();
        			    boolean flag = instance.isValidInput("test", textValue, "Email", 100, false);
        		        
        			    if(!flag)
        			    {
        			    	((UIInput) component).setValid(false);
               		        FacesMessage msg = new FacesMessage("Invalidation Email Format.");
               		        context.addMessage(component.getClientId(), msg);
        			    }
	           		} 
           		    catch (Exception e) 
           		    {
           		    	((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalidation Email Format.");
           		        context.addMessage(component.getClientId(), msg);
           		    }
        		}
        		
        		else
        		{
        			((UIInput) component).setValid(false);
		            FacesMessage msg = new FacesMessage("Enter email ID");
		            context.addMessage(component.getClientId(), msg);
        		}
            }
    		else if(name != null && name.toUpperCase().equals(EsapiConstant.SAFESTRING))
            {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
                	{
        				org.owasp.esapi.Validator instance = ESAPI.validator();
        			    boolean flag = instance.isValidInput("test", textValue, "SafeString", 100, false);
        		        
        			    if(!flag)
        			    {
        			    	((UIInput) component).setValid(false);
               		        FacesMessage msg = new FacesMessage("Invalidation String Format.");
               		        context.addMessage(component.getClientId(), msg);
        			    }
               		} 
           		    catch (Exception e) 
           		    {
           		    	((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalidation String Format.");
           		        context.addMessage(component.getClientId(), msg);
           		    }
        		}
        		
        		else
        		{
        			((UIInput) component).setValid(false);
    	            FacesMessage msg = new FacesMessage("Enter String.");
    	            context.addMessage(component.getClientId(), msg);
        		}
            }
        
    		else if(name != null && name.toUpperCase().equals(EsapiConstant.PASSWORD))
            {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
                	{
        				org.owasp.esapi.Validator instance = ESAPI.validator();
        			    boolean flag = instance.isValidInput("test", textValue, "Password", 100, false);
        		        
        			    if(!flag)
        			    {
        			    	((UIInput) component).setValid(false);
               		        FacesMessage msg = new FacesMessage("Invalidation Password Format.");
               		        context.addMessage(component.getClientId(), msg);
        			    }
               		} 
           		    catch (Exception e) 
           		    {
           		    	((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalidation Password Format.");
           		        context.addMessage(component.getClientId(), msg);
           		    }
        		}
        		
        		else
        		{
        			((UIInput) component).setValid(false);
    	            FacesMessage msg = new FacesMessage("Enter Password.");
    	            context.addMessage(component.getClientId(), msg);
        		}
            }
    		else if(name != null && name.toUpperCase().equals(EsapiConstant.DIGIT))
            {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
                	{
        				org.owasp.esapi.Validator instance = ESAPI.validator();
        			    boolean flag = instance.isValidInput("test", textValue, "Digit", 100, false);
        		        
        			    if(!flag)
        			    {
        			    	((UIInput) component).setValid(false);
               		        FacesMessage msg = new FacesMessage("Invalidation Digit Format.");
               		        context.addMessage(component.getClientId(), msg);
        			    }
               		} 
           		    catch (Exception e) 
           		    {
           		    	((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalidation Digit Format.");
           		        context.addMessage(component.getClientId(), msg);
           		    }
        		}
        		
        		else
        		{
        			((UIInput) component).setValid(false);
    	            FacesMessage msg = new FacesMessage("Enter Password.");
    	            context.addMessage(component.getClientId(), msg);
        		}
            }
    		else if(name != null && name.toUpperCase().equals(EsapiConstant.IPADDRESS))
            {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
	            	{
        				org.owasp.esapi.Validator instance = ESAPI.validator();
        			    boolean flag = instance.isValidInput("test", textValue, "IPAddress", 100, false);
        		        
        			    if(!flag)
        			    {
        			    	((UIInput) component).setValid(false);
               		        FacesMessage msg = new FacesMessage("Invalidation IPaddress Format.");
               		        context.addMessage(component.getClientId(), msg);
        			    }
        			    
	           		} 
           		    catch (Exception e) 
           		    {
           		    	((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalidation IPaddress Format.");
           		        context.addMessage(component.getClientId(), msg);
           		    }
        		}
        		
        		else
        		{
        			((UIInput) component).setValid(false);
		            FacesMessage msg = new FacesMessage("Enter IPaddress.");
		            context.addMessage(component.getClientId(), msg);
        		}
            }
    		else if(name != null && name.toUpperCase().equals(EsapiConstant.URL))
            {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
	            	{
        				org.owasp.esapi.Validator instance = ESAPI.validator();
        			    boolean flag = instance.isValidInput("test", textValue, "URL", 100, false);
        		        
        			    if(!flag)
        			    {
        			    	((UIInput) component).setValid(false);
               		        FacesMessage msg = new FacesMessage("Invalidation URL.");
               		        context.addMessage(component.getClientId(), msg);
        			    }
        			    
	           		} 
           		    catch (Exception e) 
           		    {
           		    	((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalidation URL.");
           		        context.addMessage(component.getClientId(), msg);
           		    }
        		}
        		
        		else
        		{
        			((UIInput) component).setValid(false);
		            FacesMessage msg = new FacesMessage("Enter URL.");
		            context.addMessage(component.getClientId(), msg);
        		}
            }
    		else if(name != null && name.toUpperCase().equals(EsapiConstant.SSNVALIDATION))
            {
        		if(textValue != null && !textValue.trim().equals(""))
        		{
        			try
	            	{
        				org.owasp.esapi.Validator instance = ESAPI.validator();
        			    boolean flag = instance.isValidInput("test", textValue, "SSN", 100, false);
        		        
        			    if(!flag)
        			    {
        			    	((UIInput) component).setValid(false);
               		        FacesMessage msg = new FacesMessage("Invalidation SSN.");
               		        context.addMessage(component.getClientId(), msg);
        			    }
        			    
	           		} 
           		    catch (Exception e) 
           		    {
           		    	((UIInput) component).setValid(false);
           		        FacesMessage msg = new FacesMessage("Invalidation SSN.");
           		        context.addMessage(component.getClientId(), msg);
           		    }
        		}
        		
        		else
        		{
        			((UIInput) component).setValid(false);
		            FacesMessage msg = new FacesMessage("Enter SSN.");
		            context.addMessage(component.getClientId(), msg);
        		}
            }
    		else
    		{
    			FacesMessage msg = new FacesMessage("HTML validation failed.","Enter TextField Value.");
    			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    			throw new ValidatorException(msg);
    		}

    }
    
    private int validFileName(String textValue)
    {
    	org.owasp.esapi.Validator instance = ESAPI.validator();
	    ValidationErrorList errors = new ValidationErrorList();
	        
	    instance.getValidFileName("test", textValue, ESAPI.securityConfiguration().getAllowedFileExtensions(), false, errors);
	    return errors.size();
    }
    
    private void valideFileContent(FacesContext context, UIComponent component, String textValue)
    {
    	  org.owasp.esapi.Validator instance = ESAPI.validator();
		  ValidationErrorList errors = new ValidationErrorList();
		  byte[] bytes = null;
		  try 
		  {
		 	 bytes = "12345".getBytes(encoding);
	   	  }
		  catch (Exception e) 
		  {
				((UIInput) component).setValid(false);
		        FacesMessage msg = new FacesMessage("Invalidation File Encoding");
 		        context.addMessage(component.getClientId(), msg);
 		  }
    }
    
    private void checkFileValidation(String textValue) throws Exception
    {
    	  org.owasp.esapi.Validator instance = ESAPI.validator();
	      ValidationErrorList errors = new ValidationErrorList();
	      File parent = new File(textValue);
	      String path = ESAPI.securityConfiguration().getResourceFile("ESAPI.properties").getParentFile().getCanonicalPath();
          instance.getValidDirectoryPath("dirtest1", path, parent, true, errors);
    }
    
    private void checkLenientDate(FacesContext context, UIComponent component, String textValue)
    {
    	  boolean acceptLenientDates = ESAPI.securityConfiguration().getLenientDatesAccepted();
	        if (acceptLenientDates) 
	        {
	            return;
	        }

	        Date lenientDateTest = null;
	        
	        try
	        {
	                org.owasp.esapi.Validator instance = ESAPI.validator();
	                if(format.trim().equals(EsapiConstant.SHORT))
  		        {
	                	lenientDateTest = instance.getValidDate("datatest3-lenient", textValue,
                              DateFormat.getDateInstance(DateFormat.SHORT, Locale.US),false);
  		        }
  		        else if(format.equals(EsapiConstant.MEDIUM))
  		        {
  		        	lenientDateTest = instance.getValidDate("datatest3-lenient", textValue,
                              DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US),false);
  		        }
  		        else if(format.equals(EsapiConstant.LONG))
  		        {
  		        	lenientDateTest = instance.getValidDate("datatest3-lenient", textValue,
                              DateFormat.getDateInstance(DateFormat.LONG, Locale.US),false);
  		        }
  		        else if(format.equals(EsapiConstant.FULL))
  		        {
  		        	lenientDateTest = instance.getValidDate("datatest3-lenient", textValue,
                              DateFormat.getDateInstance(DateFormat.FULL, Locale.US),false);
  		        }
  		        else
  		        {
          			((UIInput) component).setValid(false);
		              	FacesMessage msg = new FacesMessage("Invalidation Date Format");
		              	context.addMessage(component.getClientId(), msg);
  		        }
	        } 
	        catch (Exception e) 
	        {
	        	  ((UIInput) component).setValid(false);
	              FacesMessage msg = new FacesMessage("Invalidation Date Format");
	              context.addMessage(component.getClientId(), msg);
	        }
    }
    
    
	private boolean isValidateDate(String textValue) throws ValidatorException
	{
		Boolean flag = null;
    	if(textValue != null && !textValue.trim().equals(""))
		{
    		org.owasp.esapi.Validator instance = ESAPI.validator();
    		DateFormat format = SimpleDateFormat.getDateInstance();
    		flag = instance.isValidDate("datetest1", textValue, format, true);
		}
    	else
    	{
    		FacesMessage msg = new FacesMessage("Date validation failed.","Null Input Value.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
    	}
    	
    	return flag;
	}
	
	private boolean isValidateCreditCard(String textValue) throws ValidatorException
	{
		Boolean flag = null;
		if(textValue != null && !textValue.trim().equals(""))
		{
			org.owasp.esapi.Validator instance = ESAPI.validator();
			flag = !instance.isValidCreditCard("cctest1", textValue, true);
		}
		else
		{
			FacesMessage msg = new FacesMessage("Credit Card validation failed.","Enter Credit Card Number.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		return flag;
	}
	
	private String getValidHtmlOutput(UIComponent component,String textValue) throws ValidationException
	{
		    org.owasp.esapi.Validator instance = ESAPI.validator();
	        ValidationErrorList errors = new ValidationErrorList();
	
	        // new school test case setup
	        HTMLValidationRule rule = new HTMLValidationRule("test");
	        ESAPI.validator().addRule(rule);
	
	        String filteredTextValue = (String)ESAPI.validator().getRule("test").getValid("test", textValue);
	        System.out.println("********** Filtered Value :"+filteredTextValue);
	        System.out.println("********** Get Class :"+component.getClass());
	        
	        
		return filteredTextValue;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format) 
	{
		this.format = format;
	}

	public String getEncoding() 
	{
		return encoding;
	}

	public void setEncoding(String encoding) 
	{
		this.encoding = encoding;
	}
	
}
