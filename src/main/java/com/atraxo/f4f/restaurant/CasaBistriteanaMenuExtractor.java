package com.atraxo.f4f.restaurant;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.atraxo.f4f.model.restaurant.Restaurant;
import com.atraxo.f4f.model.restaurant.RestaurantMenuItem;
import com.atraxo.f4f.restaurant.dto.ItemDayDTO;
import com.atraxo.f4f.util.DateUtils;

public class CasaBistriteanaMenuExtractor implements RestaurantMenuExtractor{

	@Override
	public List<RestaurantMenuItem> extractRestaurantMenuItems(InputStream stream, Restaurant restaurant) {
		
		List<RestaurantMenuItem> menuItems = new ArrayList<>();
		
		if ( stream == null) {
			String url = "http://www.lacasabistriteana.ro/dl/MENIU.xls";
			stream = readMenu(url);
		}
		
		List<ItemDayDTO> iDays = processDays(stream);
		
		for (ItemDayDTO id : iDays ){
			RestaurantMenuItem mi = new RestaurantMenuItem();
			mi.setActive(true);
			mi.setDescription(id.getDescription());
			mi.setNumber(id.getNr());
			mi.setRestaurant(restaurant);
			mi.setPrice(id.getPrice()!=null? new BigDecimal(id.getPrice()) : restaurant.getDefaultPrice());
			
			Date date = null;
			if ( id.getWeekNumber() == 1) {
				date = DateUtils.getCurrentWeekDay(id.getDayNumber()).getTime();
			}
			else if ( id.getWeekNumber() == 2) {
				date = DateUtils.getNextWeekDay(id.getDayNumber()).getTime();
			}
			mi.setDate(date);
			
			menuItems.add(mi);
		}
		
		return menuItems;
	}
	
	private List<ItemDayDTO> processDays(InputStream stream) {
		List<ItemDayDTO> itemDays = new ArrayList<>();

		try{
			//Create Workbook instance holding reference to .xlsx file
			Workbook workbook =  WorkbookFactory.create(stream);

			//Get first/desired sheet from the workbook
			Sheet sheet =  workbook.getSheetAt(0);

			int week = 0;
			for ( int j = 0 ; j < 5; j++ ) {
				ItemDayDTO iDay = null;
				int count = 1;
				String day = "";
				if ( j == 0){
					day="LUNI";
				}
				else if ( j == 1){
					day="MARTI";
				}
				else if ( j == 2){
					day="MIERCURI";
				}
				else if ( j == 3){
					day="JOI";
				}
				else if ( j == 4){
					day="VINERI";
				}

				for ( int i = 0 ; i < sheet.getLastRowNum() ; i++ ){
					boolean skipCell = false;

					Row row = sheet.getRow(i);

					if ( row != null ) {
						String cellValue = "";
						Cell cell = row.getCell(j);
						if ( cell != null ){
							cellValue = cell.getStringCellValue().trim();

							if ( cellValue.equals(day) || (day.equals("MARTI")&&cellValue.startsWith("MAR")) ){
								week++;
								count = 1;
							}

							if ( cellValue.equals("")){
								skipCell = true;
							}
							else{
								skipCell = getSkipCell(cellValue);
							}
						}
						else{
							skipCell = true;
						}

						if ( !skipCell ) {
							if ( iDay == null ) {
								iDay = new ItemDayDTO();
								iDay.setDay(day);
								iDay.setDayNumber(j+1);
								iDay.setWeekNumber(week%2!=0 ? 1 : 2);
								iDay.setNr(count++);
								iDay.setPrice(new Double(10.00));
								itemDays.add(iDay);
							}
							iDay.addDescription(cellValue);
						}
						else{
							iDay = null;
						}
					}
				}

				//also add some soup menu items
				ItemDayDTO iDay1 = new ItemDayDTO();
				iDay1.setDay(day);
				iDay1.setDayNumber(j+1);
				iDay1.setWeekNumber(1);
				iDay1.setNr(count);
				iDay1.setPrice(new Double(5));
				iDay1.setDescription("Supa MENIU");
				itemDays.add(iDay1);
				
				ItemDayDTO iDay2 = new ItemDayDTO();
				iDay2.setDay(day);
				iDay2.setDayNumber(j+1);
				iDay2.setWeekNumber(2);
				iDay2.setNr(count);
				iDay2.setPrice(new Double(5));
				iDay2.setDescription("Supa MENIU");
				itemDays.add(iDay2);
				
			}

			Collections.sort(itemDays,new Comparator<ItemDayDTO>() {
				@Override
				public int compare(ItemDayDTO o1, ItemDayDTO o2) {
					return Integer.valueOf(o1.getWeekNumber()).compareTo(Integer.valueOf(o2.getWeekNumber()));
				}
			});

			stream.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return itemDays;
	}


	private InputStream readMenu(String url){

		InputStream myInputStream = null;

		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();

		// Create a method instance.
		GetMethod method = new GetMethod(url);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
				new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			myInputStream = new ByteArrayInputStream(responseBody); 

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		} 
		return myInputStream;
	}
	
	private boolean getSkipCell(String cellValue){
		boolean skipCell = false;
		String[] daysAndMonths = {"LUNI","MAR","MIERCURI","JOI","VINERI","IANUARIE","FEBRUARIE","MARTIE","APRILIE","MAI","IUNIE","IULIE","AUGUST","SEPTEMBRIE","OCTOMBRIE","NOIEMBRIE","DECEMBRIE"};
		cellValue = cellValue.toUpperCase();
		for ( String day : daysAndMonths ){
			if (cellValue.equals(day)||cellValue.contains(day)){
				skipCell = true;
				break;
			}
		}
		return skipCell;
	}

	@Override
	public Restaurant createRestaurant() {
		Restaurant res = new Restaurant();
		res.setName(RestaurantNameEnum.CASA_BISTRITEANA.getName());
		res.setAddress("Brasov | Str. Avram iancu 24 ");
		res.setDescription("La Casa Bistriteana\" este un local unde se poate servi un pranz rapid si gustos, unde va invitam sa gustati ciorbe, produse traditionale, gratare, mancaruri gatite, carnaciori de casa si mititei din productie proprie.");
		res.setEmail("contact@lacasabistriteana.ro");
		res.setPhone("0724389573");
		res.setDefaultPrice(new BigDecimal("10.00"));
		return res;
	}

}
