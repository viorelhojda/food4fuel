package com.atraxo.f4f.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Logger;

import com.atraxo.f4f.facade.OrderMenuItemFacade;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.restaurant.dto.WeeklyMenuItemDTO;
import com.atraxo.f4f.util.RestaurantMenuHelper;

@ViewScoped
@ManagedBean(name="moneyMB")
public class MoneyMB extends AbstractMB implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(MoneyMB.class);

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private static final long serialVersionUID = 1L;

	private OrderMenuItemFacade orderMenuItemFacade = new OrderMenuItemFacade();

	private List<WeeklyMenuItemDTO> weeklyItems;
	private WeeklyMenuItemDTO weeklyItem;
	
	/**
	 * 
	 */
	public MoneyMB(){

	}

	@PostConstruct
	public void init(){
		loadWeeklyItems();
	}

	/**
	 * 
	 */
	private void loadWeeklyItems() {
		List<OrderRestaurantMenuItem> items = orderMenuItemFacade.getPayableThisWeekItems();
		
		//TODO setup for weekly items / restaurant (right now they are all in one place, for 1 restaurant only)
		weeklyItems = new ArrayList<>();
		Map<Restaurant,List<WeeklyMenuItemDTO>> map = RestaurantMenuHelper.extractWeeklyItems(items) ;
		for (Iterator<Restaurant> it = map.keySet().iterator(); it.hasNext();) {
			Restaurant resKey = it.next();
			weeklyItems.addAll(map.get(resKey));
		}
	}
	
	
	/**
	 * 
	 */
	public void payWeeklyMenuItem(WeeklyMenuItemDTO selectedWeeklyItem){
		for ( OrderRestaurantMenuItem item : selectedWeeklyItem.getItems() ) {
			orderMenuItemFacade.pay(item);
		}
		loadWeeklyItems();
	}

	
	
	/**
	 * @return
	 */
	public String getTotalOrderWeekly(){
		BigDecimal bg = BigDecimal.ZERO;
		for ( WeeklyMenuItemDTO item : weeklyItems ) {
			bg = bg.add(item.getPrice());
		}
		return bg.toString();
	}

	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public List<WeeklyMenuItemDTO> getWeeklyItems() {
		return weeklyItems;
	}

	public void setWeeklyItems(List<WeeklyMenuItemDTO> weeklyItems) {
		this.weeklyItems = weeklyItems;
	}

	public WeeklyMenuItemDTO getWeeklyItem() {
		return weeklyItem;
	}

	public void setWeeklyItem(WeeklyMenuItemDTO weeklyItem) {
		this.weeklyItem = weeklyItem;
	}




}