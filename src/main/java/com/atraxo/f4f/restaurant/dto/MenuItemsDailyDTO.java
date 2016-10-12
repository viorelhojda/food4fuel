package com.atraxo.f4f.restaurant.dto;

import java.util.Calendar;

import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;

public class MenuItemsDailyDTO {

	private int number;
	
	private RestaurantMenuItem monday;
	private RestaurantMenuItem tuesday;
	private RestaurantMenuItem wednesday;
	private RestaurantMenuItem thursday;
	private RestaurantMenuItem friday;
	
	public MenuItemsDailyDTO(){
		monday = new RestaurantMenuItem();
		tuesday = new RestaurantMenuItem();
		wednesday = new RestaurantMenuItem();
		thursday = new RestaurantMenuItem();
		friday = new RestaurantMenuItem();
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public RestaurantMenuItem getMonday() {
		return monday;
	}
	public void setMonday(RestaurantMenuItem monday) {
		this.monday = monday;
	}
	public RestaurantMenuItem getTuesday() {
		return tuesday;
	}
	public void setTuesday(RestaurantMenuItem tuesday) {
		this.tuesday = tuesday;
	}
	public RestaurantMenuItem getWednesday() {
		return wednesday;
	}
	public void setWednesday(RestaurantMenuItem wednesday) {
		this.wednesday = wednesday;
	}
	public RestaurantMenuItem getThursday() {
		return thursday;
	}
	public void setThursday(RestaurantMenuItem thursday) {
		this.thursday = thursday;
	}
	public RestaurantMenuItem getFriday() {
		return friday;
	}
	public void setFriday(RestaurantMenuItem friday) {
		this.friday = friday;
	}
	
	public void setDay(RestaurantMenuItem rim) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(rim.getDate());
		
		if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
			setMonday(rim);
		}
		else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY){
			setTuesday(rim);
		}
		else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY){
			setWednesday(rim);
		}
		else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY){
			setThursday(rim);
		}
		else if (cal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
			setFriday(rim);
		}
	}
	@Override
	public String toString() {
		return "MenuItemsDailyDTO [number=" + number + ", monday=" + monday + ", tuesday=" + tuesday + ", wednesday="
				+ wednesday + ", thursday=" + thursday + ", friday=" + friday + "]";
	}
	
	
}
