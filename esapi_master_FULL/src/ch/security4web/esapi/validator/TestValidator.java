package ch.security4web.esapi.validator;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspProperty;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFValidator;

import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

@JSFValidator(
    name="esapi:validateRegex",
    bodyContent="empty",
    tagClass="esapi.unifreiburg.validator.TestValidationTag")
@JSFJspProperty(
    name="binding",
    returnType = "javax.faces.validator.RegexValidator",
    longDesc = "A ValueExpression that evaluates to a RegexValidator.")
    
public class TestValidator implements Validator, PartialStateHolder
{

    /**
     * Converter ID, as defined by the JSF 2.0 specification.
     */
    public static final String VALIDATOR_ID = "javax.faces.RegularExpression";

    /**
     * This message ID is used when the pattern is <code>null</code>, or an empty String.
     */
    public static final String PATTERN_NOT_SET_MESSAGE_ID = "javax.faces.validator.RegexValidator.PATTERN_NOT_SET";

    /**
     * This message ID is used when the passed value is not a String, or when
     * the pattern does not match the passed value.
     */
    public static final String NOT_MATCHED_MESSAGE_ID = "javax.faces.validator.RegexValidator.NOT_MATCHED";

    /**
     * This message ID is used when the pattern is not a valid regular expression, according
     * to the rules as defined in class {@link java.util.regex.Pattern}
     */
    public static final String MATCH_EXCEPTION_MESSAGE_ID = "javax.faces.validator.RegexValidator.MATCH_EXCEPTION";

    //TODO: Find a better place for such a common constant
    private static final String EMPTY_STRING = "";

    private String pattern;

    private boolean isTransient = false;

    // VALIDATE
    /** {@inheritDoc} */
    public void validate(FacesContext context,
                         UIComponent component,
                         Object value)
    {
        if (context == null)
        {
            throw new NullPointerException("context");
        }
        if (component == null)
        {
            throw new NullPointerException("component");
        }

        if (value == null)
        {
            return;
        }
        if (!(value instanceof String))
        {
            //throw new ValidatorException(_MessageUtils.getErrorMessage(context, NOT_MATCHED_MESSAGE_ID, null));
        }

        String string = (String) value;

        Pattern thePattern = null;
        if (pattern == null
         || pattern.equals(EMPTY_STRING))
        {
            //throw new ValidatorException(_MessageUtils.getErrorMessage(context, PATTERN_NOT_SET_MESSAGE_ID, null));
        }

        try
        {
            thePattern = Pattern.compile(pattern);
        }
        catch (PatternSyntaxException pse)
        {
            //throw new ValidatorException(_MessageUtils.getErrorMessage(context, MATCH_EXCEPTION_MESSAGE_ID, null));
        }

        if (!thePattern.matcher(string).matches())
        {
            //TODO: Present the patternExpression in a more user friendly way
            //Object[] args = {thePattern, _MessageUtils.getLabel(context, component)};
            //throw new ValidatorException(_MessageUtils.getErrorMessage(context, NOT_MATCHED_MESSAGE_ID, args));
        }
    }

    // RESTORE & SAVE STATE

    /** {@inheritDoc} */
    public Object saveState(FacesContext context)
    {
        if (!initialStateMarked())
        {
            return pattern;
        }
        return null;
    }

    /** {@inheritDoc} */
    public void restoreState(FacesContext context, Object state)
    {
        if (state != null)
        {
            //Since pattern is required, if state is null
            //nothing has changed
            this.pattern = (String) state;
        }
    }

    // SETTER & GETTER

    /** {@inheritDoc} */
    public boolean isTransient()
    {
        return isTransient;
    }

    /** {@inheritDoc} */
    public void setTransient(boolean isTransient)
    {
        this.isTransient = isTransient;
    }

    /**
     * The Regular Expression property to validate against. This property must be a ValueExpression
     * that resolves to a String in the format of the java.util.regex patterns.
     *
     * @param pattern a ValueExpression that evaluates to a String that is the regular expression pattern
     */
    public void setPattern(String pattern)
    {
        //TODO: Validate input parameter
        this.pattern = pattern;
        clearInitialState();
    }

    /**
     * Return the ValueExpression that yields the regular expression pattern when evaluated.
     *
     * @return The pattern.
     */
    @JSFProperty(required = true)
    public String getPattern()
    {
        return this.pattern;
    }

    private boolean _initialStateMarked = false;

    public void clearInitialState()
    {
        _initialStateMarked = false;
    }

    public boolean initialStateMarked()
    {
        return _initialStateMarked;
    }

    public void markInitialState()
    {
        _initialStateMarked = true;
    }
    
  
}

