package com.atraxo.f4f.job.quartz.action;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;

import com.atraxo.f4f.facade.ProcessJobFacade;
import com.atraxo.f4f.job.quartz.ISchedulableJob;
import com.atraxo.f4f.job.quartz.JobActionResponseEnum;
import com.atraxo.f4f.job.quartz.SchedulableJobFactory;
import com.atraxo.f4f.job.quartz.ServiceJobActionFactory;
import com.atraxo.f4f.model.job.ProcessJob;
import com.atraxo.f4f.model.job.ProcessJobStatusEnum;
import com.atraxo.f4f.model.job.ProcessJobTypeEnum;

/**
 * @author vhojda
 *
 */
/**
 * @author Hojda Viorel
 *
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QuartzJobAction implements Job{

	private static final Logger LOGGER = Logger.getLogger(QuartzJobAction.class);

	public static final String PROCESS_JOB_ID = "process_job_id";
	public static final String PROCESS_JOB_TYPE = "process_job_type";


	private ProcessJobFacade processJobFacade = new ProcessJobFacade();

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START execute()");
		}
		LOGGER.info("START execute() JOB for "+this.getClass());

		try {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			Integer processJobId = jobDataMap.getIntegerFromString(PROCESS_JOB_ID);
			String processJobType = jobDataMap.getString(PROCESS_JOB_TYPE);
			
			if ( processJobId != null ) {

				if ( (processJobType!= null && !processJobType.equals("")) || ProcessJobTypeEnum.valueOf(processJobType) == null ) {

					ProcessJob processJob = processJobFacade.find(processJobId);

					if ( processJob != null ) {
						JobActionResponseEnum actionType = null;
						ProcessJobTypeEnum jobType = ProcessJobTypeEnum.valueOf(processJobType);
						
						String failMessage = null;
						try {			
							GenericJobAction actionService = ServiceJobActionFactory.getFactory().getGenericJobActionService(jobType);
							boolean executed = actionService.preExecuteAction(jobDataMap);
							actionType = executed ? actionService.getSuccessJobActionResponse() : JobActionResponseEnum.ERROR ; 
						} 
						catch (Exception e) {
							LOGGER.error("ERROR: could not execute job process with ID:"+processJobId, e);
							failMessage = e.getMessage();
							throw e;
						}
						finally {
							updateProcessJobAfterActionsExecuted(processJob, actionType, failMessage, jobDataMap.getString(ProcessJob.EXCEPTION_KEY)) ;
						}
						
					} else {
						LOGGER.error("ERROR: could not find the process job with ID: "+processJobId);
					}
				} else{
					LOGGER.error("ERROR: could not locate the JOB TYPE in the map : "+processJobType+" for Job ID" + processJobId);
				}
			} else {
				LOGGER.error("ERROR: could not locate the JOB ID in the map : "+processJobId);
			}

		} catch (Exception e) {
			LOGGER.error("ERROR: could not execute the job !!!", e);
		}

		LOGGER.info("END execute() JOB for "+this.getClass());

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END execute()");
		}
	}

	/**
	 * @param processJob
	 * @param actionType
	 * @param failMessage
	 * @param exception
	 * @throws SchedulerException
	 */
	private void updateProcessJobAfterActionsExecuted(ProcessJob processJob, Object actionType, String failMessage, String exception) throws SchedulerException {
		if (JobActionResponseEnum.STAY_ALIVE.equals(actionType)) {
			processJob.setStatus(ProcessJobStatusEnum.ACTIVE);
		} else if (JobActionResponseEnum.FINISHED.equals(actionType)) {
			processJob.setStatus(ProcessJobStatusEnum.EXECUTED);
		} else {
			processJob.setStatus(ProcessJobStatusEnum.FAILED);
			processJob.setFailMessage(failMessage);
			processJob.incrementAttempt();

			ISchedulableJob schedulableJob = SchedulableJobFactory.getFactory().getSchedulableJob(processJob.getType());
			if (schedulableJob != null) {
				schedulableJob.rescheduleFailedJob(processJob, exception);
			} else {
				LOGGER.error("execute: job with type [" + processJob.getType() + "] doesn't exist!!!!!");
			}
		}

		processJobFacade.update(processJob);
	}




}
