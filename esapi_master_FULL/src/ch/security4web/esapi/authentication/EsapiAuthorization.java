package ch.security4web.esapi.authentication;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.Authenticator;
import org.owasp.esapi.User;
import org.owasp.esapi.reference.FileBasedAuthenticator;

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


 /** 
 * The EsapiAuthorized class stores the users information in the user.txt file, that information contains
 * user's role, user's last login time, etc. and based on the information, the users are given the rights to
 * access the presentation layer of the JSF application. The presentation layer is nothing but .XHTML or JSP file
 * 
 * <p>
 * For example :- <br>
 * {@code <esapi:authorization role="admin">} <br>
 * {@code Admin Content.}<br>
 * {@code <esapi:authorization> } 
 * </p>
 * 
 * As shown in the above code, The role attribute "admin" of the <esapi:authorization> tag show  the 
 * content inside the tag is accessible to the admin user only. The user with other role can not have 
 * access to the content above.
 * 
 * Moreover, the user with "admin" role can have full access to the content of entire page, however,
 * the normal user can not see them, because they don't have right's to see like Admin user.
 * 
 * <p>
 * For example :- <br>
 * {@code <esapi:authorization role="user">}<br>
 * {@code User Content.}<br>
 * {@code </esapi:authorization> } <br> 
 * <p>
 * 
 * </p>
 * The User with "user" role could see only data, which is inside the tag but other important 
 * data with other roles are not visualized to them.
 * </p>
 *
 */
@FacesComponent(value = "esapiAuthorization")
@ResourceDependency(name = "authorization")
public class EsapiAuthorization extends UIOutput
{
	private String role;
	private static List<UIComponent> children;
	
	/** 
     * constructor.
     * @since 1.0
     */
	public EsapiAuthorization()
	{
		
	}
	
	/**
	 * Save state of role associated with authorization tag in facelest context.
	 * @param context  facelet Context
	 * @return Array object which has context in first place(location) and return role in second place(location). 
	 * @since 1.0
	 */
	
	@Override
    public Object saveState(FacesContext context) 
	{
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = role;
        return ((Object) (values));
    }

	/**
	 * Restore value of role in existing facelet context
	 * @param context  facelet context 
	 * @param state  state of role to be restored in existing context.
	 * 
	 * @return null
	 * @since 1.0
	 */
	
    @Override
    public void restoreState(FacesContext context, Object state) 
    {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        role = (String)values[1];
    }
    
        
    /**
     * Give family of this tag
     * @return tag belongs to esapi.authorization family.
     * @since 1.0
     */
   	public String getFamily()
   	{
   		return "esapi.authorization";
    }
   	
   	/**
   	 * Get role associated with authorization tag.
   	 * @return Role name
   	 * @since 1.0
   	 */
	public String getRole() 
	{
		return role;
	}
	
	/**
	 *  Role is set which is associated with authorization tag.<br>
	 * {@code "admin","user" ext.} 
	 * @param role role name 
	 * @return null
	 */
	public void setRole(String role) 
	{
		this.role = role;
	}
	
    /**
     * This method render component and sub component inside authorization tag base of user authorization.
     * {@code role="admin"} could see all page content.
     * {@code role="user"} could visualized only public content on the page and {@code "user"}.
     * but not able to see {@code "admin"} content.
     * @param context facelet context
     * @exception IOException throws IOException when user is not found inside user.txt file.
     * @return null
     * @since 1.0
     */
    public void encodeBegin(FacesContext context) throws IOException 
    {
    	Authenticator authenticator = FileBasedAuthenticator.getInstance(); 
    	User user =authenticator.getCurrentUser();
    	if(user != null)
    	{
    		
    		System.out.println("***User Name :"+user.getAccountName());
    		User fileBaseUser = authenticator.getUser(user.getAccountName());
    		if(fileBaseUser != null)
    		{
    			Set<String> roles =fileBaseUser.getRoles();
    			
    			boolean roleFlag = false;
    			
    			Set<String> currentUserRoles = user.getRoles();
    			Iterator<String> iterCurrentUserRole = currentUserRoles.iterator();
    			while(iterCurrentUserRole.hasNext())
    			{
    				String userRole = iterCurrentUserRole.next();
    				if(roles.contains(userRole) && roles.contains(role))
    				{
    					roleFlag = true;
    				}
    			}
    			
    			if(!roleFlag)
    			{
    				FacesContext facesContext = FacesContext.getCurrentInstance();
    		        ExternalContext externalContext = facesContext.getExternalContext();
    		        HttpServletRequest httpServletRequest = (HttpServletRequest)externalContext.getRequest();

    		        //getSession(false), which returns null if no session already exists for the current client.
    		        HttpSession session =(HttpSession)externalContext.getSession(false);

    		    	List<String> blackList= (List<String>) session.getAttribute("blackList");
    		    	if(blackList == null){
    		    		blackList = new LinkedList<String>();
    		    	}
    				
    		    	System.out.println("*********************Enter to encodeBegin Function");
    				disableRec(this,blackList);	
    				
    				// set blackList in seesion
    				session.setAttribute("blackList", blackList);
    				
    		    	System.out.println("*********************Length of blacklist"+ session.getAttribute("blackList"));

    				List<UIComponent> uiList = getChildren();
    	        	
    	        	Iterator<UIComponent> iter = uiList.iterator();
    	        	while(iter.hasNext())
    	        	{
    	        		UIComponent uiComponent = iter.next();
    	        		getUIComponent(uiComponent);
    	        		uiComponent.setRendered(false);
    	        		uiComponent.setInView(false);
    	        		
    	        		//String id=uiComponent.getClientId();
    	        		/**javax.faces.component.html.HtmlOutputText htmlOutputText = new HtmlOutputText();
    	        		if(uiComponent.getClass() == htmlOutputText.getClass())
    	        		{
    	        			HtmlOutputText htmlOutputText1 = (HtmlOutputText)uiComponent;
    	        		}
    	        		**/
    	        	}
    			}
    			
    				
    		}
    		else
    		{
    			throw new IOException("User is not stored in current session");
    		}
    		
    	}
    	
    }
    
    /**
     * This method recursively calls and enables or disables all sub component base on user role inside tag.
     * {@code <esapi:authorization role="admin">} <br>
     * {@code <h:outputText name="user.sellData">}<br>
     * {@code <h:outputText name="user.taxInfo">}<br>
     * {@code </esapi:authorization>}<br>
     *  
     * @param mainUIComponent parentComponent
     * @return null
     */
    private void getUIComponent(UIComponent mainUIComponent)
    {
    	Iterator iter = mainUIComponent.getChildren().iterator();
    	while(iter.hasNext())
    	{
    		UIComponent uiComponent = (UIComponent)iter.next();
    		if(uiComponent.getChildCount() > 0)
    		{
    			getUIComponent(uiComponent);
    		}
    		uiComponent.setInView(false);
    		
    		/**javax.faces.component.html.HtmlOutputText htmlOutputText = new HtmlOutputText();
    		if(uiComponent.getClass() == htmlOutputText.getClass())
    		{
    			HtmlOutputText htmlOutputText1 = (HtmlOutputText)uiComponent;
    		}
    		**/
    	}
    }
    
    // function to add all node id's in a blackList
    protected static void disableRec(UIComponent c,List<String> blackList){


    	System.out.println("disable"+c.getClientId());
    	blackList.add(c.getClientId());
    	children = c.getChildren();

    	for(UIComponent child : children){
    		disableRec(child,blackList);
    	}
    }

    /**protected static List<UIComponent> getChildrenList(){
	
    	return children;
    }*/
    
}
