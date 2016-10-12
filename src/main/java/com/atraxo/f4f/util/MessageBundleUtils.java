package com.atraxo.f4f.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class MessageBundleUtils {
	private static final Logger LOGGER = Logger.getLogger(MessageBundleUtils.class);

	private static Locale defaultLocale = Locale.ENGLISH;
	
	private MessageBundleUtils(){
	}
	
	private static ResourceBundle getProperties(){
		return ResourceBundle.getBundle(
				Constants.BUNDLE_MESSAGES, getLocale() );
	}
	
	private static Locale getLocale(){
		if ( FacesContext.getCurrentInstance() != null  
				&& FacesContext.getCurrentInstance().getViewRoot() != null
					&& FacesContext.getCurrentInstance().getViewRoot().getLocale() != null ){
			return FacesContext.getCurrentInstance().getViewRoot().getLocale();
		}
		
		return defaultLocale;
	}
	
	public static String getCurrentLanguage(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getCurrentLanguage()");
		}
		
		String language = getProperties().getLocale().toString();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getCurrentLanguage()");
		}
		return language;
	}
	
	public static final String getMessage(String msgKey){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getMessage()");
		}
		
		String retStr = null;
		try {
			retStr = getProperties().getString(msgKey);
		} catch (MissingResourceException mre) {
			retStr = msgKey;
			LOGGER.info("Could not get message for : " + msgKey, mre);
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getMessage()");
		}

		return retStr;
	}

	public static final String getMessage(String msgPatternKey, Object...props ){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getMessage()");
		}
		
		String retStr = MessageFormat.format(getProperties().getString(msgPatternKey), props );

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getMessage()");
		}

		return retStr;
	}

	public static String getNAMessage(){
		return getMessage(Constants.MSG_NA);
	}

}
