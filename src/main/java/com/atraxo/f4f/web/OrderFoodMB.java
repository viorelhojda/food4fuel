package com.atraxo.f4f.web;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.OrderMenuItemFacade;
import com.atraxo.f4f.model.restaurant.MenuItemStatus;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;
import com.atraxo.f4f.util.RestaurantMenuHelper;

@ViewScoped
@ManagedBean(name="orderFoodMB")
public class OrderFoodMB extends AbstractMB implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(OrderFoodMB.class);

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private static final long serialVersionUID = 1L;

	private OrderMenuItemFacade orderMenuItemFacade = new OrderMenuItemFacade();

	private OrderRestaurantMenuItem preorderMenuItem;
	private List<OrderRestaurantMenuItem> preorderedMenuItems;

	private OrderRestaurantMenuItem orderMenuItem;
	private List<OrderRestaurantMenuItem> orderedMenuItems;

	/**
	 * 
	 */
	public OrderFoodMB(){

	}

	@PostConstruct
	public void init(){
		loadTodayPreorderdMenuItems();
		loadTodayOrderedMenuItems();
	}

	private void loadTodayOrderedMenuItems() {
		orderedMenuItems = orderMenuItemFacade.getTodayItems();
	}

	private void loadTodayPreorderdMenuItems() {
		preorderedMenuItems = orderMenuItemFacade.getTodayItems(MenuItemStatus.PRE_ORDERED);
	}

	/**
	 * 
	 */
	public void placeOrder(){
		try {
			orderMenuItemFacade.placeOrder(preorderedMenuItems);

			displayInfoMessageToUser("Ordered "+getTotalPreorderToday() );

			loadTodayPreorderdMenuItems();
			loadTodayOrderedMenuItems();

		} catch (Exception e) {
			LOGGER.error("ERRO:could not order menus !");
			displayErrorMessageToUser("Could not order menus !");
			e.printStackTrace();
		}

	}
	
	public String getTotalPreorderToday(){
		return getTotalToday(preorderedMenuItems);
	}
	public String getTotalOrderToday(){
		return getTotalToday(orderedMenuItems);
	}

	public String getTotalToday(List<OrderRestaurantMenuItem> items){
		if (items.isEmpty()){
			return MessageBundleUtils.getMessage(Constants.MSG_NA);
		}
		
		StringBuilder sb = new StringBuilder();

		Map<Restaurant, Map<Integer,List<OrderRestaurantMenuItem>> > fullMap = RestaurantMenuHelper.extractFullRestaurantMenuItemsMap(items);
		
		for ( Iterator<Restaurant> it = fullMap.keySet().iterator(); it.hasNext(); ){
			Restaurant resKey = it.next();
			Map<Integer,List<OrderRestaurantMenuItem>> resMap = fullMap.get(resKey);
			
			sb.append(resKey.getName()+":");
			
			for ( Iterator<Integer> itm = resMap.keySet().iterator(); itm.hasNext(); ){
				Integer nrKey = itm.next();
				List<OrderRestaurantMenuItem> listItems = resMap.get(nrKey);
				if(!listItems.isEmpty()) {
					sb.append(listItems.size()).append(" X Menu ").append(nrKey).append("; ");
				}
			}
			
			sb.append("\r\n");
		}
		
		return sb.toString();
	}
	
	
	
	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public List<OrderRestaurantMenuItem> getPreorderedMenuItems() {
		return preorderedMenuItems;
	}

	public void setPreorderedMenuItems(List<OrderRestaurantMenuItem> preorderedMenuItems) {
		this.preorderedMenuItems = preorderedMenuItems;
	}

	public OrderRestaurantMenuItem getPreorderMenuItem() {
		return preorderMenuItem;
	}

	public void setPreorderMenuItem(OrderRestaurantMenuItem preorderMenuItem) {
		this.preorderMenuItem = preorderMenuItem;
	}

	public List<OrderRestaurantMenuItem> getOrderedMenuItems() {
		return orderedMenuItems;
	}

	public void setOrderedMenuItems(List<OrderRestaurantMenuItem> orderedMenuItems) {
		this.orderedMenuItems = orderedMenuItems;
	}

	public OrderRestaurantMenuItem getOrderMenuItem() {
		return orderMenuItem;
	}

	public void setOrderMenuItem(OrderRestaurantMenuItem orderMenuItem) {
		this.orderMenuItem = orderMenuItem;
	}



}