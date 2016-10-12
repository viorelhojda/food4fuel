package com.atraxo.f4f.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.atraxo.f4f.util.WebPage;

@ManagedBean
@SessionScoped
public class MenuMB extends AbstractMB{

	private static final long serialVersionUID = -2585834782285696699L;
	
	public static final String INJECTION_NAME = "#{menuMB}";
	
	private String currentPage;

	public MenuMB() {
		super();
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
	public String goToLogin() {
		String page = WebPage.PAGE_LOGIN;
		setCurrentPage(page);
		return page;
	}
	
	public String goToDashboard() {
		String page = WebPage.PAGE_DASHBOARD;
		setCurrentPage(page);
		return page;
	}
	
	public String goToJobs() {
		String page = WebPage.PAGE_JOBS;
		setCurrentPage(page);
		return page;
	}
	
	public String goToUsers() {
		String page = WebPage.PAGE_USERS;
		setCurrentPage(page);
		return page;
	}
	
	public String goToRoles() {
		String page = WebPage.PAGE_ROLES;
		setCurrentPage(page);
		return page;
	}
	
	public String goToMenuSetup(){
		String page = WebPage.PAGE_MENU_SETUP;
		setCurrentPage(page);
		return page;
	}
	
	public String goToOrderFood(){
		String page = WebPage.PAGE_ORDER_FOOD;
		setCurrentPage(page);
		return page;
	}
	
	public String goToCollectMoney(){
		String page = WebPage.PAGE_COLLECT_MONEY;
		setCurrentPage(page);
		return page;
	}
	
	public String goToManifest() {
		return WebPage.PAGE_MANIFEST;
	}
	
}
