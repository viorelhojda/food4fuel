package com.atraxo.f4f.facade;

import com.atraxo.f4f.dao.ProcessEmailJobDAO;
import com.atraxo.f4f.model.job.ProcessEmailJob;

/**
 * @author vhojda
 *
 */
public class ProcessEmailJobFacade {

	private ProcessEmailJobDAO processEmailJobDAO = new ProcessEmailJobDAO();


	/**
	 * @param item
	 */
	public void insert(ProcessEmailJob item){
		processEmailJobDAO.beginTransaction();
		processEmailJobDAO.save(item);
		processEmailJobDAO.commitAndCloseTransaction();
	}
	
	/**
	 * @param item
	 */
	public void update(ProcessEmailJob item){
		processEmailJobDAO.beginTransaction();
		processEmailJobDAO.update(item);
		processEmailJobDAO.commitAndCloseTransaction();
	}

	/**
	 * @param processJobId
	 * @return
	 */
	public ProcessEmailJob find(Integer processJobId) {
		processEmailJobDAO.beginTransaction();
		ProcessEmailJob job = processEmailJobDAO.find(processJobId);
		processEmailJobDAO.commitAndCloseTransaction();
		return job;
	}



}