package com.atraxo.f4f.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.permission.RightEnum;
import com.atraxo.f4f.model.user.User;

public class UserDAO extends GenericDAO<User> {
	
	private static final long serialVersionUID = -1809470600513887122L;
	private static final Logger LOGGER = Logger.getLogger(UserDAO.class);
	
	public UserDAO() {
		super(User.class);
	}
	
	public User findUserByUsername(String username){
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", username);
		return findOneResult(User.FIND_BY_USERNAME, parameters);
	}

	public List<User> findAllByRole(int roleID) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findAllByRole()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("roleID", roleID);
		
		List<User> users = findMultipleResults(User.FIND_BY_ROLE, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findAllByRole()");
		}
		
		return users;
	}
	
	public List<User> findAllActive() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findAllActive()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		List<User> users = findMultipleResults(User.FIND_ALL_ACTIVE, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findAllActive()");
		}
		
		return users;
	}

	public List<User> findByRight(RightEnum rightEnum) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByRight()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("right", rightEnum);
		
		List<User> users = findMultipleResults(User.FIND_BY_RIGHT, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByRight()");
		}
		
		return users;
	}
}
