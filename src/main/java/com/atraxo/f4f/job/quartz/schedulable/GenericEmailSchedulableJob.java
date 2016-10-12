/**
 * 
 */
package com.atraxo.f4f.job.quartz.schedulable;

import java.util.Date;

import org.quartz.SchedulerException;

import com.atraxo.f4f.model.job.ProcessJob;
import com.atraxo.f4f.model.job.ProcessJobStatusEnum;

/**
 * @author vhojda
 *
 */
public abstract class GenericEmailSchedulableJob extends GenericSchedulableJob {

	static final long MILLISECONDS_PER_HOUR = 60L*60*1000;

	/**
	 * 
	 */
	public GenericEmailSchedulableJob() {
		super();
	}

	@Override
	public void rescheduleFailedJob(ProcessJob job, String failMessage) throws SchedulerException  {	

		Integer maxRetrySetting = 3;
		Integer delayHourSetting = 1;

		if (job.getAttempt()==null || maxRetrySetting > job.getAttempt()) {
			Date start = new Date();
			Date expired = new Date();
			start.setTime( start.getTime() + ( delayHourSetting * MILLISECONDS_PER_HOUR) );						
			expired.setTime(start.getTime()+(job.getExpireDate().getTime() - job.getStartDate().getTime()));
			job.setStartDate(start);
			job.setExpireDate(expired);
			job.setFailMessage(failMessage);
			this.rescheduleJob(job,start);
		} else {
			job.setStatus(ProcessJobStatusEnum.FAILED);
			job.setFailMessage("Maxed number of retries attempted .... quitting ! ");
		} 					

	}

}
