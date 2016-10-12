package com.atraxo.f4f.facade;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.atraxo.f4f.dao.RoleDAO;
import com.atraxo.f4f.model.permission.Role;

public class RoleFacade implements Serializable{
	private static final long serialVersionUID = 7307424241868625824L;
	private static final Logger LOGGER = Logger.getLogger(RoleFacade.class);
	
	private RoleDAO roleDAO = new RoleDAO();
	
	public void create(Role role) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START createRole()");
		}
		roleDAO.beginTransaction();
		roleDAO.save(role);
		roleDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END createRole()");
		}
	}
	
	public void update(Role role) {
		roleDAO.beginTransaction();
		roleDAO.update(role);
		roleDAO.commitAndCloseTransaction();
	}
	
	public void delete(Role role) {
		roleDAO.beginTransaction();
		roleDAO.delete(role);
		roleDAO.commitAndCloseTransaction();
	}
	
	public List<Role> listAll(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START listAll()");
		}
		roleDAO.beginTransaction();
		List<Role> persistedRoles = roleDAO.findAll();
		roleDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END listAll()");
		}
		return persistedRoles;
	}
	
	public List<Role> listAllActive() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START listAllActive()");
		}
		roleDAO.beginTransaction();
		List<Role> persistedRoles = roleDAO.findAllActive();
		roleDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END listAllActive()");
		}
		return persistedRoles;
	}
	
	public Role findById(int id) {
		roleDAO.beginTransaction();
		Role role = roleDAO.find(id);
		roleDAO.commitAndCloseTransaction();
		return role;
	}
	
	public Role findByCode(String code) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByCode()");
		}
		roleDAO.beginTransaction();
		Role persistedRole = roleDAO.findByCode(code);
		roleDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByCode()");
		}
		return persistedRole;
	}
	
}
