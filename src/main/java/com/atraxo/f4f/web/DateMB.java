package com.atraxo.f4f.web;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.atraxo.f4f.util.MessageBundleUtils;
import com.atraxo.f4f.util.UserUtils;

@ManagedBean
@ApplicationScoped
public class DateMB implements Serializable{
	
	public static final String HUMAN_PATTERN = "EEE MMM yy HH:mm";
	public static final String DATE_PATTERN = "dd-MM-yyyy";
	public static final String SHORT_DATE_PATTERN = "MMM - yy";
	public static final String HOURS_PATTERN = "HH:mm";
	public static final String MINUTES_PATTERN = "dd-MM-yyyy HH:mm";
	public static final String SECONDS_PATTERN = "dd-MM-yyyy HH:mm:ss";
	public static final String TIME24HOURS_PATTERN = "^(([0-9])|([0-1][0-9])|([2][0-3])):([0-5][0-9])$";

	public static final String CALENDAR_PATTERN = "EEE MMM dd hh:ss:SS zzzz yyyy";
	
	private static final long serialVersionUID = 3369279012067668761L;

	public DateMB() {
		super();
	}

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}
	
	public String getHumanPattern(){
		return HUMAN_PATTERN;
	}

	public String getDatePattern(){
		return DATE_PATTERN;
	}

	public String getHourPattern(){
		return HOURS_PATTERN;
	}
	
	public String getMinutesPattern(){
		return MINUTES_PATTERN;
	}

	public String getSecondsPattern(){
		return SECONDS_PATTERN;
	}
	
	public static String convertToTime(Date date){
		return format(date,MINUTES_PATTERN);
	}

	public static String convertToDate(Date date){
		return format(date,DATE_PATTERN);
	}
	
	public static String convertToShortDate(Date date){
		return format(date,SHORT_DATE_PATTERN);
	}
	
	public static Date convertToDate(String sDate){
		Date dDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		try {
			dDate = sdf.parse(sDate);
		} catch (ParseException e) {
			// do nothing
		}
		
		return dDate;
	}

	private static String format(Date date, String pattern){
		String dateFormatted = MessageBundleUtils.getNAMessage();
		if ( date != null ) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.US);
			dateFormatted = sdf.format(date);
		}
		return dateFormatted;
	}

	public static String convertToHour(Date date){
		return format(date,HOURS_PATTERN);
	}

	public static Date currentTime(){
		return new Date();
	}
	
	public static Date currentClientTime(){
		Date now = new Date();
		return getClientDate(now);
	}

	public static Date getPreviousDayForDate(Date date, int nrDays){
		Date prevDate = date;
		if ( date != null ) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, - nrDays);
			prevDate = cal.getTime();
		}
		return prevDate; 
	}
	
	public static Date getClientDate(Date date){
		if( date == null ){
			return null;
		}
		
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int serverOffset = TimeZone.getDefault().getOffset(today.getTime()) / 1000 / 60;
		int clientOffset = Integer.parseInt(UserUtils.getClientTimeOffset())*(-1);
		
		calendar.add(Calendar.MINUTE, clientOffset - serverOffset);
		
		return calendar.getTime();
	}
	
	public static Date getServerDate(Date date){
		if( date == null ){
			return null;
		}
		
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int serverOffset = TimeZone.getDefault().getOffset(today.getTime()) / 1000 / 60;
		int clientOffset = Integer.parseInt(UserUtils.getClientTimeOffset())*(-1);
		
		calendar.add(Calendar.MINUTE, serverOffset - clientOffset);
		
		return calendar.getTime();
	}
	
}
