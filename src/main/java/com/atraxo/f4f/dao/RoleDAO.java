package com.atraxo.f4f.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.permission.Role;

public class RoleDAO extends GenericDAO<Role>{
	private static final long serialVersionUID = -1679695094364446317L;
	private static final Logger LOGGER = Logger.getLogger(RoleDAO.class);
	
	public RoleDAO() {
		super(Role.class);
	}
	
	public Role findByCode(String code) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByCode()");
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("code", code);

		Role foundRole = super.findOneResult(Role.FIND_BY_CODE, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByCode()");
		}

		return foundRole;
	}
	
	public List<Role> findAllActive() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findAllActive()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		
		List<Role> roles = super.findMultipleResults(Role.FIND_ALL_ACTIVE, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findAllActive()");
		}
		
		return roles;
	}
}
