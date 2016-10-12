package com.atraxo.f4f.job.quartz;

import com.atraxo.f4f.job.quartz.schedulable.EmailSchedulableJob;
import com.atraxo.f4f.job.quartz.schedulable.RepeatEmailSchedulableJob;
import com.atraxo.f4f.model.job.ProcessJobTypeEnum;

/**
 * @author Hojda Viorel
 * @created Feb 20, 2015
 * @since Feb 20, 2015
 *
 *	Simple SchedulableJob factory
 */
public class SchedulableJobFactory {
	
	private static SchedulableJobFactory factory;

	/**
	 * 
	 */
	private SchedulableJobFactory(){
		
	}
	
	/**
	 * @return
	 */
	public static SchedulableJobFactory getFactory() {
		if ( factory == null ){
			factory = new SchedulableJobFactory();
		}
		return factory;
	}
	
    /**
	 * @param type
	 * @return
	 */
	public ISchedulableJob getSchedulableJob(ProcessJobTypeEnum type) {
		if ( type.equals(ProcessJobTypeEnum.EMAIL) ) {
			return new EmailSchedulableJob();
		} 
		else if ( type.equals(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_COLLECT_MONEY) ) {
			return new RepeatEmailSchedulableJob();
		} 
		else if ( type.equals(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_PAY_MONEY) ) {
			return new RepeatEmailSchedulableJob();
		}
		else if ( type.equals(ProcessJobTypeEnum.DAILY_EMAIL_ORDER_FOOD) ) {
			return new RepeatEmailSchedulableJob();
		}
		else if ( type.equals(ProcessJobTypeEnum.DAILY_EMAIL_REMINDER_PREORDER_FOOD) ) {
			return new RepeatEmailSchedulableJob();
		}
		
		return null;
	}


	
	
}
