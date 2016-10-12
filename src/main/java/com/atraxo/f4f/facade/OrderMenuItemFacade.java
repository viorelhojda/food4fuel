package com.atraxo.f4f.facade;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.atraxo.f4f.dao.OrderRestaurantMenuItemDAO;
import com.atraxo.f4f.model.restaurant.MenuItemStatus;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.util.DateUtils;

/**
 * @author vhojda
 *
 */
public class OrderMenuItemFacade {

	private static final Logger LOGGER = Logger.getLogger(OrderMenuItemFacade.class);
	
	private OrderRestaurantMenuItemDAO orderRestaurantMenuItemDAO = new OrderRestaurantMenuItemDAO();

	/**
	 * @param userId
	 * @return
	 */
	public List<OrderRestaurantMenuItem> findAllForUser(Integer userId){
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findAllForUser()");
		}
		orderRestaurantMenuItemDAO.beginTransaction();
		List<OrderRestaurantMenuItem> orderItems = orderRestaurantMenuItemDAO.findForUser(userId);
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findAllForUser()");
		}
		
		return orderItems;
	}

	/**
	 * @param item
	 */
	public void delete(OrderRestaurantMenuItem item) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START delete()");
		}
		orderRestaurantMenuItemDAO.beginTransaction();
		orderRestaurantMenuItemDAO.delete(item);
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END delete()");
		}
		
	}

	/**
	 * @param items
	 */
	public void preorderItems(List<OrderRestaurantMenuItem> items) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START orderItems()");
		}
		
		orderRestaurantMenuItemDAO.beginTransaction();
		for ( OrderRestaurantMenuItem item : items ) {
			item.setStatus(MenuItemStatus.PRE_ORDERED);
			orderRestaurantMenuItemDAO.save(item);
		}
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END orderItems()");
		}
	}

	/**
	 * @return
	 */
	public List<OrderRestaurantMenuItem> getPayableThisWeekItems() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getThisWeekItems()");
		}
		orderRestaurantMenuItemDAO.beginTransaction();
		List<OrderRestaurantMenuItem> orderItems = orderRestaurantMenuItemDAO.findBetweenDates(
				DateUtils.getCurrentWeekDay(1).getTime(), 
				DateUtils.getCurrentWeekDay(7).getTime(),
				MenuItemStatus.getPayableStatuses());
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
		
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getThisWeekItems()");
		}
		return orderItems;
	}
	
	/**
	 * @return
	 */
	public List<OrderRestaurantMenuItem> getThisWeekItems(User user) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getThisWeekItems()");
		}
		orderRestaurantMenuItemDAO.beginTransaction();
		List<OrderRestaurantMenuItem> orderItems = orderRestaurantMenuItemDAO.findBetweenDates(
				DateUtils.getCurrentWeekDay(1).getTime(), DateUtils.getCurrentWeekDay(7).getTime(), user);
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getThisWeekItems()");
		}
		return orderItems;
	}
	
	/**
	 * @return
	 */
	public List<OrderRestaurantMenuItem> getTodayItems(User user,Collection<MenuItemStatus> statuses) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getTodayItems()");
		}
		orderRestaurantMenuItemDAO.beginTransaction();
		List<OrderRestaurantMenuItem> orderItems = orderRestaurantMenuItemDAO.findForDateAndStatusesAndUser(new Date(), statuses, user);
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getTodayItems()");
		}
		return orderItems;
	}
	
	/**
	 * @return
	 */
	public List<OrderRestaurantMenuItem> getTodayItems() {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getTodayItems()");
		}
		Date todayDate = new Date();
		
		orderRestaurantMenuItemDAO.beginTransaction();
		List<OrderRestaurantMenuItem> orderItems = orderRestaurantMenuItemDAO.findForDate(todayDate);
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getTodayItems()");
		}
		return orderItems;
	}
	
	/**
	 * @return
	 */
	public List<OrderRestaurantMenuItem> getTodayItems(MenuItemStatus status) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START getTodayItems()");
		}
		Date todayDate = new Date();
		
		orderRestaurantMenuItemDAO.beginTransaction();
		List<OrderRestaurantMenuItem> orderItems = orderRestaurantMenuItemDAO.findForDateAndStatus(todayDate, status);
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
		
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END getTodayItems()");
		}
		return orderItems;
	}

	
	/**
	 * @param preorderedMenuItems
	 */
	public void placeOrder(List<OrderRestaurantMenuItem> preorderedMenuItems) {
		orderRestaurantMenuItemDAO.beginTransaction();
		for (OrderRestaurantMenuItem item : preorderedMenuItems ) {
			item.setStatus(MenuItemStatus.ORDERED);
			orderRestaurantMenuItemDAO.update(item);
		}
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
	}

	/**
	 * @param item
	 */
	public void pay(OrderRestaurantMenuItem item) {
		orderRestaurantMenuItemDAO.beginTransaction();
		OrderRestaurantMenuItem dbItem = orderRestaurantMenuItemDAO.find(item.getId());
		dbItem.setStatus(MenuItemStatus.PAID);
		orderRestaurantMenuItemDAO.commitAndCloseTransaction();
	}

}