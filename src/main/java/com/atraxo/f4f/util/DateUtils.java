package com.atraxo.f4f.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	public static final String DATE_PATTERN = "dd-MM-yyyy";
	public static final String TIME_PATTERN = "dd-MM-yyyy HH:mm";
	public static final String SECONDS_PATTERN = "dd-MM-yyyy HH:mm:ss";
	public static final String HOUR_PATTERN = "HH:mm";
	public static final String TIME24HOURS_PATTERN = "^(([0-9])|([0-1][0-9])|([2][0-3])):([0-5][0-9])$";

	public static final String CALENDAR_PATTERN = "EEE MMM dd hh:ss:SS zzzz yyyy";

	
	public static Calendar getFutureDay(int hour, int minutes){
		Calendar calNextDay = DateUtils.getNextDay();
		calNextDay.set(Calendar.HOUR_OF_DAY, hour);
		calNextDay.set(Calendar.MINUTE, minutes);
		calNextDay.set(Calendar.SECOND, 0);
		calNextDay.set(Calendar.MILLISECOND, 0);
		
		Calendar calThisDay = Calendar.getInstance();
		calThisDay.set(Calendar.HOUR_OF_DAY, hour);
		calThisDay.set(Calendar.MINUTE, minutes);
		calThisDay.set(Calendar.SECOND, 0);
		calThisDay.set(Calendar.MILLISECOND, 0);
		
		Calendar now = Calendar.getInstance();
		
		if ( calThisDay.before(now) ) {
			return calThisDay;
		}
		else{
			return calNextDay;
		}
	}
	
	public static Calendar getPreviousDay(int hour, int minutes){
		Calendar calPreviousDay = Calendar.getInstance();
		calPreviousDay.add(Calendar.DAY_OF_MONTH, -1);
		calPreviousDay.set(Calendar.HOUR_OF_DAY, hour);
		calPreviousDay.set(Calendar.MINUTE, minutes);
		calPreviousDay.set(Calendar.SECOND, 0);
		calPreviousDay.set(Calendar.MILLISECOND, 0);
		return calPreviousDay;
	}
	
	public static Calendar getNextDay(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal;
	}
	
	public static Calendar getCurrentWeekDay(int numberOfDayInWeek){
		Calendar calMonday = Calendar.getInstance();
		boolean isMonday = calMonday.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
		while ( !isMonday ) {
			calMonday.add(Calendar.DAY_OF_MONTH, -1);
			isMonday = calMonday.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
		}
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(calMonday.getTime());
		cal.add(Calendar.DAY_OF_MONTH, numberOfDayInWeek-1);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		return cal;
	}
	
	public static Calendar getNextWeekDay(int numberOfDayInWeek){
		Calendar cal = getCurrentWeekDay(numberOfDayInWeek);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		return cal;
	}
	
	public static Calendar getNextNextWeekDay(int numberOfDayInWeek){
		Calendar cal = getCurrentWeekDay(numberOfDayInWeek);
		cal.add(Calendar.DAY_OF_MONTH, 14);
		return cal;
	}
	
	public static Calendar getPreviousWeekDay(int numberOfDayInWeek){
		Calendar cal = getCurrentWeekDay(numberOfDayInWeek);
		cal.add(Calendar.DAY_OF_MONTH, -7);
		return cal;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String convertToSeconds(Date date){
		return format(date,SECONDS_PATTERN);
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String convertToDate(Date date){
		return format(date,DATE_PATTERN);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String convertToHour(Date date){
		return format(date,HOUR_PATTERN);
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String convertToTime(Date date){
		return format(date,TIME_PATTERN);
	}
	
	private static String format(Date date, String pattern){
		String dateFormatted = "";
		if ( date != null ) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			dateFormatted = sdf.format(date);
		} else{
			dateFormatted = "";
		}
		return dateFormatted;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static Date currentTime(){
		Date now = new Date();
		return now;
	}

	/**
	 * @return
	 */
	public static String currentDateAsText(){
		return convertToDate(currentTime());
	}
	
	public static String currentWeekAsText(){
		String weekText = convertToDate(getCurrentWeekDay(1).getTime())+ " -> "+convertToDate(getCurrentWeekDay(5).getTime());
		return weekText;
	}
	
}
