package com.atraxo.f4f.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class JSFMessageUtil {
	private static final Logger LOGGER = Logger.getLogger(JSFMessageUtil.class);
	
	public void sendInfoMessageToUser(String message) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendInfoMessageToUser()");
		}
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO, message);
		addMessageToJsfContext(facesMessage);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END sendInfoMessageToUser()");
		}
	}

	public void sendErrorMessageToUser(String message) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendErrorMessageToUser()");
		}
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_ERROR, message);
		addMessageToJsfContext(facesMessage);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END sendErrorMessageToUser()");
		}
	}
	
	public void sendWarningMessageToUser(String message) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START sendWarningMessageToUser()");
		}
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_WARN, message);
		addMessageToJsfContext(facesMessage);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END sendWarningMessageToUser()");
		}
	}

	private static FacesMessage createMessage(Severity severity, String mensageError) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START createMessage()");
		}
		FacesMessage facesMessage = new FacesMessage(severity, mensageError, mensageError);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END createMessage()");
		}
		return facesMessage;
	}

	private static void addMessageToJsfContext(FacesMessage facesMessage) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START addMessageToJsfContext()");
		}
		
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END addMessageToJsfContext()");
		}
	}
}
