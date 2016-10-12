/**
 * 
 */
package com.atraxo.f4f.dao;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.job.ProcessEmailJob;

/**
 * 
 * @author Hojda Viorel
 * @created Feb 20, 2015
 * @since Feb 20, 2015
 *
 */
public class ProcessEmailJobDAO extends GenericDAO<ProcessEmailJob> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4687128424819424891L;
	
	
	private static final Logger LOGGER = Logger.getLogger(ProcessEmailJobDAO.class);
	
	public ProcessEmailJobDAO() {
		super(ProcessEmailJob.class);
	}
	


}
