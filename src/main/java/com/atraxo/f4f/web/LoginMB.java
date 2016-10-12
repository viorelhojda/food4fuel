package com.atraxo.f4f.web;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.UserFacade;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;
import com.atraxo.f4f.util.WebPage;

@ManagedBean
@RequestScoped
public class LoginMB extends AbstractMB {
	
	private static final long serialVersionUID = 5820965293402601903L;
	private static final Logger LOGGER = Logger.getLogger(LoginMB.class);
	
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;
	
	private String xml;
	private String response;
	
	private String username;
	private String password;
	
	private transient FacesContext context;
	
	public String login(){
		
		String page = null;
		
		User user = null;
		UserFacade userFacade = new UserFacade();
		
		try {
			user = userFacade.isValidLogin(username, password);
		} 
		catch (IOException e) {
			LOGGER.debug("WARNING: the login is not valid !" + e );
		}
		
		if( user != null && user.getActive() ){
			initiateContext();
			
			userMB.setUser(user);
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			
			page = WebPage.PAGE_DASHBOARD;
		}
		else{
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_LOGIN_VALIDATION));
		}
		
		return page;
		
	}
	
	private void initiateContext(){
		if ( context == null ){
			context = FacesContext.getCurrentInstance();
		}

		if ( userMB == null ){
			UserMB managedUserMB = context.getApplication().evaluateExpressionGet(context,UserMB.INJECTION_NAME, UserMB.class); 
			setUserMB(managedUserMB);
		}
	}
	
	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public FacesContext getContext() {
		return context;
	}

	public void setContext(FacesContext context) {
		this.context = context;
	}
	
}
