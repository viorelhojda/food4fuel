package com.atraxo.f4f.job.quartz.action;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.model.CalculationChain;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;

import com.atraxo.f4f.facade.OrderMenuItemFacade;
import com.atraxo.f4f.facade.ProcessJobFacade;
import com.atraxo.f4f.facade.UserFacade;
import com.atraxo.f4f.job.quartz.JobActionResponseEnum;
import com.atraxo.f4f.model.restaurant.MenuItemStatus;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.service.email.ProcessJobEmailService;

/**
 * @author vhojda
 *
 */
public class PreOrderFoodEmailJobAction extends GenericJobAction {

	private static final Logger LOGGER = Logger.getLogger(PreOrderFoodEmailJobAction.class);

	private ProcessJobFacade processJobFacade = new ProcessJobFacade();
	private OrderMenuItemFacade orderMenuItemFacade = new OrderMenuItemFacade();
	private UserFacade userFacade = new UserFacade();

	@Override
	public JobActionResponseEnum getSuccessJobActionResponse() {
		return JobActionResponseEnum.STAY_ALIVE;
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

		//		Integer jobId = jobDataMap.getIntegerFromString(QuartzJobAction.PROCESS_JOB_ID);
		//		ProcessEmailJob job = (ProcessEmailJob) processJobFacade.find(jobId);

		Calendar calToday = Calendar.getInstance();
		boolean weekend = calToday.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || calToday.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY; 

		if ( !weekend ) { 
			try {
				List<User> users = userFacade.listAllActive();
				for (User user : users){
					List<OrderRestaurantMenuItem> preOrderedItems = orderMenuItemFacade.getTodayItems(user, MenuItemStatus.getEatableStatuses());
					if ( preOrderedItems.isEmpty() ){
						//send mail to pay the money [if the case]
						if( user.getPreferences().getRemindPreOrder()) {
							ProcessJobEmailService.getInstance().sendPreOrderMail(user);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("ERROR:could not execute action !", e);
				actionExecuted = false;
			}
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END executeJobAction()");
		}

		return actionExecuted;

	}


}
