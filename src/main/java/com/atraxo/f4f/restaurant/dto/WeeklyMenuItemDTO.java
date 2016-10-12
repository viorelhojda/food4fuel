package com.atraxo.f4f.restaurant.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.atraxo.f4f.model.restaurant.MenuItemStatus;
import com.atraxo.f4f.model.restaurant.OrderRestaurantMenuItem;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.model.user.User;

public class WeeklyMenuItemDTO {

	private User user;
	
	private BigDecimal price = BigDecimal.ZERO;
	
	private int numberItems = 0;
	
	private Restaurant restaurant;
	
	private WeeklyMenuItemStatusEnum status;
	
	private List<OrderRestaurantMenuItem> items;
	
	public WeeklyMenuItemDTO(){
		items = new ArrayList<OrderRestaurantMenuItem>();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getNumberItems() {
		return numberItems;
	}

	public void setNumberItems(int numberItems) {
		this.numberItems = numberItems;
	}

	public void addNewMenu(OrderRestaurantMenuItem item) {
		price = price.add(item.getMenuItem().getPrice());
		numberItems++;
		items.add(item);
		refreshStatus();
	}

	private void refreshStatus() {
		status = WeeklyMenuItemStatusEnum.PAID;
		for (OrderRestaurantMenuItem item : items){
			if ( !item.getStatus().equals(MenuItemStatus.PAID)) {
				status = WeeklyMenuItemStatusEnum.NOT_PAID;
				break;
			}
		}
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public WeeklyMenuItemStatusEnum getStatus() {
		return status;
	}

	public void setStatus(WeeklyMenuItemStatusEnum status) {
		this.status = status;
	}

	public List<OrderRestaurantMenuItem> getItems() {
		return items;
	}

	public void setItems(List<OrderRestaurantMenuItem> items) {
		this.items = items;
	}
	
}
