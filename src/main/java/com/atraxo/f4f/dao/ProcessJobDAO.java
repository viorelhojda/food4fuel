/**
 * 
 */
package com.atraxo.f4f.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.job.ProcessJob;
import com.atraxo.f4f.model.job.ProcessJobStatusEnum;
import com.atraxo.f4f.model.job.ProcessJobTypeEnum;

/**
 * 
 * @author Hojda Viorel
 * @created Feb 20, 2015
 * @since Feb 20, 2015
 *
 */
public class ProcessJobDAO extends GenericDAO<ProcessJob> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4687128424819424891L;
	
	
	private static final Logger LOGGER = Logger.getLogger(ProcessJobDAO.class);
	
	public ProcessJobDAO() {
		super(ProcessJob.class);
	}
	
	public List<ProcessJob> findAllByStatus(ProcessJobStatusEnum status) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("START findAllByStatus()");
		}

		List<ProcessJob> result = null;
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", status);
		result = findMultipleResults(ProcessJob.FIND_ALL_JOBS_BY_STATUS, parameters);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("END findAllByStatus()");
		}

		return result;
	}



	/**
	 * @param type
	 * @return
	 */
	public List<ProcessJob> findAllByType(ProcessJobTypeEnum type) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("START findAllByType()");
		}

		List<ProcessJob> result = null;
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("type", type);
		result = findMultipleResults(ProcessJob.FIND_ALL_JOBS_BY_TYPE, parameters);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("END findAllByType()");
		}

		return result;
	}
	
	public List<ProcessJob> findProcessJobsByTypeAndStatus(ProcessJobTypeEnum type,
			ProcessJobStatusEnum status) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("START findProcessJobsByTypeAndStatus()");
		}

		List<ProcessJob> result = new ArrayList<ProcessJob>();

		if (type == null) {
			LOGGER.error("Invalid param: type is null");
		} else if (status == null) {
			LOGGER.error("Invalid param: status is null");
		} else {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("type", type);
			parameters.put("status", status);
			result = findMultipleResults(ProcessJob.FIND_ALL_JOBS_BY_STATUS_AND_TYPE, parameters);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("END findProcessJobsByTypeAndStatus()");
		}

		return result;
	}


}
