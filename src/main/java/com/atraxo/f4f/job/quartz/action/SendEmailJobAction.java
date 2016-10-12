package com.atraxo.f4f.job.quartz.action;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;

import com.atraxo.f4f.facade.ProcessJobFacade;
import com.atraxo.f4f.job.quartz.JobActionResponseEnum;
import com.atraxo.f4f.model.job.ProcessEmailJob;
import com.atraxo.f4f.service.email.WsClientEmail;

/**
 * @author vhojda
 *
 */
public class SendEmailJobAction extends GenericJobAction {

	private static final Logger LOGGER = Logger.getLogger(SendEmailJobAction.class);

	private ProcessJobFacade processJobFacade = new ProcessJobFacade();

    @Override
    public JobActionResponseEnum getSuccessJobActionResponse() {
        return JobActionResponseEnum.FINISHED;
    }
	/*
	 * (non-Javadoc)
	 * @see com.penta.gac.quartz.GenericJobAction#executeJobAction(org.quartz.JobDataMap)
	 */
	@Override
	public boolean executeJobAction(JobDataMap jobDataMap) throws JobExecutionException {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START executeJobAction()");
		}
		boolean actionExecuted = true;
		
		Integer jobId = jobDataMap.getIntegerFromString(QuartzJobAction.PROCESS_JOB_ID);
		ProcessEmailJob emailJob = (ProcessEmailJob) processJobFacade.find(jobId);
		
		WsClientEmail.sendMail(emailJob.getEmailReceiver(), emailJob.getEmailSender(), 
				emailJob.getEmailCc(), emailJob.getEmailBcc(), emailJob.getEmailSubject(), emailJob.getContent());
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END executeJobAction()");
		}

		return actionExecuted;

	}


}
