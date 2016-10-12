package com.atraxo.f4f.facade;

import java.util.Date;
import java.util.List;

import com.atraxo.f4f.dao.RestaurantMenuItemDAO;
import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;
import com.atraxo.f4f.util.DateUtils;

public class MenuItemFacade {

	private RestaurantMenuItemDAO menuItemDAO = new RestaurantMenuItemDAO();

	public void insertMenuItems(List<RestaurantMenuItem> items){
		for ( RestaurantMenuItem mi : items ){
			menuItemDAO.beginTransaction();
			menuItemDAO.save(mi);
			menuItemDAO.commitAndCloseTransaction();
		}
	}

	public void insertMenuItem(RestaurantMenuItem item){
		menuItemDAO.beginTransaction();
		menuItemDAO.save(item);
		menuItemDAO.commitAndCloseTransaction();
	}

	public List<RestaurantMenuItem> findThisWeekItems(Integer restaurantId) {
		menuItemDAO.beginTransaction();
		Date fromDate = DateUtils.getCurrentWeekDay(1).getTime();
		Date toDate = DateUtils.getCurrentWeekDay(7).getTime();
		List<RestaurantMenuItem> items = menuItemDAO.findItemsBetweenDates(restaurantId, fromDate, toDate);
		menuItemDAO.commitAndCloseTransaction();
		return items;
	}

	public List<RestaurantMenuItem> findNextWeekItems(Integer restaurantId) {
		menuItemDAO.beginTransaction();
		Date fromDate = DateUtils.getNextWeekDay(1).getTime();
		Date toDate = DateUtils.getNextWeekDay(7).getTime();
		List<RestaurantMenuItem> items = menuItemDAO.findItemsBetweenDates(restaurantId, fromDate, toDate);
		menuItemDAO.commitAndCloseTransaction();
		return items;
	}

}