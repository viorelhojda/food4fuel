package com.atraxo.f4f.facade;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.atraxo.f4f.dao.RoleRightDAO;
import com.atraxo.f4f.model.permission.RoleRight;

public class RoleRightFacade implements Serializable{
	private static final long serialVersionUID = -6432121623564112276L;
	private static final Logger LOGGER = Logger.getLogger(RoleRightFacade.class);
	
	private RoleRightDAO roleRightDAO = new RoleRightDAO();
	
	public void create(RoleRight roleRight) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START createRoleRight()");
		}
		roleRightDAO.beginTransaction();
		roleRightDAO.save(roleRight);
		roleRightDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END createRoleRight()");
		}
	}
	
	public void update(RoleRight roleRight) {
		roleRightDAO.beginTransaction();
		roleRightDAO.update(roleRight);
		roleRightDAO.commitAndCloseTransaction();
	}
	
	public void delete(RoleRight roleRight) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START deleteRoleRight()");
		}
		roleRightDAO.beginTransaction();
		roleRightDAO.delete(roleRight);
		roleRightDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END deleteRoleRight()");
		}
	}
	
	public List<RoleRight> listAll(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START listAll()");
		}
		roleRightDAO.beginTransaction();
		List<RoleRight> persistedRoles = roleRightDAO.findAll();
		roleRightDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END listAll()");
		}
		return persistedRoles;
	}
	
	public RoleRight findByRoleAndRight(Integer roleID, Integer rightID) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByRoleAndRight()");
		}
		roleRightDAO.beginTransaction();
		RoleRight persistedRole = roleRightDAO.findByRoleAndRight(roleID, rightID);
		roleRightDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByRoleAndRight()");
		}
		return persistedRole;
	}
	
	
}
