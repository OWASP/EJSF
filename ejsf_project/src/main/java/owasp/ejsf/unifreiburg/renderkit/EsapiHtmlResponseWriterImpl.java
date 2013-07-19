package owasp.ejsf.unifreiburg.renderkit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.shared.renderkit.RendererUtils;
import org.apache.myfaces.shared.renderkit.html.HTML;
import org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared.renderkit.html.HtmlResponseWriterImpl;
import org.apache.myfaces.shared.renderkit.html.util.UnicodeEncoder;
import org.apache.myfaces.shared.util.CommentUtils;
import org.apache.myfaces.shared.util.FastWriter;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.ValidationErrorList;
import org.owasp.esapi.reference.validation.HTMLValidationRule;

/**
 * It is configuring Response writer, which is now used to generates render filtered with removing XSS attacked code from HTML Response.
 *
 * @author Rakesh
 * @version $Revision: 1.0 $ $Date: 2012-03-15 21:56:39 -0500 (Wed, 03 Dec 2008) $
 */

public class EsapiHtmlResponseWriterImpl extends HtmlResponseWriterImpl
{
    private static final Logger log = Logger.getLogger(EsapiHtmlResponseWriterImpl.class.getName());

    private static final String DEFAULT_CONTENT_TYPE = "text/html";
    private static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";
    private static final String UTF8 = "UTF-8";

    private static String APPLICATION_XML_CONTENT_TYPE = "application/xml";
    private static String TEXT_XML_CONTENT_TYPE = "text/xml";
    
    private Writer _outputWriter;
    
    private Writer _currentWriter;
    
    private FastWriter _bufferedWriter;
    
    private String _contentType;
    
    private boolean _isXhtmlContentType;
    
    private boolean _useStraightXml;
    
    private String _characterEncoding;
    private boolean _wrapScriptContentWithXmlCommentTag;
    
    private String _startElementName;
    private Boolean _isInsideScript;
    private Boolean _isStyle;
    private Boolean _isTextArea;
    private UIComponent _startElementUIComponent;
    private boolean _startTagOpen;
    private boolean _cdataOpen;

    private static final Set<String> s_emptyHtmlElements = new HashSet<String>();

    private static final String CDATA_START = "<![CDATA[ \n";
    private static final String CDATA_START_NO_LINE_RETURN = "<![CDATA[";
    private static final String COMMENT_START = "<!--\n";
    private static final String CDATA_COMMENT_START = "//<![CDATA[ \n";
    private static final String CDATA_COMMENT_END = "\n//]]>";
    private static final String CDATA_END = "\n]]>";
    private static final String CDATA_END_NO_LINE_RETURN = "]]>";
    private static final String COMMENT_COMMENT_END = "\n//-->";
    private static final String COMMENT_END = "\n-->";

    static
    {
        s_emptyHtmlElements.add("area");
        s_emptyHtmlElements.add("br");
        s_emptyHtmlElements.add("base");
        s_emptyHtmlElements.add("basefont");
        s_emptyHtmlElements.add("col");
        s_emptyHtmlElements.add("frame");
        s_emptyHtmlElements.add("hr");
        s_emptyHtmlElements.add("img");
        s_emptyHtmlElements.add("input");
        s_emptyHtmlElements.add("isindex");
        s_emptyHtmlElements.add("link");
        s_emptyHtmlElements.add("meta");
        s_emptyHtmlElements.add("param");
    }

    public EsapiHtmlResponseWriterImpl(Writer writer, String contentType, String characterEncoding)
    {
        this(writer,contentType,characterEncoding,true);
    }

