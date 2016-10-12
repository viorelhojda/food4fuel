package com.atraxo.f4f.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.UserFacade;
import com.atraxo.f4f.model.permission.RightEnum;
import com.atraxo.f4f.model.permission.RoleRight;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;
import com.atraxo.f4f.util.WebPage;

@ManagedBean
@SessionScoped
public class UserMB extends AbstractMB implements Serializable{
	private static final long serialVersionUID = -8308507133719773858L;
	private static final Logger LOGGER = Logger.getLogger(UserMB.class);
	
	public static final String INJECTION_NAME = "#{userMB}";
	
	private User user;
	private UserFacade userFacade = new UserFacade();
	private List<RightEnum> userRights;
	
	private String oldPassword;
	private String newPassword;
	
	public UserMB() {
		super();
	}
	
	/**
	 * 
	 */
	private void populateUserRights(){
		userRights = new ArrayList<>();
		for( RoleRight rr : user.getRole().getRights() ){
			userRights.add(rr.getRight().getCode());
		}
	}
	
	/**
	 * @return
	 */
	public String logOut() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START logOut()");
		}
		
		userFacade.logout(user);
		
		String page = WebPage.PAGE_LOGIN;

		getRequest().getSession().invalidate();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END logOut()");
		}
		
		return page;
	}
	
	/**
	 * 
	 */
	public void updatePreferences(){
		try{
			userFacade.update(user);
			
			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_UPDATE_OK));
		}catch (Exception e) {
			LOGGER.error("Could not update user", e);
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_UPDATE_NOT_OK));
		}
	}
	
	
	/**
	 * 
	 */
	public void changePassword(){
		try {
			String newEncryptedPass = userFacade.changePassword(user, oldPassword, newPassword);
			user.getAccount().setPassword(newEncryptedPass);
			
			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_PASSWORD_CHANGE_OK));
		} catch (Exception e) {
			LOGGER.error("Could not change password !", e);
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_PASSWORD_CHANGE_NOT_OK));
		}
		
	}
	
	public boolean permissionJobView(){
		return userRights.contains(RightEnum.JOB_VIEW);
	}
	
	public boolean permissionJobUpdate(){
		return userRights.contains(RightEnum.JOB_UPDATE);
	}
	
	public boolean permissionUserView(){
		return userRights.contains(RightEnum.USER_VIEW);
	}
	
	public boolean permissionUserCreate(){
		return userRights.contains(RightEnum.USER_CREATE);
	}
	
	public boolean permissionUserUpdate(){
		return userRights.contains(RightEnum.USER_UPDATE);
	}
	
	public boolean permissionRoleView(){
		return userRights.contains(RightEnum.ROLE_VIEW);
	}
	
	public boolean permissionRoleCreate(){
		return userRights.contains(RightEnum.ROLE_CREATE);
	}
	
	public boolean permissionRoleUpdate(){
		return userRights.contains(RightEnum.ROLE_UPDATE);
	}
	
	public boolean permissionUserMenuSetup(){
		return userRights.contains(RightEnum.MENU_SETUP);
	}
	
	public boolean permissionUserOrderFood(){
		return userRights.contains(RightEnum.ORDER_FOOD);
	}
	public boolean permissionCollectMoney(){
		return userRights.contains(RightEnum.COLLECT_MONEY);
	}
	
	private static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
		populateUserRights();
	}
	
	public List<RightEnum> getUserRights() {
		return userRights;
	}
	public void setUserRights(List<RightEnum> userRights) {
		this.userRights = userRights;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


}
