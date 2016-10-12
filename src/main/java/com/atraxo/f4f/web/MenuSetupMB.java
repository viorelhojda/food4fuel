package com.atraxo.f4f.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.atraxo.f4f.facade.MenuItemFacade;
import com.atraxo.f4f.facade.RestaurantFacade;
import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;
import com.atraxo.f4f.restaurant.CasaBistriteanaMenuExtractor;
import com.atraxo.f4f.restaurant.RestaurantNameEnum;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;

@SessionScoped
@ManagedBean(name="menuSetupMB")
public class MenuSetupMB extends AbstractMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(MenuSetupMB.class);

	private MenuItemFacade menuItemFacade = new MenuItemFacade();
	private RestaurantFacade restaurantFacade = new RestaurantFacade();
	
	private CasaBistriteanaMenuExtractor extractor = new CasaBistriteanaMenuExtractor();

	/**
	 * 
	 */
	public void loadMenuFromWebsite(){
		try {
			Restaurant restaurant = getRestaurant();

			List<RestaurantMenuItem> items = extractor.extractRestaurantMenuItems(null,restaurant);
			restaurantFacade.inactivateMenuItems(restaurant);
			menuItemFacade.insertMenuItems(items);
			
			displayInfoMessageToUser("Succesful uploaded "+ items.size() + " menu items !");
		} catch (Exception e) {
			LOGGER.error("Could not load menu !", e);
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_COULD_NOT_LOAD_MENU));
			e.printStackTrace();
		}
	}


	/**
	 * @param event
	 */
	public void loadMenuFromFile(FileUploadEvent event) {
		try {
			Restaurant restaurant = getRestaurant();
			InputStream fileStream = event.getFile().getInputstream();

			List<RestaurantMenuItem> items = extractor.extractRestaurantMenuItems(fileStream,restaurant);
			restaurantFacade.inactivateMenuItems(restaurant);
			menuItemFacade.insertMenuItems(items);
			
			displayInfoMessageToUser("File "+ event.getFile().getFileName()+" containing " +items.size() + " menu items is uploaded.");
		} catch (IOException e) {
			LOGGER.error("Could not load menu !", e);
			displayErrorMessageToUser(MessageBundleUtils.getMessage(Constants.MSG_COULD_NOT_LOAD_MENU));
			e.printStackTrace();
		}
	}

	private Restaurant getRestaurant() {
		Restaurant restaurant =	restaurantFacade.findByName(RestaurantNameEnum.CASA_BISTRITEANA.getName());
		if ( restaurant == null ){
			restaurant = extractor.createRestaurant();
			restaurantFacade.insertRestaurant(restaurant);
		}
		return restaurant;
	}
}