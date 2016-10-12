package com.atraxo.f4f.restaurant;

/**
 * @author vhojda
 *
 */
public enum RestaurantNameEnum {
	
	CASA_BISTRITEANA("Casa Bistriteana");
	
	private String name;
	
	private RestaurantNameEnum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
