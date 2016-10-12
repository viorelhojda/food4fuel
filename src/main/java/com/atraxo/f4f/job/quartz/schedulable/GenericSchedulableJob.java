/**
 * 
 */
package com.atraxo.f4f.job.quartz.schedulable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.atraxo.f4f.job.quartz.ISchedulableJob;
import com.atraxo.f4f.job.quartz.JobScheduler;
import com.atraxo.f4f.model.job.ProcessJob;
import com.atraxo.f4f.util.DateUtils;

/**
 * @author vhojda
 *
 */
public abstract class GenericSchedulableJob implements ISchedulableJob  {

	private static final Logger LOGGER = Logger.getLogger(GenericSchedulableJob.class);

	protected JobScheduler jobScheduler;

	private Calendar triggerCal;
	
	/**
	 * 
	 */
	public GenericSchedulableJob() {
		jobScheduler = JobScheduler.getInstance();
	}

	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	@Override
	public boolean suspendJob(ProcessJob job) throws SchedulerException{
		boolean operationOk = jobScheduler.suspend(job.formJobKeyFromProcessJob());
		LOGGER.info("SUSPENDED JK="+ job.formJobKeyFromProcessJob() +"; success:" + operationOk );
		return operationOk;
	}
	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	@Override
	public boolean pauseJob(ProcessJob job) throws SchedulerException {
		boolean operationOk = jobScheduler.pause(job.formJobKeyFromProcessJob());
		LOGGER.info("PAUSED JK="+ job.formJobKeyFromProcessJob() +"; success:" + operationOk );
		return operationOk;
	}

	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	@Override
	public boolean resumeJob(ProcessJob job) throws SchedulerException {
		boolean operationOk = jobScheduler.resume( job.formJobKeyFromProcessJob() );
		LOGGER.info("RESUMED JK="+ job.formJobKeyFromProcessJob() +"; success:" + operationOk );
		return operationOk;
	}

	/**
	 * @param job
	 * @param date
	 * @return
	 * @throws SchedulerException
	 */
	@Override
	public boolean rescheduleJob(ProcessJob job, Date date) throws SchedulerException {
		boolean operationOk;

		//get the JobKey
		JobKey jobKey = job.formJobKeyFromProcessJob(); 

		//create the new trigger
		Trigger newTrigger = TriggerBuilder.newTrigger()
				.forJob(jobKey)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule())
				.startAt( date )
				.build();

		//get the old trigger
		TriggerKey oldTriggerKey = null;
		List<Trigger> existingTriggers = jobScheduler.getExistingTriggers(jobKey); 
		if ( existingTriggers != null && existingTriggers.size() == 1 ) {
			oldTriggerKey = existingTriggers.get(0).getKey();
		}

		if ( oldTriggerKey != null ) {
			Date nextFireTime = jobScheduler.reschedule(oldTriggerKey, newTrigger);

			if (nextFireTime != null) {
				operationOk = true;
				LOGGER.info("RESCHEDULED Job JK=" + jobKey + " trigger will fire at nextFireTime=" + nextFireTime);
			} else {
				operationOk = false;
				LOGGER.error("RESCHEDULED Job JK=" + jobKey + "scheduleJob: nextFireTime is null!! This trigger will never fire!!!");
			}
		} else{
			LOGGER.error("ERROR getting the trigger for "+jobKey+" !!! No triggers or too many triggers found !!!");
			operationOk = false;
		}

		LOGGER.info("RESCHEDULED JK="+ jobKey +" at "+DateUtils.convertToSeconds(date)+"; success:" + operationOk );

		return operationOk;
	}

	@Override
	public boolean executeJob(ProcessJob job) throws SchedulerException {
		boolean operationOk = true;

		//get the JobKey
		JobKey jobKey = job.formJobKeyFromProcessJob();

		try {
			jobScheduler.getScheduler().triggerJob(jobKey);
		} catch (SchedulerException e) {
			operationOk = false;
		}

		LOGGER.info("TRIGGERED JK="+ jobKey +" at "+DateUtils.currentTime()+"; success:" + operationOk );

		return operationOk;
	}

	/**
	 * Calculates a SAFE trigger date for a job to execute, given the initial ProcessJob
	 * 
	 * @param job
	 * @return
	 */
	protected Calendar getSafeScheduledDate(ProcessJob job) {

		Date startDate = job.getStartDate();
		String processId =  job.getGroupElementId()+"";

		Calendar triggerCalendar = Calendar.getInstance();
		triggerCalendar.setTime(startDate);
		Calendar now = Calendar.getInstance();

		if ( triggerCalendar.before(now) ) {
			//in the past, so set it as now + 1 minute
			triggerCalendar.setTime(now.getTime());
			triggerCalendar.add(Calendar.MINUTE, 1);
		}
		
		try {
			Trigger lastTrigger = calculateLastTrigger(job);

			triggerCalendar.setTime(lastTrigger.getStartTime());
			triggerCalendar.add(Calendar.MINUTE, 1);
			
		} catch (SchedulerException e) {
			LOGGER.error("ERROR: could not get the job keys for process "+processId, e);
		}

		return triggerCalendar;
	}

	/**
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	private Trigger calculateLastTrigger(ProcessJob job) throws SchedulerException {
		Trigger lastTrigger = null;

		//search for other jobs that belong to the same group (same process)
		Set<JobKey> processJobKeys = jobScheduler.getGroupJobKeys(
				job.formJobKeyFromProcessJob()); 

		//get the triggers for the same process
		List<Trigger> triggersOnSameProcess = new ArrayList<Trigger>();
		for (JobKey jk : processJobKeys ) {
			List<Trigger> existingTriggers = jobScheduler.getTriggersOfJob(jk);
			if ( !existingTriggers.isEmpty() ) {
				triggersOnSameProcess.addAll(existingTriggers);
			}
		}

		//if there are more triggers on same process (that means more jobs also)
		if ( !triggersOnSameProcess.isEmpty() ) {
			boolean recalculateTrigger = false;
			String s2 = DateUtils.convertToTime(triggerCal.getTime());
			for ( Trigger trig : triggersOnSameProcess ) {
				String s1 = DateUtils.convertToTime(trig.getStartTime());
				if ( s1.equals(s2) ) {
					//this means getting a SAFE date is mandatory
					recalculateTrigger = true;
					break;
				}
			}

			if ( recalculateTrigger ) {
				//calculate the last trigger,that is the trigger with the latest execution date
				for ( Trigger trig : triggersOnSameProcess ) {
					if ( lastTrigger == null ){
						lastTrigger = trig;
					} else{
						if ( trig.getStartTime().compareTo(lastTrigger.getStartTime()) > 0 ){
							lastTrigger = trig;
						}
					}
				}
			}
		}

		return lastTrigger;
	}

	



}
