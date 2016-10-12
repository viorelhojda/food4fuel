package com.atraxo.f4f.job.quartz.action;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionException;

import com.atraxo.f4f.facade.OrderMenuItemFacade;
import com.atraxo.f4f.facade.ProcessJobFacade;
import com.atraxo.f4f.job.quartz.JobActionResponseEnum;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.restaurant.dto.WeeklyMenuItemDTO;
import com.atraxo.f4f.service.email.ProcessJobEmailService;
import com.atraxo.f4f.util.RestaurantMenuHelper;

/**
 * @author vhojda
 *
 */
public class CollectMoneyEmailJobAction extends GenericJobAction {

	private static final Logger LOGGER = Logger.getLogger(CollectMoneyEmailJobAction.class);

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
			List<OrderRestaurantMenuItem> items = orderMenuItemFacade.getPayableThisWeekItems();
			
			StringBuilder sb = new StringBuilder();
			BigDecimal total = BigDecimal.ZERO;
			
			Map<Restaurant,List<WeeklyMenuItemDTO>> map = RestaurantMenuHelper.extractWeeklyItems(items) ;
			
			for (Iterator<Restaurant> it = map.keySet().iterator(); it.hasNext();) {
				Restaurant resKey = it.next();
				List<WeeklyMenuItemDTO> weeklyItems = map.get(resKey);
				
				sb.append(resKey.getName()).append(": ").append("<br/>");
				
				for (WeeklyMenuItemDTO weeklyItem : weeklyItems ) {
					sb.append(weeklyItem.getPrice());
					sb.append(" RON - "); 
					sb.append(weeklyItem.getUser().getAccount().getDescriptor());
					sb.append("<br/>");
					total = total.add(weeklyItem.getPrice());
				}
				
				sb.append("<br/><br/>");
			}
			
			//send mail to the responsible with collecting money
			ProcessJobEmailService.getInstance().sendCollectMoneyMail(sb.toString());
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
