package com.atraxo.f4f.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.atraxo.f4f.facade.ProcessJobFacade;
import com.atraxo.f4f.job.quartz.JobScheduler;
import com.atraxo.f4f.model.job.ProcessJob;
import com.atraxo.f4f.model.job.ProcessJobStatusEnum;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;

@ViewScoped
@ManagedBean
public class JobMB extends AbstractMB{

	private static final long serialVersionUID = 2419273682562031822L;
	private static final Logger LOGGER = Logger.getLogger(JobMB.class);

	private ProcessJobFacade processJobFacade;

	private ProcessJob job;
	private List<ProcessJob> jobs;

	private Date rescheduleDate;

	public JobMB() {
		super();
		processJobFacade = new ProcessJobFacade();
	}

	public void refresh(){
		jobs = processJobFacade.findAll();
	}

	public void pauseJob(){
		try {
			job.setStatus(ProcessJobStatusEnum.PAUSED);
			processJobFacade.update(job);

			JobScheduler.getInstance().pause(job.formJobKeyFromProcessJob());

			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_PAUSE_JOB_OK));
		}
		catch (Exception e) {
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_PAUSE_JOB_NOT_OK));
			LOGGER.error("ERROR: could not PAUSE the job !", e);
		}
	}

	public void resumeJob(){
		try {
			job.setStatus(ProcessJobStatusEnum.ACTIVE);
			processJobFacade.update(job);

			JobScheduler.getInstance().resume(job.formJobKeyFromProcessJob());

			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_RESUME_JOB_OK));
		}
		catch (Exception e) {
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_RESUME_JOB_NOT_OK));
			LOGGER.error("ERROR: could not RESUME the job !", e);
		}
	}

	public void suspendJob(){
		try {
			job.setStatus(ProcessJobStatusEnum.SUSPENDED);
			processJobFacade.update(job);

			JobScheduler.getInstance().suspend(job.formJobKeyFromProcessJob());

			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_SUSPEND_JOB_OK));
		}
		catch (Exception e) {
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_SUSPEND_JOB_NOT_OK));
			LOGGER.error("ERROR: could not SUSPEND the job !", e);
		}
	}

	public void scheduleJob(){
		try {
			job.setStartDate(rescheduleDate);
			Calendar rescheduleExpireCalendar = Calendar.getInstance();
			rescheduleExpireCalendar.setTime(rescheduleDate);
			rescheduleExpireCalendar.add(Calendar.MONTH, 1);
			job.setExpireDate(rescheduleExpireCalendar.getTime());

			job.setStatus(ProcessJobStatusEnum.ACTIVE);
			processJobFacade.update(job);

			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_SCHEDULE_JOB_OK));
		}
		catch (Exception e) {
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_SCHEDULE_JOB_NOT_OK));
			LOGGER.error("ERROR: could not SCHEDULE the job !", e);
		}

		rescheduleDate = null;
	}

	/**
	 * 
	 */
	public void rescheduleJob(){
		Calendar rescheduleCalendar = Calendar.getInstance(TimeZone.getDefault());
		rescheduleCalendar.setTime(DateMB.getServerDate(rescheduleDate));

		Calendar rescheduleExpireCalendar = Calendar.getInstance(TimeZone.getDefault());
		rescheduleExpireCalendar.setTime(rescheduleCalendar.getTime());
		rescheduleExpireCalendar.add(Calendar.MONTH, 1);

		job.setStartDate(rescheduleCalendar.getTime());
		job.setExpireDate(rescheduleExpireCalendar.getTime());

		job.setStatus(ProcessJobStatusEnum.ACTIVE);
		processJobFacade.update(job);

		try {
			if( jobHasNoTrigger() ){
				JobScheduler.getInstance().scheduleJob(job);
			}else{
				JobScheduler.getInstance().rescheduleJob(job,rescheduleDate);
			}
			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_RESCHEDULE_JOB_OK));
		}
		catch (Exception e) {
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_RESCHEDULE_JOB_NOT_OK));
			LOGGER.error("ERROR: could not RESCHEDULE the job !", e);
		}
		
		rescheduleDate = null;
	}

	private boolean jobHasNoTrigger(){
		List<Trigger> triggers = new ArrayList<>();

		try {
			triggers = JobScheduler.getInstance().getExistingTriggers(job.formJobKeyFromProcessJob());
		} catch (SchedulerException e) {
			LOGGER.error("Could not verify if job has triggers", e);
		}

		return triggers.isEmpty();
	}

	public ProcessJob getJob() {
		return job;
	}

	public void setJob(ProcessJob job) {
		this.job = job;
	}

	public Date getRescheduleDate() {
		return rescheduleDate;
	}

	public void setRescheduleDate(Date rescheduleDate) {
		this.rescheduleDate = rescheduleDate;
	}

	public List<ProcessJob> getJobs() {
		if( jobs == null ){
			jobs = processJobFacade.findAll();
		}
		return jobs;
	}

	public void setJobs(List<ProcessJob> jobs) {
		this.jobs = jobs;
	}


	public ProcessJobFacade getProcessJobFacade() {
		return processJobFacade;
	}

	public void setProcessJobFacade(ProcessJobFacade processJobFacade) {
		this.processJobFacade = processJobFacade;
	}



	//	public boolean filterByDate(Object value, Object filter, Locale locale) { // NOSONAR
	//        if( filter == null ) {
	//            return true;
	//        }
	//        if( value == null ) {
	//            return false;
	//        }
	//        return DateUtils.truncatedEquals((Date) filter, (Date) value, Calendar.DATE);
	//    }
}
