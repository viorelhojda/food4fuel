package com.atraxo.f4f.facade;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.atraxo.f4f.dao.RightDAO;
import com.atraxo.f4f.model.permission.Right;

public class RightFacade implements Serializable{
	private static final long serialVersionUID = 5759351122136097812L;
	private static final Logger LOGGER = Logger.getLogger(RightFacade.class);
	
	private RightDAO rightDAO = new RightDAO();
	
	public List<Right> listAll(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START listAll()");
		}
		rightDAO.beginTransaction();
		List<Right> persistedRights = rightDAO.findAll();
		rightDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END listAll()");
		}
		return persistedRights;
	}
	
	public List<Right> listAllActive() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START listAllActive()");
		}
		rightDAO.beginTransaction();
		List<Right> persistedRights = rightDAO.findAllActive();
		rightDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END listAllActive()");
		}
		return persistedRights;
	}
	
	public Right findById(int id) {
		rightDAO.beginTransaction();
		Right right = rightDAO.find(id);
		rightDAO.commitAndCloseTransaction();

		return right;
	}
	
	public Right findByCode(String code) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByCode()");
		}
		rightDAO.beginTransaction();
		Right persistedRight = rightDAO.findByCode(code);
		rightDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByCode()");
		}
		return persistedRight;
	}
}
