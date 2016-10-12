package com.atraxo.f4f.dao;
 
import java.util.HashMap;
import java.util.Map;

import com.atraxo.f4f.model.restaurant.Restaurant;
 
public class RestaurantDAO extends GenericDAO<Restaurant> {
 
	private static final long serialVersionUID = 1L;

	public RestaurantDAO() {
        super(Restaurant.class);
    }
	
	public Restaurant findByName(String name){
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", name);
		return findOneResult(Restaurant.FIND_BY_NAME, parameters);
	}
	
}