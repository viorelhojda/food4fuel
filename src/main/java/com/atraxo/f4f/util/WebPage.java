package com.atraxo.f4f.util;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class WebPage {
	
	private static final Logger LOGGER = Logger.getLogger(WebPage.class);
	
	public static final String EXTENSION_SIMPLE = ".xhtml";
	public static final String EXTENSION = ".xhtml?faces-redirect=true";
	
	//public pages
	public static final String PAGE_404 = "/pages/404" + EXTENSION;
	public static final String PAGE_ERROR = "/pages/error" + EXTENSION;
	public static final String PAGE_LOGIN = "/pages/login" + EXTENSION;
	public static final String PAGE_ACCESS_DENIED = "/pages/access-denied" + EXTENSION;
	
	//private pages
	public static final String PAGE_DASHBOARD = "/pages/default/dashboard" + EXTENSION;
	
	public static final String PAGE_JOBS = "/pages/private/job/list" + EXTENSION_SIMPLE;
	
	public static final String PAGE_USERS = "/pages/private/user/list" + EXTENSION_SIMPLE;
	
	public static final String PAGE_ROLES = "/pages/private/role/list" + EXTENSION_SIMPLE;
	
	public static final String PAGE_MENU_SETUP = "/pages/private/menu/menuSetup" + EXTENSION_SIMPLE;
	
	public static final String PAGE_ORDER_FOOD = "/pages/private/menu/orderFood" + EXTENSION_SIMPLE;
	
	public static final String PAGE_COLLECT_MONEY = "/pages/private/menu/collectMoney" + EXTENSION_SIMPLE;

	public static final String PAGE_MANIFEST = "/pages/default/manifest" + EXTENSION;
	
	private WebPage(){
		//private constructor
	}
	
	public static final void redirectToPage(String page) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext extContext = context.getExternalContext();
			String url = extContext.encodeActionURL(extContext
					.getRequestContextPath() + page);
			extContext.redirect(url);
		} catch (IOException e) {
			LOGGER.error("ERROR:Could not redirect to " + page + " !", e);
		}
	}
	
}