    public EsapiHtmlResponseWriterImpl(Writer writer, String contentType, String characterEncoding,
             boolean wrapScriptContentWithXmlCommentTag)
    throws FacesException
    {
    	super(writer,contentType,characterEncoding,wrapScriptContentWithXmlCommentTag);
        _outputWriter = writer;
        _currentWriter = _outputWriter;
        _bufferedWriter = new FastWriter(1024);
        _wrapScriptContentWithXmlCommentTag = wrapScriptContentWithXmlCommentTag;
        
        _contentType = contentType;
        if (_contentType == null)
        {
            if (log.isLoggable(Level.FINE))
            {
            	log.fine("No content type given, using default content type " + DEFAULT_CONTENT_TYPE);
            }
            _contentType = DEFAULT_CONTENT_TYPE;
        }
        _isXhtmlContentType = HtmlRendererUtils.isXHTMLContentType(_contentType);
        
        _useStraightXml = _contentType.indexOf(APPLICATION_XML_CONTENT_TYPE) != -1 ||
                          _contentType.indexOf(TEXT_XML_CONTENT_TYPE) != -1;

        if (characterEncoding == null)
        {
            if (log.isLoggable(Level.FINE))
            {
            	log.fine("No character encoding given, using default character encoding " + DEFAULT_CHARACTER_ENCODING);
            }
            _characterEncoding = DEFAULT_CHARACTER_ENCODING;
        }
        else
        {
            try
            {
                new String("myfaces".getBytes(), characterEncoding);
            }
            catch (UnsupportedEncodingException e)
            {
                throw new IllegalArgumentException("Unsupported encoding: "+characterEncoding);
            }
            _characterEncoding = characterEncoding.toUpperCase();
        }
    }

    public void startElement(String name, UIComponent uiComponent) throws IOException
    {
    	if (name == null)
        {
            throw new NullPointerException("elementName name must not be null");
        }
    	
    	name = ESAPI.encoder().encodeForHTML(name);
    	
    	closeStartTagIfNecessary();
        _currentWriter.write('<');
        _currentWriter.write(name);

        resetStartedElement();
        _startElementName = name;
        _startElementUIComponent = uiComponent;
        _startTagOpen = true;
        
        if(isScript(name))
        {
            _isInsideScript = Boolean.TRUE;
            _isStyle = Boolean.FALSE;
            _isTextArea = Boolean.FALSE;
        }
        else if (isStyle(name))
        {
            _isInsideScript = Boolean.FALSE;
            _isStyle = Boolean.TRUE;
            _isTextArea = Boolean.FALSE;
        }
    }

    private void closeStartTagIfNecessary() throws IOException
    {
        if (_startTagOpen)
        {
            if (!_useStraightXml && s_emptyHtmlElements.contains(_startElementName.toLowerCase()))
            {
                _currentWriter.write("/>");
                resetStartedElement();
            }
            else
            {
                _currentWriter.write('>');
                if (isScript(_startElementName) && (_isXhtmlContentType || _wrapScriptContentWithXmlCommentTag))
                {
                    _bufferedWriter.reset();
                    _currentWriter = _bufferedWriter;
                }                
                if (isStyle(_startElementName) && _isXhtmlContentType)
                {
                    _bufferedWriter.reset();
                    _currentWriter = _bufferedWriter;
                }
            }
            _startTagOpen = false;
        }
    }

    private void resetStartedElement()
    {
        _startElementName = null;
        _startElementUIComponent = null;
        _isStyle = null;
        _isTextArea = null;
    }

