package com.atraxo.f4f.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.permission.RoleRight;

public class RoleRightDAO extends GenericDAO<RoleRight>{
	private static final long serialVersionUID = -1242588026502310L;
	private static final Logger LOGGER = Logger.getLogger(RoleRightDAO.class);
	
	public RoleRightDAO() {
		super(RoleRight.class);
	}
	
	public RoleRight findByRoleAndRight(Integer roleID, Integer rightID) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByRoleAndRight()");
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("roleID", roleID);
		parameters.put("rightID", rightID);

		RoleRight foundRole = super.findOneResult(RoleRight.FIND_BY_ROLE_RIGHT, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByRoleAndRight()");
		}

		return foundRole;
	}
}
