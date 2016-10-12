/**
 * 
 */
package com.atraxo.f4f.job.quartz.schedulable;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.atraxo.f4f.job.quartz.action.QuartzJobAction;
import com.atraxo.f4f.model.job.ProcessEmailJob;
import com.atraxo.f4f.model.job.ProcessJob;
import com.atraxo.f4f.model.job.ProcessJobTypeEnum;

/**
 * @author vhojda
 *
 */
public class EmailSchedulableJob extends GenericEmailSchedulableJob {

	/**
	 * 
	 */
	public EmailSchedulableJob() {
		super();
	}

	@Override
	public boolean scheduleJob(ProcessJob job) throws SchedulerException {
		ProcessEmailJob emailJob = (ProcessEmailJob) job;

		JobDataMap jobData = new JobDataMap();
		jobData.put(QuartzJobAction.PROCESS_JOB_ID, emailJob.getId()+"");
		jobData.put(QuartzJobAction.PROCESS_JOB_TYPE, ProcessJobTypeEnum.EMAIL.name());

		JobKey jk = job.formJobKeyFromProcessJob();

		JobDetail quartzJob = JobBuilder.newJob(QuartzJobAction.class)
				.withIdentity(jk).usingJobData(jobData).build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(job.getId()+"")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule())
				.startAt(job.getStartDate())
				.build();

		jobScheduler.schedule(quartzJob, trigger);
		
		return true;
	}



}