    public void endElement(String name) throws IOException
    {
        if (name == null)
        {
            throw new NullPointerException("ElementName name must not be null");
        }

        name = ESAPI.encoder().encodeForHTML(name);
        if (log.isLoggable(Level.WARNING))
        {
            if (_startElementName != null &&
                !name.equals(_startElementName))
            {
                log.warning("HTML nesting warning on closing " + name + ": element " + _startElementName +
                        (_startElementUIComponent==null?"":(" rendered by component : "+
                        RendererUtils.getPathToComponent(_startElementUIComponent)))+" not explicitly closed");
            }
        }

        if(_startTagOpen)
        {
            closeStartTagIfNecessary();
            if(_startElementName!=null)
            {
                if (isScript() && (_isXhtmlContentType || _wrapScriptContentWithXmlCommentTag))
                {
                    writeScriptContent();
                    _currentWriter = _outputWriter;
                }
                else if (isStyle() && _isXhtmlContentType)
                {
                    writeStyleContent();
                    _currentWriter = _outputWriter;
                }
                writeEndTag(name);
            }
        }
        else
        {
            if (!_useStraightXml && s_emptyHtmlElements.contains(name.toLowerCase()))
            {
            	
            }
            else
            {
                if (isScript() && (_isXhtmlContentType || _wrapScriptContentWithXmlCommentTag))
                {
                    writeScriptContent();
                    _currentWriter = _outputWriter;
                }
                else if (isStyle() && _isXhtmlContentType)
                {
                    writeStyleContent();
                    _currentWriter = _outputWriter;
                }
                writeEndTag(name);
            }
        }
        resetStartedElement();
    }

    private void writeStyleContent() throws IOException  // TODO
    {
        String content = _bufferedWriter.toString();

        if(_isXhtmlContentType)
        {
            String trimmedContent = content.trim();
            if (trimmedContent.startsWith(CommentUtils.CDATA_SIMPLE_START) && trimmedContent.endsWith(CommentUtils.CDATA_SIMPLE_END))
            {
                _outputWriter.write(content);
                return;
            }
            else if (CommentUtils.isStartMatchWithCommentedCDATA(trimmedContent) && CommentUtils.isEndMatchWithCommentedCDATA(trimmedContent))
            {
                _outputWriter.write(content);
                return;
            }
            else if (trimmedContent.startsWith(CommentUtils.COMMENT_SIMPLE_START) && trimmedContent.endsWith(CommentUtils.COMMENT_SIMPLE_END))
            {
                _outputWriter.write(CDATA_START);
                _outputWriter.write(trimmedContent.substring(4,trimmedContent.length()-3));
                _outputWriter.write(CDATA_END);
                return;
            }
            else
            {
                _outputWriter.write(CDATA_START);
                _outputWriter.write(content);
                _outputWriter.write(CDATA_END);
                return;                
            }
        }
        _outputWriter.write(content);
    }
    
    private void writeScriptContent() throws IOException
    {
        String content = _bufferedWriter.toString();
        String trimmedContent = null;
        
        if(_isXhtmlContentType)
        {
            trimmedContent = content.trim();
            
            if ( trimmedContent.startsWith(CommentUtils.COMMENT_SIMPLE_START) && 
                    CommentUtils.isEndMatchtWithInlineCommentedXmlCommentTag(trimmedContent))
            {
                if (_cdataOpen)
                {
                    _outputWriter.write("//\n");
                }
                else
                {
                   _outputWriter.write(CDATA_COMMENT_START);
                }

                _outputWriter.write(trimmedContent.substring(4));

                if (_cdataOpen)
                {
                    _outputWriter.write("\n");
                }
                else
                {
                    _outputWriter.write(CDATA_COMMENT_END);
                }
                
                return;
            }
            else if (CommentUtils.isStartMatchWithCommentedCDATA(trimmedContent) && CommentUtils.isEndMatchWithCommentedCDATA(trimmedContent))
            {
                _outputWriter.write(content);
                return;
            }
            else if (CommentUtils.isStartMatchWithInlineCommentedCDATA(trimmedContent) && CommentUtils.isEndMatchWithInlineCommentedCDATA(trimmedContent))
            {
                _outputWriter.write(content);
                return;
            }
            else
            {
                if (_cdataOpen)
                {
                    _outputWriter.write("//\n");
                }
                else
                {
                   _outputWriter.write(CDATA_COMMENT_START);
                }

                _outputWriter.write(content);

                if (_cdataOpen)
                {
                    _outputWriter.write("\n");
                }
                else
                {
                    _outputWriter.write(CDATA_COMMENT_END);
                }

                return;
            }
        }
        else
        {
            if (_wrapScriptContentWithXmlCommentTag)
            {
                trimmedContent = content.trim();
                
                if ( trimmedContent.startsWith(CommentUtils.COMMENT_SIMPLE_START) && 
                        CommentUtils.isEndMatchtWithInlineCommentedXmlCommentTag(trimmedContent))
                {
                    _outputWriter.write(content);
                    return;
                }
                else if (CommentUtils.isStartMatchWithCommentedCDATA(trimmedContent) && CommentUtils.isEndMatchWithCommentedCDATA(trimmedContent))
                {
                    _outputWriter.write(content);
                    return;
                }
                else if (CommentUtils.isStartMatchWithInlineCommentedCDATA(trimmedContent) && CommentUtils.isEndMatchWithInlineCommentedCDATA(trimmedContent))
                {
                    _outputWriter.write(content);
                    return;
                }
                else
                {
                	//content = ESAPI.encoder().encodeForJavaScript(content);
                    _outputWriter.write(COMMENT_START);
                    _outputWriter.write(content);
                    _outputWriter.write(COMMENT_COMMENT_END);
                    return;
                }
            }
        }
        _outputWriter.write(content);
    }
    
