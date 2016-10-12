package com.atraxo.f4f.model.restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vhojda
 *
 */
public enum MenuItemStatus {
	
	NEW, PRE_ORDERED, ORDERED, DELIVERED, CANCELED, PAID;
	
	private static List<MenuItemStatus> payableStatuses;
	private static List<MenuItemStatus> eatableStatuses;

	public boolean canBeDeleted() {
		if ( this.equals(NEW) || this.equals(PRE_ORDERED) ){
			return true;
		}
		return false;
	}
	
	public static List<MenuItemStatus> getPayableStatuses(){
		if ( payableStatuses == null ){
			payableStatuses = new ArrayList<MenuItemStatus>();
			payableStatuses.add(ORDERED);
			payableStatuses.add(DELIVERED);
		}
		return payableStatuses;
	}
	
	public static List<MenuItemStatus> getEatableStatuses(){
		if ( eatableStatuses == null ){
			eatableStatuses = new ArrayList<MenuItemStatus>();
			eatableStatuses.add(PRE_ORDERED);
			eatableStatuses.add(ORDERED);
			eatableStatuses.add(DELIVERED);
		}
		return eatableStatuses;
	}
}
