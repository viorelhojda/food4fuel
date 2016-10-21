package com.atraxo.f4f.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.atraxo.f4f.model.restaurant.MenuItemStatus;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.restaurant.dto.WeeklyMenuItemDTO;
import com.atraxo.f4f.restaurant.dto.WeeklyMenuItemStatusEnum;

public class RestaurantMenuHelper {

	/**
	 * @param items
	 * @return
	 */
	public static Map<Restaurant, Map<Integer,List<OrderRestaurantMenuItem>> > extractFullRestaurantMenuItemsMap(List<OrderRestaurantMenuItem> items){
		Collections.sort(items, new Comparator<OrderRestaurantMenuItem>() {
			@Override
			public int compare(OrderRestaurantMenuItem o1, OrderRestaurantMenuItem o2) {
				return o1.getMenuItem().getRestaurant().getName().compareTo(o2.getMenuItem().getRestaurant().getName());
			}
		});

		HashMap<Restaurant, List<OrderRestaurantMenuItem>> map = new HashMap<Restaurant, List<OrderRestaurantMenuItem>>();

		for ( OrderRestaurantMenuItem item : items ) {
			Restaurant resKey = item.getMenuItem().getRestaurant();
			if ( !map.containsKey(resKey)) {
				map.put(resKey, new ArrayList<OrderRestaurantMenuItem>());
			}
			map.get(resKey).add(item);
		}

		//create the full Map
		Map<Restaurant, Map<Integer,List<OrderRestaurantMenuItem>> > fullMap = new HashMap<Restaurant, Map<Integer,List<OrderRestaurantMenuItem>>>();

		for ( Iterator<Restaurant> it = map.keySet().iterator(); it.hasNext(); ){
			Restaurant resKey = it.next();

			if ( !fullMap.containsKey(resKey) ){
				fullMap.put(resKey, new HashMap<Integer,List<OrderRestaurantMenuItem>>());
			}
			Map<Integer,List<OrderRestaurantMenuItem>> menuMap = fullMap.get(resKey);

			List<OrderRestaurantMenuItem> restaurantItems = map.get(resKey);
			for ( OrderRestaurantMenuItem resItem : restaurantItems ){
				Integer menuNumber = resItem.getMenuItem().getNumber();
				if ( !menuMap.containsKey(menuNumber)){
					menuMap.put(menuNumber, new ArrayList<OrderRestaurantMenuItem>());
				}
				menuMap.get(menuNumber).add(resItem);
			}
		}

		return fullMap;
	}


	/**
	 * @param users
	 * @return
	 */
	public static String getEmailForUsers(List<User> users){
		StringBuilder email = new StringBuilder();
		for ( Iterator<User> it = users.iterator();it.hasNext(); ){
			email.append(it.next().getAccount().getEmail());
			if (it.hasNext() ){
				email.append(",");
			}
		}
		return email.toString();
	}
	
	/**
	 * @param users
	 * @return
	 */
	public static String getNamesForUsers(List<User> users){
		StringBuilder email = new StringBuilder();
		for ( User u : users ){
			if(!u.isAdmin()) {
				email.append(u.getAccount().getFirstNameLastName()).append(";");
			}
		}
		return email.toString();
	}


	/**
	 * @param items
	 * @return
	 */
	public static Map<Restaurant,List<WeeklyMenuItemDTO>> extractWeeklyItems(List<OrderRestaurantMenuItem> items){
		Map<Restaurant,List<WeeklyMenuItemDTO>> fullMap = new HashMap<Restaurant,List<WeeklyMenuItemDTO>>();
		
		for ( OrderRestaurantMenuItem item : items ){
			
			Restaurant restaurant = item.getMenuItem().getRestaurant();
			if (!fullMap.containsKey(restaurant)){
				fullMap.put(restaurant, new ArrayList<WeeklyMenuItemDTO>());
			}
			List<WeeklyMenuItemDTO> weeklyItems = fullMap.get(restaurant);
			
			WeeklyMenuItemDTO weeklyItem = getUserWeeklyItem(item.getUser(),weeklyItems);
			weeklyItem.addNewMenu(item);
		}
		
		return fullMap;
	}

	/**
	 * @param user
	 * @return
	 */
	private static WeeklyMenuItemDTO getUserWeeklyItem(User user, List<WeeklyMenuItemDTO> weeklyItems) {
		WeeklyMenuItemDTO foundWeeklyItem = null;
		for ( WeeklyMenuItemDTO itm : weeklyItems  ) {
			if ( itm.getUser().getId().equals(user.getId())) {
				foundWeeklyItem = itm;
				break;
			}
		}
		if ( foundWeeklyItem == null ) {
			foundWeeklyItem = new WeeklyMenuItemDTO();
			foundWeeklyItem.setUser(user);
			weeklyItems.add(foundWeeklyItem);
		}
		return foundWeeklyItem;
	}
}
