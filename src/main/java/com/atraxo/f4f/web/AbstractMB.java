package com.atraxo.f4f.web;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.atraxo.f4f.util.JSFMessageUtil;
import com.atraxo.f4f.util.MessageBundleUtils;

public class AbstractMB implements Serializable{
	private static final long serialVersionUID = 6166841845976002684L;

	private static final String KEEP_DIALOG_OPENED = "KEEP_DIALOG_OPENED";
	private static final Logger LOGGER = Logger.getLogger(AbstractMB.class);
	
	public AbstractMB() {
		super();
	}
	
	public void displayErrorMessageToUser(String message) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START displayErrorMessageToUser()");
		}
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendErrorMessageToUser(message);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END displayErrorMessageToUser()");
		}
	}

	public void displayWarningMessageToUser(String message) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START displayWarningMessageToUser()");
		}
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendWarningMessageToUser(message);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END displayWarningMessageToUser()");
		}
	}

	public void displayInfoMessageToUser(String message) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START displayInfoMessageToUser()");
		}

		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendInfoMessageToUser(message);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END displayInfoMessageToUser()");
		}
	}

	public void closeDialog(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START closeDialog()");
		}
		getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, false);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END closeDialog()");
		}
	}

	public void keepDialogOpen(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START keepDialogOpen()");
		}

		getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, true);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END keepDialogOpen()");
		}
	}
	
	public String getText(String messageCode){
		return MessageBundleUtils.getMessage(messageCode);
	}

	protected RequestContext getRequestContext(){
		return RequestContext.getCurrentInstance();
	}

}
