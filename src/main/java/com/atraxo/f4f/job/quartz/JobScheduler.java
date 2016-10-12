package com.atraxo.f4f.job.quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.atraxo.f4f.facade.ProcessJobFacade;
import com.atraxo.f4f.model.job.ProcessEmailJob;
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
public class JobScheduler {

	private static final Logger LOGGER = Logger.getLogger(JobScheduler.class);

	private Scheduler scheduler;

	private ProcessJobFacade processJobFacade;

	private static JobScheduler instance;


	/**
	 * @return
	 */
	public static JobScheduler getInstance() {
		if ( instance == null ){
			instance = new JobScheduler();
		}
		return instance;
	}

	/**
	 * 
	 */
	public JobScheduler() {
		processJobFacade = new ProcessJobFacade();
		init();
	}

	/**
	 * Initializes the quartz and start the scheduler
	 */
	private void init(){
		LOGGER.info("START init() for MarsJobScheduler !");

		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			//start();
		} catch (SchedulerException e) {
			LOGGER.error("ERROR when instantiating the scheduler !!", e);
		}

		LOGGER.info("END init() for MarsJobScheduler !");
	}

	/**
	 * Stops the scheduler
	 */
	public void stop(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START stop()");
		}
		try {
			if( !scheduler.isShutdown() ){
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			LOGGER.error("ERROR when shuttting down the scheduler !!", e);
		}
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END stop()");
		}
	}

	/**
	 * Stops the scheduler
	 */
	public void standBy(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START standBy()");
		}
		try {
			scheduler.standby();
		} catch (SchedulerException e) {
			LOGGER.error("ERROR when stend by the scheduler !!", e);
		}
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END standBy()");
		}
	}

	/**
	 * Starts the scheduler
	 */
	public void start(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START start()");
		}
		if ( scheduler != null ) {
			try {
				if ( ! scheduler.isStarted() ) {
					scheduler.start();
					startJobs();
				}
			} catch (SchedulerException e) {
				LOGGER.error("ERROR when starting the scheduler !!", e);
			}
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END start()");
		}
	}

	/**
	 * Start jobs
	 * @throws SchedulerException
	 */
	private void startJobs() throws SchedulerException {
		//activate all failed jobs
		for (ProcessJobTypeEnum bacJobType : ProcessJobTypeEnum.getActivateFailedJobTypesList()) {
			activateFailedJobsByType(bacJobType);
		}
		
		checkJobExistence(ProcessJobTypeEnum.DAILY_EMAIL_ORDER_FOOD);
		checkJobExistence(ProcessJobTypeEnum.DAILY_EMAIL_REMINDER_PREORDER_FOOD);
		checkJobExistence(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_COLLECT_MONEY);
		checkJobExistence(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_PAY_MONEY);

		scheduleAllActiveJobs();
	}



	/**
	 * @param jobType
	 */
	private void checkJobExistence(ProcessJobTypeEnum jobType) {
		List<ProcessJob> jobs = processJobFacade.findAllByType(jobType);
		if ( jobs.isEmpty() ) {
			ProcessEmailJob newJob = new ProcessEmailJob();
			newJob.setType(jobType);
			newJob.setStartDate(jobType.getStartDate().getTime());
			Calendar expireCalendar = Calendar.getInstance();
			expireCalendar.setTime(newJob.getStartDate());
			expireCalendar.add(Calendar.MONTH, 1);
			newJob.setStatus(ProcessJobStatusEnum.ACTIVE);
			processJobFacade.insert(newJob);
		}
		else{
			if ( jobs.size() != 1 ){
				LOGGER.warn("WARNING:there are multiple jobs with type "+jobType+" !! Will use the first one !");
			}
			ProcessEmailJob job = (ProcessEmailJob) jobs.get(0);
			if ( !job.getStatus().equals(ProcessJobStatusEnum.ACTIVE) ) {
				job.setStatus(ProcessJobStatusEnum.ACTIVE);
				processJobFacade.update(job);
			}
		}
	}

	/**
	 * Activate all failed jobs by job type
	 */
	private void activateFailedJobsByType(ProcessJobTypeEnum type){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START activateFailedJobsByType() for type:" + type);
		}

		List<ProcessJob> jobsList = processJobFacade.findProcessJobsByTypeAndStatus(type, ProcessJobStatusEnum.FAILED);
		if (!jobsList.isEmpty() ){
			LOGGER.info("activateFailedJobsByType count=[" + jobsList.size() + "] jobs og type=[" + type.name() + "]");

			for (ProcessJob job : jobsList) {
				if( job != null) {
					job.setStartDate(new Date());
					job.setStatus(ProcessJobStatusEnum.ACTIVE);
					processJobFacade.update(job);
				}
			}
		}
	}


	/**
	 * 
	 */
	public void restart(){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START restart()");
		}
		stop();
		start();

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END restart()");
		}
	}


	/**
	 * @param jk
	 * @return
	 * @throws SchedulerException
	 */
	public boolean pause(JobKey jk) throws SchedulerException{
		boolean operationOk = false;
		scheduler.pauseJob(jk);
		operationOk = true;
		return operationOk;
	}

	/**
	 * @param jk
	 * @return
	 * @throws SchedulerException
	 */
	public boolean resume(JobKey jk) throws SchedulerException{
		boolean operationOk = false;
		scheduler.resumeJob(jk);
		operationOk = true;
		return operationOk;
	}

	/**
	 * @param jk
	 * @return
	 * @throws SchedulerException
	 */
	public boolean suspend(JobKey jk) throws SchedulerException{
		boolean operationOk = scheduler.deleteJob(jk);
		return operationOk;
	}

	/**
	 * @param job
	 * @param trigger
	 */
	public Date schedule(JobDetail job, Trigger trigger) {
		Date firstFireTime = null;
		try {
			firstFireTime = scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			LOGGER.error("schedule: ", e);
			logJobError(job.getKey());
		}

		LOGGER.info("schedule : firstFireTime=" + firstFireTime);

		return firstFireTime;
	}

	/**
	 * @param oldTriggerKey
	 * @param newTrigger
	 */
	public Date reschedule(TriggerKey oldTriggerKey, Trigger newTrigger) {
		Date firstFireTime = null;

		try {
			firstFireTime = scheduler.rescheduleJob(oldTriggerKey, newTrigger);
		} catch (SchedulerException e) {
			LOGGER.error("reschedule: ", e);
			logJobError(newTrigger.getJobKey());
		}

		LOGGER.info("reschedule : firstFireTime=" + firstFireTime);

		return firstFireTime;
	}

	/**
	 * log job data map
	 * @param jobKey
	 */
	private void logJobError(JobKey jobKey) {
		ProcessJob processJob = processJobFacade.find(Integer.parseInt(jobKey.getName()));
		LOGGER.error("(re)schedule: Something bad happened while trying to (re)schedule Quartz job with " +
				"name = " + jobKey + "ProcessJob.idProcessJob = " + ((processJob != null) ? processJob.getId() : null));
	}

	/**
	 * @param jk
	 * @return
	 * @throws SchedulerException 
	 */
	public List<Trigger> getExistingTriggers(JobKey jk) throws SchedulerException{
		List<Trigger> trigges = (List<Trigger>) scheduler.getTriggersOfJob(jk); 
		return  trigges;
	}

	/**
	 * @param jk
	 * @return
	 * @throws SchedulerException 
	 */
	public List<Trigger> getTriggersOfJob(JobKey jk) throws SchedulerException {
		List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jk);
		return triggers;
	}

	public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) throws SchedulerException {
		Trigger.TriggerState result = scheduler.getTriggerState(triggerKey);
		return result;
	}

	public JobDataMap getJobDataMap(JobKey jk) throws SchedulerException {
		JobDataMap result = null;
		if (jk != null) {
			JobDetail jobDetail = scheduler.getJobDetail(jk);
			if (jobDetail != null) {
				result = jobDetail.getJobDataMap();
			}
		}
		return result;
	}

	/**
	 * @param jk
	 * @return
	 * @throws SchedulerException
	 */
	public Set<JobKey> getGroupJobKeys(JobKey jk) throws SchedulerException {
		Set<JobKey> processJobKeys = scheduler.getJobKeys(
				GroupMatcher.jobGroupEquals( jk.getGroup()) );
		return processJobKeys;
	}

	/**
	 * @param job
	 * @param date
	 */
	public void rescheduleJob(ProcessJob job, Date date){
		ISchedulableJob schedulableJob = SchedulableJobFactory.getFactory().getSchedulableJob(job.getType());
		if (schedulableJob != null) {
			try {
				boolean result = schedulableJob.rescheduleJob(job, date);
				if (result) {
					System.out.println("rescheduleJob: job [" + job.getId() + "] with type [" +
							job.getType() + "] RESCHEDULED!");
					LOGGER.debug("rescheduleJob: job [" + job.getId() + "] with type [" +
							job.getType() + "] RESCHEDULED!");
				} else {
					LOGGER.error("rescheduleJob: job [" + job.getId() + "] with type [" +
							job.getType() + "] DIDN'T RESCHEDULE!");
				}
			} catch (SchedulerException e) {
				LOGGER.error("rescheduleJob: something bad happened in Quartz Scheduler!!", e);
			}
		} else {
			LOGGER.error("rescheduleJob: job with type [" + job.getType() + "] doesn't exist!!!!!");
		}	
	}
	
	
	/**
	 * @param job
	 */
	public void scheduleJob(ProcessJob job){
		ISchedulableJob schedulableJob = SchedulableJobFactory.getFactory().getSchedulableJob(job.getType());
		if (schedulableJob != null) {
			try {
				boolean result = schedulableJob.scheduleJob(job);
				if (result) {
					System.out.println("scheduleAllActiveJobs: job [" + job.getId() + "] with type [" +
							job.getType() + "] SCHEDULED!");
					LOGGER.debug("scheduleAllActiveJobs: job [" + job.getId() + "] with type [" +
							job.getType() + "] SCHEDULED!");
				} else {
					LOGGER.error("scheduleAllActiveJobs: job [" + job.getId() + "] with type [" +
							job.getType() + "] DIDN'T SCHEDULE!");
				}
			} catch (SchedulerException e) {
				LOGGER.error("scheduleAllActiveJobs: something bad happened in Quartz Scheduler!!", e);
			}
		} else {
			LOGGER.error("scheduleAllActiveJobs: job with type [" + job.getType() + "] doesn't exist!!!!!");
		}
	}
	
	/**
	 * @throws SchedulerException
	 */
	private void scheduleAllActiveJobs() throws SchedulerException{
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START scheduleAllActiveJobs()");
		}

		List<ProcessJob> activeJobs = processJobFacade.findAllByStatus(ProcessJobStatusEnum.ACTIVE);
		//schedule all the active jobs
		for (ProcessJob job : activeJobs) {
			scheduleJob(job);
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END scheduleAllActiveJobs()");
		}
	}


	/**
	 * @param excludedJobKey
	 * @return
	 */
	public boolean jobsAreExecuting(JobKey excludedJobKey){
		boolean executing = false;
		try {

			List<JobExecutionContext> execs = scheduler.getCurrentlyExecutingJobs();
			for ( JobExecutionContext exe : execs ) {
				JobKey jk = exe.getJobDetail().getKey();

				if ( jk.getGroup().equals(excludedJobKey.getGroup()) ) {
					if ( jk.getName().equals(excludedJobKey.getName())) {
						//do nothing, this is an exception
					} else{
						executing = true;
						break;
					}

				}
			}
		} catch (SchedulerException e) {
			LOGGER.error("ERROR: could not find out if jobs are executing !", e);
		}
		return executing;
	}

	/**
	 * @return
	 */
	public boolean isInStandbyMode() {
		boolean standBy = false;
		if ( scheduler != null ) {
			try {
				standBy = scheduler.isInStandbyMode();
			} catch (SchedulerException e) {
				//do nothing, just need to find the error
				LOGGER.error("isInStandbyMode: ", e);
			}
		}
		return standBy;
	}


	public Scheduler getScheduler() {
		return scheduler;
	}



}
