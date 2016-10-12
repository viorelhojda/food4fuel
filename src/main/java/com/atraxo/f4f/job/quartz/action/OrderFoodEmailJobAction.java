package com.atraxo.f4f.job.quartz.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;

import com.atraxo.f4f.facade.OrderMenuItemFacade;
import com.atraxo.f4f.facade.ProcessJobFacade;
import com.atraxo.f4f.job.quartz.JobActionResponseEnum;
import com.atraxo.f4f.model.restaurant.MenuItemStatus;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.service.email.ProcessJobEmailService;
import com.atraxo.f4f.util.RestaurantMenuHelper;

/**
 * @author vhojda
 *
 */
public class OrderFoodEmailJobAction extends GenericJobAction {

	private static final Logger LOGGER = Logger.getLogger(OrderFoodEmailJobAction.class);

	private ProcessJobFacade processJobFacade = new ProcessJobFacade();

	private OrderMenuItemFacade orderMenuItemFacade = new OrderMenuItemFacade();

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

		try {
			List<OrderRestaurantMenuItem> items = orderMenuItemFacade.getTodayItems(MenuItemStatus.ORDERED);

			Map<Restaurant, Map<Integer,List<OrderRestaurantMenuItem>> > fullMap = RestaurantMenuHelper.extractFullRestaurantMenuItemsMap(items);

			for ( Iterator<Restaurant> it = fullMap.keySet().iterator(); it.hasNext(); ){
				Restaurant resKey = it.next();
				Map<Integer,List<OrderRestaurantMenuItem>> resMap = fullMap.get(resKey);

				if (!resMap.isEmpty()) {

					StringBuilder sb = new StringBuilder();
					for ( Iterator<Integer> itm = resMap.keySet().iterator(); itm.hasNext(); ){
						Integer nrKey = itm.next();
						List<OrderRestaurantMenuItem> listItems = resMap.get(nrKey);
						if(!listItems.isEmpty()) {
							sb.append(listItems.size()).append(" X Menu ").append(nrKey);
							sb.append(" (").append(listItems.get(0).getMenuItem().getDescription()).append(")");
							sb.append(";</br>");
						}
					}
					
					//send mail to restaurant in order to order food
					ProcessJobEmailService.getInstance().sendOrderFoodMail(resKey, sb.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ERROR:could not execute action !", e);
			actionExecuted = false;
		}

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END executeJobAction()");
		}

		return actionExecuted;

	}


}
