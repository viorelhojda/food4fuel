package com.atraxo.f4f.model.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.DateUtils;
import com.atraxo.f4f.util.MessageBundleUtils;
import com.atraxo.f4f.util.SettingsBundleUtils;


/**
 * This enumeration defines the existing types of process jobs
 * @author vhojda
 *
 */
public enum ProcessJobTypeEnum {
	
	EMAIL( Constants.ENUM_JOB_TYPE_EMAIL, false),
	WEEKLY_EMAIL_REMINDER_COLLECT_MONEY(Constants.ENUM_JOB_WEEKLY_EMAIL_REMINDER_COLLECT_MONEY, true),
	WEEKLY_EMAIL_REMINDER_PAY_MONEY(Constants.ENUM_JOB_WEEKLY_EMAIL_REMINDER_PAY_MONEY, true),
	DAILY_EMAIL_ORDER_FOOD(Constants.ENUM_JOB_DAILY_EMAIL_ORDER_FOOD, true),
	DAILY_EMAIL_REMINDER_PREORDER_FOOD(Constants.ENUM_JOB_DAILY_EMAIL_REMINDER_PREORDER_FOOD, true);
	
	private static List<ProcessJobTypeEnum> activateFailedJobTypesList;

	private String labelMsgKey;
	private boolean activateFailed;
	
	
	/**
	 * @param labelMsgKey
	 * @param activateFailed
	 */
	private ProcessJobTypeEnum(String labelMsgKey, boolean activateFailed) {
		this.labelMsgKey = labelMsgKey;
		this.activateFailed = activateFailed;
	}
	
	public String getLabelMessage() {
		return MessageBundleUtils.getMessage(labelMsgKey);
	}

	public String getName(){
		return this.name();
	}

	public boolean isActivateFailed() {
		return activateFailed;
	}

	public synchronized static List<ProcessJobTypeEnum> getActivateFailedJobTypesList() {
		if ( activateFailedJobTypesList == null ) {
			activateFailedJobTypesList = new ArrayList<ProcessJobTypeEnum>();
			for ( ProcessJobTypeEnum jobType : ProcessJobTypeEnum.values() ) {
				if ( jobType.isActivateFailed() ) {
					activateFailedJobTypesList.add(jobType);
				}
			}
		}
		return activateFailedJobTypesList;
	}

	
	public Calendar getStartDate(){
		Calendar cal = Calendar.getInstance();
		
		if ( this.equals(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_COLLECT_MONEY) ) {
			//Friday 11:00 
			cal = DateUtils.getPreviousWeekDay(5);
			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(SettingsBundleUtils.getSetting(
					SettingsBundleUtils.REMINDER_WEEKLY_COLLECT_HOURS)));
			cal.set(Calendar.MINUTE, Integer.parseInt(SettingsBundleUtils.getSetting(
					SettingsBundleUtils.REMINDER_WEEKLY_COLLECT_MIN)));
		} 
		else if ( this.equals(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_PAY_MONEY) ) {
			//Friday 10:00 
			cal = DateUtils.getPreviousWeekDay(5);
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(SettingsBundleUtils.getSetting(
					SettingsBundleUtils.REMINDER_WEEKLY_PAY_HOURS)));
			cal.set(Calendar.MINUTE, Integer.parseInt(SettingsBundleUtils.getSetting(
					SettingsBundleUtils.REMINDER_WEEKLY_PAY_MIN)));
		}
		else if ( this.equals(ProcessJobTypeEnum.DAILY_EMAIL_ORDER_FOOD) ) {
			//daily 10:00 
			cal = DateUtils.getPreviousDay(
					Integer.parseInt(SettingsBundleUtils.getSetting(SettingsBundleUtils.REMINDER_DAILY_ORDER_HOURS)),
					Integer.parseInt(SettingsBundleUtils.getSetting(SettingsBundleUtils.REMINDER_DAILY_ORDER_MIN)));
		}
		else if ( this.equals(ProcessJobTypeEnum.DAILY_EMAIL_REMINDER_PREORDER_FOOD) ) {
			//daily 09:45 
			cal = DateUtils.getPreviousDay(
					Integer.parseInt(SettingsBundleUtils.getSetting(SettingsBundleUtils.REMINDER_DAILY_PREORDER_HOURS)),
					Integer.parseInt(SettingsBundleUtils.getSetting(SettingsBundleUtils.REMINDER_DAILY_PREORDER_MIN)));
		}
		
		return cal;
	}
	
	public int getNumberOfMinutes(){
		int minutes = 1;
		if ( this.equals(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_COLLECT_MONEY) ) {
			//weekly 
			return 24 * 60 * 7;
		} 
		else if ( this.equals(ProcessJobTypeEnum.WEEKLY_EMAIL_REMINDER_PAY_MONEY) ) {
			//weekly 
			return 24 * 60 * 7;
		}
		else if ( this.equals(ProcessJobTypeEnum.DAILY_EMAIL_ORDER_FOOD) ) {
			//daily 
			return 24 * 60;
		}
		else if ( this.equals(ProcessJobTypeEnum.DAILY_EMAIL_REMINDER_PREORDER_FOOD) ) {
			//daily 
			return 24 * 60;
		}
		return minutes;
	}

	
}
