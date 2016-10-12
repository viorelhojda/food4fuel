package com.atraxo.f4f.dao;
 
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.restaurant.MenuItemStatus;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.user.User;
 
/**
 * @author vhojda
 *
 */
public class OrderRestaurantMenuItemDAO extends GenericDAO<OrderRestaurantMenuItem> {
 
	private static final Logger LOGGER = Logger.getLogger(OrderRestaurantMenuItemDAO.class);
	
	private static final long serialVersionUID = 1L;

	
	public OrderRestaurantMenuItemDAO() {
        super(OrderRestaurantMenuItem.class);
    }

	/**
	 * @param userId
	 * @return
	 */
	public List<OrderRestaurantMenuItem> findForUser(Integer userId) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findForUser()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		
		List<OrderRestaurantMenuItem> orderItems = findMultipleResults(
				OrderRestaurantMenuItem.FIND_BY_USER, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findForUser()");
		}
		return orderItems;
	}

	/**
	 * @param date
	 * @return
	 */
	public List<OrderRestaurantMenuItem> findForDateAndStatus(Date date, MenuItemStatus status) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findForDateAndStatus()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("date", date);
		parameters.put("status", status);
		
		List<OrderRestaurantMenuItem> orderItems = findMultipleResults(
				OrderRestaurantMenuItem.FIND_BY_DATE_AND_STATUS, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findForDateAndStatus()");
		}
		return orderItems;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public List<OrderRestaurantMenuItem> findForDateAndStatusesAndUser(Date date, Collection<MenuItemStatus> statuses, User user) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findForDateAndStatusAndUser()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("date", date);
		parameters.put("userID", user.getId());
		parameters.put("statuses", statuses);
		
		List<OrderRestaurantMenuItem> orderItems = findMultipleResults(
				OrderRestaurantMenuItem.FIND_BY_DATE_AND_STATUSES_AND_USER, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findForDateAndStatusAndUser()");
		}
		return orderItems;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public List<OrderRestaurantMenuItem> findForDate(Date date) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findForDate()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("date", date);
		
		List<OrderRestaurantMenuItem> orderItems = findMultipleResults(
				OrderRestaurantMenuItem.FIND_BY_DATE, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findForDate()");
		}
		return orderItems;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public List<OrderRestaurantMenuItem> findBetweenDates(Date fromDate, Date toDate, Collection<MenuItemStatus> statuses) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findBetweenDates()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		parameters.put("statuses", statuses);
		
		List<OrderRestaurantMenuItem> orderItems = findMultipleResults(
				OrderRestaurantMenuItem.FIND_BETWEEN_DATES_AND_STATUSES, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findBetweenDates()");
		}
		return orderItems;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public List<OrderRestaurantMenuItem> findBetweenDates(Date fromDate, Date toDate, User user) {
		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("START findBetweenDates()");
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		parameters.put("userID", user.getId());
		
		List<OrderRestaurantMenuItem> orderItems = findMultipleResults(
				OrderRestaurantMenuItem.FIND_BETWEEN_DATES_AND_USER, parameters);

		if ( LOGGER.isDebugEnabled() ){
			LOGGER.debug("END findBetweenDates()");
		}
		return orderItems;
	}
	
	
}