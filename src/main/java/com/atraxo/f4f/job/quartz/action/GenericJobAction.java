package com.atraxo.f4f.job.quartz.action;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;

import com.atraxo.f4f.job.quartz.JobActionResponseEnum;

/**
 * @author Hojda Viorel
 *
 */
public abstract class GenericJobAction {

	/**
	 * @return
	 */
	public abstract JobActionResponseEnum getSuccessJobActionResponse();

	/**
	 * 
	 * Executes the job action for the current job.
	 * 
	 * @param jobDataMap
	 * @return
	 * @throws JobExecutionException 
	 */
	public abstract boolean executeJobAction(JobDataMap jobDataMap) throws JobExecutionException;


	/**
	 * @param jobDataMap
	 * @return
	 * @throws JobExecutionException
	 */
	public boolean preExecuteAction(JobDataMap jobDataMap) throws JobExecutionException {
		// set API KEY for HTTP requests send from NON-SERVICE components (e.g.
		// quartz jobs)

		return executeJobAction(jobDataMap);
	}


}
