package com.atraxo.f4f.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.account.Account;

public class AccountDAO extends GenericDAO<Account>{
	private static final long serialVersionUID = 5768352556373979051L;
	private static final Logger LOGGER = Logger.getLogger(AccountDAO.class);
	
	public AccountDAO() {
		super(Account.class);
	}
	
	public Account findByUsername(String username){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByUsername()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", username);
		Account foundAccount = super.findOneResult(Account.FIND_BY_USERNAME, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByUsername()");
		}
		return foundAccount;
	}
	
	public String getLastIdentificationNumber(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getLastIdentificationNumber()");
		}
		
		Query query = em.createQuery("Select max(a.idNo) From Account a");
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getLastIdentificationNumber()");
		}

		return (String) query.getSingleResult();
	}
	
}
