package com.atraxo.f4f.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.permission.Right;

public class RightDAO extends GenericDAO<Right>{
	private static final long serialVersionUID = 8362017165075333765L;
	private static final Logger LOGGER = Logger.getLogger(RightDAO.class);
	
	public RightDAO() {
		super(Right.class);
	}
	
	public Right findByCode(String code) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findByCode()");
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("code", code);

		Right foundRight = findOneResult(Right.FIND_BY_CODE, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findByCode()");
		}

		return foundRight;
	}

	public List<Right> findAllActive() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findAllActive()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		
		List<Right> rights = findMultipleResults(Right.FIND_ALL_ACTIVE, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findAllActive()");
		}
		
		return rights;
	}
}