    private void writeEndTag(String name)
        throws IOException
    {
        if (isScript(name))
        {
            // reset _isInsideScript
            _isInsideScript = Boolean.FALSE;
        }
        else if (isStyle(name))
        {
            _isStyle = Boolean.FALSE;
        }
        
        _currentWriter.write("</");
        _currentWriter.write(ESAPI.encoder().encodeForHTML(name));
        _currentWriter.write('>');
    }

    public void writeAttribute(String name, Object value, String componentPropertyName) throws IOException
    {
        if (name == null)
        {
            throw new NullPointerException("attributeName name must not be null");
        }
        if (!_startTagOpen)
        {
            throw new IllegalStateException("Must be called before the start element is closed (attribute '" + name + "')");
        }

        if (value instanceof Boolean)
        {
            if (((Boolean)value).booleanValue())
            {
                _currentWriter.write(' ');
                _currentWriter.write(name);
                _currentWriter.write("=\"");
                _currentWriter.write(name);
                _currentWriter.write('"');
            }
        }
        else
        {
            String strValue = (value==null)?"":value.toString();
            
            _currentWriter.write(' ');
            _currentWriter.write(name);
            _currentWriter.write("=\"");
            //strValue = ESAPI.encoder().encodeForURL(strValue);
             strValue = ESAPI.encoder().encodeForHTML(strValue);
             _currentWriter.write(strValue);
            //_currentWriter.write(org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder.encode(strValue, false, false, !UTF8.equals(_characterEncoding)));
            _currentWriter.write('"');
        }
    }

    public void writeURIAttribute(String name, Object value, String componentPropertyName) throws IOException
    {
        if (name == null)
        {
            throw new NullPointerException("attributeName name must not be null");
        }
        if (!_startTagOpen)
        {
            throw new IllegalStateException("Must be called before the start element is closed (attribute '"+ name + "')");
        }

        String strValue = value.toString();
        _currentWriter.write(' ');
        _currentWriter.write(name);
        _currentWriter.write("=\"");
        if (strValue.toLowerCase().startsWith("javascript:"))
        {
            _currentWriter.write(org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder.encode(strValue, false, false, !UTF8.equals(_characterEncoding)));
        }
        else
        {
        	_currentWriter.write(org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder.encodeURIAtributte(strValue, _characterEncoding));
        }
        _currentWriter.write('"');
    }

    public void writeComment(Object value) throws IOException
    {
    	/**
        if (value == null)
        {
            throw new NullPointerException("comment name must not be null");
        }

        closeStartTagIfNecessary();
        _currentWriter.write("<!--");
        _currentWriter.write(value.toString());    //TODO: Escaping: must not have "-->" inside!
        _currentWriter.write("-->");
        **/
    }

