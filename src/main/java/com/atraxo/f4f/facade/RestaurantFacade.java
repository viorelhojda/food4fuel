package com.atraxo.f4f.facade;

import com.atraxo.f4f.dao.RestaurantDAO;
import com.atraxo.f4f.dao.RestaurantMenuItemDAO;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;

/**
 * @author vhojda
 *
 */
public class RestaurantFacade {

	private RestaurantDAO restaurantDAO = new RestaurantDAO();
	private RestaurantMenuItemDAO restaurantMenuItemDAO = new RestaurantMenuItemDAO();

	/**
	 * @param name
	 * @return
	 */
	public Restaurant findByName(String name){
		Restaurant res = null;
		restaurantDAO.beginTransaction();
		res = restaurantDAO.findByName(name);
		restaurantDAO.commitAndCloseTransaction();
		return res;
	}

	public void insertRestaurant(Restaurant res){
		restaurantDAO.beginTransaction();
		restaurantDAO.save(res);
		restaurantDAO.commitAndCloseTransaction();
	}

	public void inactivateMenuItems(Restaurant res){
		for ( RestaurantMenuItem rmi : res.getMenuItems() ){
			rmi.setActive(false);
		}
		restaurantDAO.beginTransaction();
		restaurantDAO.update(res);
		restaurantDAO.commitAndCloseTransaction();
	}

}