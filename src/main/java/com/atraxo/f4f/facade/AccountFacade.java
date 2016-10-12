package com.atraxo.f4f.facade;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.atraxo.f4f.dao.AccountDAO;
import com.atraxo.f4f.model.account.Account;

public class AccountFacade implements Serializable {
	private static final long serialVersionUID = 3943835784771790977L;
	private static final Logger LOGGER = Logger.getLogger(AccountFacade.class);
	
	private AccountDAO accountDAO = new AccountDAO();
	
	public void create(Account account) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START create()");
		}
		
		accountDAO.beginTransaction();
		accountDAO.save(account);
		accountDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END create()");
		}
	}
	
	public void update(Account account) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START update()");
		}
		
		accountDAO.beginTransaction();
		accountDAO.update(account);
		accountDAO.commitAndCloseTransaction();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END update()");
		}
	}
	
	public List<Account> listAll(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START listAll()");
		}
		
		accountDAO.beginTransaction();
		List<Account> persistedAccounts = accountDAO.findAll();
		accountDAO.commitAndCloseTransaction();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END listAll()");
		}
		
		return persistedAccounts;
	}
	
	public Account findById(int id){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findUserById()");
		}
		
		accountDAO.beginTransaction();
		Account persistedAccount = accountDAO.find(id);
		accountDAO.commitAndCloseTransaction();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findUserById()");
		}
		
		return persistedAccount;
	}
	
	public Account findByUsername(String username){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByUsername()");
		}
		
		accountDAO.beginTransaction();
		Account persistedAccount = accountDAO.findByUsername(username);
		accountDAO.commitAndCloseTransaction();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByUsername()");
		}
		
		return persistedAccount;
	}
	
	public String getNewIdentificationNumber(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getLastIdentificationNumber()");
		}
		
		accountDAO.beginTransaction();
		String idNo = accountDAO.getLastIdentificationNumber();
		accountDAO.commitAndCloseTransaction();
		
		int max = Integer.parseInt(idNo.substring(1)) + 1;
		idNo = idNo.substring(0,1) + max;
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getLastIdentificationNumber()");
		}
		
		return idNo;
	}
	
}
