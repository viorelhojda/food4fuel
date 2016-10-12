package com.atraxo.f4f.job.quartz;

import java.util.Date;

import org.quartz.SchedulerException;

import com.atraxo.f4f.model.job.ProcessJob;

/**
 * @author vhojda
 *
 */
public interface ISchedulableJob {
	
	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public boolean scheduleJob(ProcessJob job) throws SchedulerException ;
	
	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public boolean suspendJob(ProcessJob job) throws SchedulerException;
	
	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public boolean pauseJob(ProcessJob job) throws SchedulerException;

	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public boolean resumeJob(ProcessJob job) throws SchedulerException;
	
	/**
	 * @param job
	 * @param date
	 * @return
	 * @throws SchedulerException
	 */
	public boolean rescheduleJob(ProcessJob job, Date date) throws SchedulerException ;
	
	/**
	 * @param job
	 * @param failMessage
	 * @throws SchedulerException
	 */
	public void rescheduleFailedJob(ProcessJob job, String failMessage) throws SchedulerException;
	
	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public boolean executeJob(ProcessJob job) throws SchedulerException;


}
