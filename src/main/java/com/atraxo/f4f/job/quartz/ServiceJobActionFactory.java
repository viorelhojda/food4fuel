package com.atraxo.f4f.job.quartz;

import com.atraxo.f4f.job.quartz.action.CollectMoneyEmailJobAction;
import com.atraxo.f4f.job.quartz.action.GenericJobAction;
import com.atraxo.f4f.job.quartz.action.OrderFoodEmailJobAction;
import com.atraxo.f4f.job.quartz.action.PayMoneyEmailJobAction;
import com.atraxo.f4f.job.quartz.action.PreOrderFoodEmailJobAction;
import com.atraxo.f4f.job.quartz.action.SendEmailJobAction;
import com.atraxo.f4f.model.job.ProcessJobTypeEnum;

/**
 * @author Hojda Viorel
 *
 */
public class ServiceJobActionFactory {

	private static ServiceJobActionFactory factory;
	
	/**
	 * 
	 */
	private ServiceJobActionFactory(){
		
	}
	
	public static ServiceJobActionFactory getFactory() {
		if ( factory == null ){
			factory = new ServiceJobActionFactory();
		}
		return factory;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public GenericJobAction getGenericJobActionService(ProcessJobTypeEnum type) {
		if ( type.equals(ProcessJobTypeEnum.EMAIL) ) {
			return new SendEmailJobAction();
		} 
		else if ( type.equals(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_COLLECT_MONEY) ) {
			return new CollectMoneyEmailJobAction();
		} 
		else if ( type.equals(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_PAY_MONEY) ) {
			return new PayMoneyEmailJobAction();
		}
		else if ( type.equals(ProcessJobTypeEnum.DAILY_EMAIL_ORDER_FOOD) ) {
			return new OrderFoodEmailJobAction();
		}
		else if ( type.equals(ProcessJobTypeEnum.DAILY_EMAIL_REMINDER_PREORDER_FOOD) ) {
			return new PreOrderFoodEmailJobAction();
		}
		return null;
	}

}
