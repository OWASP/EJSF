package ch.security4web.esapi.validator;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.RegexValidator;
import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorELTag;
import javax.servlet.jsp.JspException;

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

@FacesComponent(value = "testValidationTag")
public class TestValidationTag extends ValidatorELTag
{
	private static final long serialVersionUID = 8363913774859484811L;

    private ValueExpression _pattern;
    private ValueExpression _binding;
    
    public TestValidationTag()
    {
    	System.out.println("*********ValidateRegexTag***********");
    }

    @Override
    protected Validator createValidator() throws JspException
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elc = fc.getELContext();
        if (_binding != null)
        {
            Object validator;
            try
            {
                validator = _binding.getValue(elc);
            }
            catch (Exception e)
            {
                throw new JspException("Error while creating the Validator", e);
            }
            
            if (validator instanceof RegexValidator)
            {
                return (Validator)validator;
            }
        }
        if (null != _pattern)
        {
            Application appl = fc.getApplication();
            RegexValidator validator = (RegexValidator) appl.createValidator(RegexValidator.VALIDATOR_ID);
            String pattern = (String)_pattern.getValue(elc);
            validator.setPattern(pattern);

            if (_binding != null)
            {
                _binding.setValue(elc, validator);
            }

            return validator;
        }
        else
        {
            throw new AssertionError("pattern may not be null");
        }
    }

    public ValueExpression getBinding()
    {
        return _binding;
    }

    public void setBinding(ValueExpression binding)
    {
        _binding = binding;
    }

    public ValueExpression getPattern()
    {
        return _pattern;
    }

    public void setPattern(ValueExpression pattern)
    {
        _pattern = pattern;
    }

    @Override
    public void release()
    {
        _pattern = null;
        _binding = null;
    }
	

}
