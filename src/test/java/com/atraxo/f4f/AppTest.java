package com.atraxo.f4f;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.atraxo.f4f.facade.MenuItemFacade;
import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;
import com.atraxo.f4f.restaurant.dto.MenuItemsDailyDTO;
import com.atraxo.f4f.util.DateUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testFindThisWeekItems(){
    	MenuItemFacade menuItemFacade = new MenuItemFacade();
    	List<RestaurantMenuItem> items = menuItemFacade.findThisWeekItems(1);
    	
    	List<MenuItemsDailyDTO> dailyItems = new ArrayList<>();
    	for (RestaurantMenuItem rim : items ){
    		MenuItemsDailyDTO dailyItem = getDailyItemByNumber(rim.getNumber(),dailyItems);
    		dailyItem.setDay(rim);
    	}
    	
    	assertTrue(!items.isEmpty());
    	
    }

	/**
	 * @param menuNr
	 * @param dailyItems
	 * @return
	 */
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
}