    public void writeText(Object value, String componentPropertyName) throws IOException // JSoup
    {
        if (value == null)
        {
            throw new NullPointerException("Text must not be null.");
        }

        closeStartTagIfNecessary();

        String strValue = value.toString();
        if (isScriptOrStyle())
        {
            if (UTF8.equals(_characterEncoding))
            {
            	//String safe = Jsoup.clean(strValue, Whitelist.basic());
            	//_currentWriter.write(safe);
            	_currentWriter.write(strValue);
            }
            else
            {
            	//_currentWriter.write(UnicodeEncoder.encode(strValue) );
            	String encodedValue = ESAPI.encoder().encodeForHTML(strValue);
                _currentWriter.write(encodedValue);
            }
        }
        else
        {
        	 String encodedValue = ESAPI.encoder().encodeForHTML(strValue);
            _currentWriter.write(encodedValue);
        }
    }

    public void writeText(char cbuf[], int off, int len) throws IOException
    {
        if (cbuf == null)
        {
            throw new NullPointerException("cbuf name must not be null");
        }
        
        if (cbuf.length < off + len)
        {
            throw new IndexOutOfBoundsException((off + len) + " > " + cbuf.length);
        }

        closeStartTagIfNecessary();

        if (isScriptOrStyle())
        {
            String strValue = new String(cbuf, off, len);
            
            if (UTF8.equals(_characterEncoding))
            {
            	//String safe = Jsoup.clean(strValue, Whitelist.basic());
            	//_currentWriter.write(safe);
            	_currentWriter.write(strValue);
            }
            else
            {
            	String encodedValue = ESAPI.encoder().encodeForHTML(strValue);
                _currentWriter.write(encodedValue);
                // 	_currentWriter.write(UnicodeEncoder.encode(strValue) );
            }
        }
        else if (isTextarea())
        {
            org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder.encode(cbuf, off, len, false, false, !UTF8.equals(_characterEncoding), _currentWriter);
        }
        else
        {
            org.apache.myfaces.shared.renderkit.html.util.HTMLEncoder.encode(cbuf, off, len, true, true, !UTF8.equals(_characterEncoding), _currentWriter);
        }
        //UnicodeEncoder.encode("test");
    }

    private boolean isScriptOrStyle()
    {
    	return (_isStyle != null && _isStyle.booleanValue()) || (_isInsideScript != null && _isInsideScript.booleanValue());
    }
    
    private boolean isScript(String element)
    {
        return (HTML.SCRIPT_ELEM.equalsIgnoreCase(element));
    }
    
    private boolean isScript()
    {
        return (_isInsideScript != null && _isInsideScript.booleanValue());
    }
    
    private boolean isStyle(String element)
    {
        return (HTML.STYLE_ELEM.equalsIgnoreCase(element));
    }
    
    private boolean isStyle()
    {
        return (_isStyle != null && _isStyle.booleanValue());
    }

    private boolean isTextarea()
    {
        initializeStartedTagInfo();

        return _isTextArea != null && _isTextArea.booleanValue();
    }

    private void initializeStartedTagInfo()
    {
        if(_startElementName != null)
        {
            if(_isTextArea == null)
            {
                if(_startElementName.equalsIgnoreCase(HTML.TEXTAREA_ELEM))
                {
                    _isTextArea = Boolean.TRUE;
                }
                else
                {
                    _isTextArea = Boolean.FALSE;
                }
            }
        }
    }

    public ResponseWriter cloneWithWriter(Writer writer)
    {
    	EsapiHtmlResponseWriterImpl newWriter = new EsapiHtmlResponseWriterImpl(writer, getContentType(), getCharacterEncoding(), _wrapScriptContentWithXmlCommentTag);
        //HtmlResponseWriterImpl newWriter = new HtmlResponseWriterImpl(writer, getContentType(), getCharacterEncoding(), _wrapScriptContentWithXmlCommentTag);
        return newWriter;
    }

