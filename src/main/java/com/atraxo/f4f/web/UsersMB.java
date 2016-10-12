package com.atraxo.f4f.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.AccountFacade;
import com.atraxo.f4f.facade.RoleFacade;
import com.atraxo.f4f.facade.UserFacade;
import com.atraxo.f4f.model.account.Account;
import com.atraxo.f4f.model.permission.Role;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;

@ViewScoped
@ManagedBean
public class UsersMB extends AbstractMB{
	private static final long serialVersionUID = -3065504744359785192L;
	private static final Logger LOGGER = Logger.getLogger(UsersMB.class);
	
	private UserFacade userFacade;
	private RoleFacade roleFacade;
	private AccountFacade accountFacade;
	
	private User user;
	private List<User> users;
	
	public UsersMB() {
		super();
		userFacade = new UserFacade();
		roleFacade = new RoleFacade();
		accountFacade = new AccountFacade();
	}
	
	/**
	 * 
	 */
	public void createUser(){
		try{
			users.add(user);
			userFacade.create(user);
			
			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_CREATE_OK));
		}catch (Exception e) {
			LOGGER.error("Could not create user", e);
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_CREATE_NOT_OK));
		}
	}
	
	/**
	 * 
	 */
	public void updateUser(){
		try{
			userFacade.update(user);
			
			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_UPDATE_OK));
		}catch (Exception e) {
			LOGGER.error("Could not update user", e);
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_UPDATE_NOT_OK));
		}
	}
	
	/**
	 * @param user
	 */
	public void deleteUser(User user){
		try{
			//delete the specified user
			users.remove(user);
			userFacade.delete(user);
			
			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_DELETE_OK));
		}catch (Exception e) {
			LOGGER.error("Could not delete user", e);
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_DELETE_NOT_OK));
		}
	}

	public RoleFacade getRoleFacade() {
		return roleFacade;
	}
	
	public AccountFacade getAccountFacade() {
		return accountFacade;
	}

	public List<User> getUsers() {
		if( users == null ){
			users = userFacade.listAll();
		}
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}
	
	public void instantiateNewUser(){
		user = new User();
		user.setAccount(new Account());
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public List<Role> getAllActiveRoles(){
		return roleFacade.listAll();
	}
	
}
