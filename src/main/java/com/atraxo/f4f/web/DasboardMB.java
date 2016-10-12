package com.atraxo.f4f.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.MenuItemFacade;
import com.atraxo.f4f.facade.OrderMenuItemFacade;
import com.atraxo.f4f.facade.RestaurantFacade;
import com.atraxo.f4f.model.restaurant.MenuItemStatus;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;
import com.atraxo.f4f.restaurant.RestaurantNameEnum;
import com.atraxo.f4f.restaurant.dto.MenuItemsDailyDTO;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;

@SessionScoped
@ManagedBean(name="dashboardMB")
public class DasboardMB extends AbstractMB implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(DasboardMB.class);
	
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;
	
	private static final long serialVersionUID = 1L;

	private OrderMenuItemFacade orderMenuItemFacade = new OrderMenuItemFacade();
	private MenuItemFacade menuItemFacade = new MenuItemFacade();
	private RestaurantFacade restaurantFacade = new RestaurantFacade();

	private MenuItemsDailyDTO menuItem;
	
	private OrderRestaurantMenuItem orderMenuItem;

	private List<MenuItemsDailyDTO> thisWeekMenuItems ;
	private List<MenuItemsDailyDTO> nextWeekMenuItems ;
	
	private List<OrderRestaurantMenuItem> orderedMenuItems;

	/**
	 * 
	 */
	public DasboardMB(){
		
	}
	
	@PostConstruct
	public void init(){
		loadOrderedMemuItems();
	}
	
	/**
	 * 
	 */
	private void loadOrderedMemuItems() {
		orderedMenuItems = orderMenuItemFacade.findAllForUser(userMB.getUser().getId());
	}

	public List<MenuItemsDailyDTO> getThisWeekMenuItems() {
		if (thisWeekMenuItems == null){
			loadThisWeekMenuItems();
		}
		return thisWeekMenuItems;
	}

	public List<MenuItemsDailyDTO> getNextWeekMenuItems() {
		if (nextWeekMenuItems == null){
			loadNextWeekMenuItems();
		}
		return nextWeekMenuItems;
	}

	private void loadThisWeekMenuItems() {
		Restaurant res = restaurantFacade.findByName(RestaurantNameEnum.CASA_BISTRITEANA.getName());
		if ( res != null ) {
			thisWeekMenuItems = new ArrayList<MenuItemsDailyDTO>();
			List<RestaurantMenuItem> items = menuItemFacade.findThisWeekItems(res.getId());

			for (RestaurantMenuItem rim : items ){
				MenuItemsDailyDTO dailyItem = getDailyItemByNumber(rim.getNumber(),thisWeekMenuItems);
				dailyItem.setDay(rim);
			}
		}
	}

	private void loadNextWeekMenuItems() {
		Restaurant res = restaurantFacade.findByName(RestaurantNameEnum.CASA_BISTRITEANA.getName());
		if ( res != null ) {
			nextWeekMenuItems = new ArrayList<MenuItemsDailyDTO>();
			List<RestaurantMenuItem> items = menuItemFacade.findNextWeekItems(res.getId());

			for (RestaurantMenuItem rim : items ){
				MenuItemsDailyDTO dailyItem = getDailyItemByNumber(rim.getNumber(),nextWeekMenuItems);
				dailyItem.setDay(rim);
			}
		}
	}

	private MenuItemsDailyDTO getDailyItemByNumber(Integer menuNr, List<MenuItemsDailyDTO> dailyItems) {
		MenuItemsDailyDTO dailyDto = null;
		for ( MenuItemsDailyDTO daily : dailyItems ){
			if (daily.getNumber() == menuNr.intValue()) {
				dailyDto = daily;
				break;
			}
		}
		if (dailyDto == null) {
			dailyDto = new MenuItemsDailyDTO();
			dailyDto.setNumber(menuNr);
			dailyItems.add(dailyDto);
		}
		return dailyDto;
	}

	public String getColumnStyleForToday(RestaurantMenuItem item){
		if (item != null && item.getDate()!=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(item.getDate());
			Calendar now = Calendar.getInstance();
			if (now.get(Calendar.DAY_OF_WEEK) == cal.get(Calendar.DAY_OF_WEEK)) {
				return "color: blue;";
			}
		}
		return "";
	}

	public void preorderMenuItem(RestaurantMenuItem rim){
		OrderRestaurantMenuItem orderMenuItem = new OrderRestaurantMenuItem();
		orderMenuItem.setMenuItem(rim);
		orderMenuItem.setStatus(MenuItemStatus.NEW);
		orderMenuItem.setUser(userMB.getUser());
		
		orderedMenuItems.add(orderMenuItem);
	}
	
	/**
	 * @param item
	 */
	public void deleteOrderedMenuItems(OrderRestaurantMenuItem item){
		try{
			orderedMenuItems.remove(item);
			if ( item.getId() != null ){
				orderMenuItemFacade.delete(item);
			}
			displayInfoMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_DELETE_OK));
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Could not delete order menu items", e);
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_DELETE_NOT_OK));
		}
	}
	
	/**
	 * @param item
	 * @return
	 */
	public boolean shouldDisplay(RestaurantMenuItem item){
		return item.getId()!=null;
	}
	
	/**
	 * @param item
	 * @return
	 */
	public boolean canDeleteOrderedMenuItem(OrderRestaurantMenuItem item){
		return item.getStatus().canBeDeleted();
	}
	
	
	/**
	 * 
	 */
	public void placePreOrder(){
		List<OrderRestaurantMenuItem> items = new ArrayList<>();
		for ( OrderRestaurantMenuItem orderMenuItem : orderedMenuItems ) {
			if ( orderMenuItem.getStatus().equals(MenuItemStatus.NEW)){
				items.add(orderMenuItem);
			}
		}
		orderMenuItemFacade.preorderItems(items);
		
		loadOrderedMemuItems();
	}


	public MenuItemsDailyDTO getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItemsDailyDTO menuItem) {
		this.menuItem = menuItem;
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

	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}


}