    public void write(char cbuf[], int off, int len) throws IOException  // Tested
    {
        closeStartTagIfNecessary();
        String strValue = new String(cbuf, off, len);
        if (UTF8.equals(_characterEncoding))
       	{	
        	//String safe = Jsoup.clean(strValue, Whitelist.basic());
        	_currentWriter.write(strValue);
        }
        else
        {	
        	_currentWriter.write(UnicodeEncoder.encode(strValue));
        }
    }

    public void write(int c) throws IOException
    {
        closeStartTagIfNecessary();
        _currentWriter.write(c);
    }

    public void write(char cbuf[]) throws IOException // Tested
    {
        closeStartTagIfNecessary();
        String strValue = new String(cbuf);
        if (UTF8.equals(_characterEncoding))
        {
        	//String safe = Jsoup.clean(strValue, Whitelist.basic());
        	//_currentWriter.write(safe);
        	_currentWriter.write(strValue);
        }
        else
        {	
        	/**String encodedValue = ESAPI.encoder().encodeForHTML(strValue);
            _currentWriter.write(encodedValue);**/
        	_currentWriter.write(UnicodeEncoder.encode(strValue));
        }
    }

    public void write(String str) throws IOException
    {
    	try
    	{
    		/**String unsafe = 
      		  "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
      		  **/
    		
      		// now: <p><a href="http://example.com/" rel="nofollow">Link</a></p>
      	
	        closeStartTagIfNecessary();
	        if (str.length() > 0)
	        {
            	Whitelist whitelist = new Whitelist();
            	whitelist = whitelist.basicWithImages();
            	String safe = "";
	            if (UTF8.equals(_characterEncoding))
	            {
	            	//safe = Jsoup.clean(str, whitelist);
	            	if(str != null && ! str.trim().equals(""))
	            	{
	            		safe = getSafeHtmlCode(str);
		            	_currentWriter.write(safe);	
		            }
	            
	            }
	            else
	            {
	            	//_currentWriter.write(UnicodeEncoder.encode(str));
	            	 safe = Jsoup.clean(UnicodeEncoder.encode(str), whitelist);
	            	_currentWriter.write(safe);
	            }
	        }
	        
    	}
    	catch(Exception exception)
    	{
    		exception.printStackTrace();
    	}
    }
    
    public void write(String str, int off, int len) throws IOException
    {
    	closeStartTagIfNecessary();
        String strValue = str.substring(off, off+len);
        if (UTF8.equals(_characterEncoding))
        {
      		//String safe = Jsoup.clean(strValue, Whitelist.basic());
        	if(strValue != null && ! strValue.trim().equals(""))
        	{
            	String safe = getSafeHtmlCode(strValue);
            	_currentWriter.write(safe);
        	}
        	//_currentWriter.write(strValue);
        }
        else
        {
        
        	_currentWriter.write(UnicodeEncoder.encode(strValue) );
        }
    }
    
    public void writeText(Object object, UIComponent component, String string) throws IOException
    {
        writeText(object,string);
    }
    
    private String  getSafeHtmlCode(String textValue)
    {
    	String newTextValue;
    	
    	try
		{
	        org.owasp.esapi.Validator instance = ESAPI.validator();
	        ValidationErrorList errors = new ValidationErrorList();
	
	        // new school test case setup
	        HTMLValidationRule rule = new HTMLValidationRule("test");
	        ESAPI.validator().addRule(rule);
	
	        newTextValue = (String)ESAPI.validator().getRule("test").getValid("test", textValue);
		}
		catch(Exception exception)
		{
			newTextValue = textValue;
			exception.printStackTrace();
		}
		
		return textValue;
    }
    
}

