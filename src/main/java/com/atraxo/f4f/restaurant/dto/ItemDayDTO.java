package com.atraxo.f4f.restaurant.dto;

public class ItemDayDTO {
	
	private int weekNumber;
	
	private String day;
	
	private int dayNumber;
	
	private String description = "";
	
	private int nr;
	
	private Double price;

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addDescription(String desc){
		if ( !description.equals("") ){
			description = description +"|" + desc;
		}
		else{
			description = desc;
		}
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	@Override
	public String toString() {
		return "ItemDay [weekNumber=" + weekNumber + ", day=" + day + ", dayNumber=" + dayNumber + ", description="
				+ description + ", nr=" + nr + "]";
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String extractFirstCourse(){
		return description.substring(0,description.indexOf("|"));
	}
	
	public String extractSecondCourse(){
		return description.substring(description.indexOf("|")+1,description.length());
	}
	
	
}
