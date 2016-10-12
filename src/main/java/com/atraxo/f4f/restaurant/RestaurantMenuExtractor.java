package com.atraxo.f4f.restaurant;

import java.io.InputStream;
import java.util.List;

import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;

/**
 * @author vhojda
 *
 */
public interface RestaurantMenuExtractor {

	/**
	 * @return
	 */
	public List<RestaurantMenuItem> extractRestaurantMenuItems(InputStream stream, Restaurant restaurant);
	
	/**
	 * @return
	 */
	public Restaurant createRestaurant();
	
}
