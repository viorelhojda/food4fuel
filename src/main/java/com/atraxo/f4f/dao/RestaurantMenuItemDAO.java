package com.atraxo.f4f.dao;
 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;
 
/**
 * @author vhojda
 *
 */
public class RestaurantMenuItemDAO extends GenericDAO<RestaurantMenuItem> {
 
	private static final long serialVersionUID = 1L;

	public RestaurantMenuItemDAO() {
        super(RestaurantMenuItem.class);
    }
	
	public List<RestaurantMenuItem> findItemsBetweenDates(Integer restaurantId, Date from, Date to){
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("restaurantId", restaurantId);
		parameters.put("fromDate", from);
		parameters.put("toDate", to);
		return findMultipleResults(RestaurantMenuItem.FIND_BEETWEEN_DATES, parameters);
	}
	
}