package com.atraxo.f4f.restaurant;

public class ItemDay {

	
	private int weekNumber;
	
	private String day;
	
	private int dayNumber;
	
	private String description = "";
	
	private int nr;

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
	
	
}
