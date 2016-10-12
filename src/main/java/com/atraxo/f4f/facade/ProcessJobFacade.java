package com.atraxo.f4f.facade;

import java.util.List;

import com.atraxo.f4f.dao.ProcessJobDAO;
import com.atraxo.f4f.model.job.ProcessJob;
import com.atraxo.f4f.model.job.ProcessJobStatusEnum;
import com.atraxo.f4f.model.job.ProcessJobTypeEnum;

/**
 * @author vhojda
 *
 */
public class ProcessJobFacade {

	private ProcessJobDAO processJobDAO = new ProcessJobDAO();


	/**
	 * @param item
	 */
	public void insert(ProcessJob item){
		processJobDAO.beginTransaction();
		processJobDAO.save(item);
		processJobDAO.commitAndCloseTransaction();
	}
	
	/**
	 * @param item
	 */
	public void update(ProcessJob item){
		processJobDAO.beginTransaction();
		processJobDAO.update(item);
		processJobDAO.commitAndCloseTransaction();
	}

	/**
	 * @param processJobId
	 * @return
	 */
	public ProcessJob find(Integer processJobId) {
		processJobDAO.beginTransaction();
		ProcessJob job = processJobDAO.find(processJobId);
		processJobDAO.commitAndCloseTransaction();
		return job;
	}

	/**
	 * @param type
	 * @param status
	 * @return
	 */
	public List<ProcessJob> findProcessJobsByTypeAndStatus(ProcessJobTypeEnum type, ProcessJobStatusEnum status) {
		processJobDAO.beginTransaction();
		List<ProcessJob> result = processJobDAO.findProcessJobsByTypeAndStatus(type, status);
		processJobDAO.commitAndCloseTransaction();
		return result;
	}

	/**
	 * @param status
	 * @return
	 */
	public List<ProcessJob> findAllByStatus(ProcessJobStatusEnum status) {
		processJobDAO.beginTransaction();
		List<ProcessJob> result = processJobDAO.findAllByStatus(status);
		processJobDAO.commitAndCloseTransaction();
		return result;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public List<ProcessJob> findAllByType(ProcessJobTypeEnum type) {
		processJobDAO.beginTransaction();
		List<ProcessJob> result = processJobDAO.findAllByType(type);
		processJobDAO.commitAndCloseTransaction();
		return result;
	}

	public List<ProcessJob> findAll() {
		processJobDAO.beginTransaction();
		List<ProcessJob> result = processJobDAO.findAll();
		processJobDAO.commitAndCloseTransaction();
		return result;
	}


}