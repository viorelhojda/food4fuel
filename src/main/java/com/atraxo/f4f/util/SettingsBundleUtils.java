package com.atraxo.f4f.util;

import java.util.ResourceBundle;

public class SettingsBundleUtils {

	private static ResourceBundle properties = ResourceBundle.getBundle(Constants.BUNDLE_SETTINGS);
	
	public static final String CONFIG_PATH = "configsPath";
	
	public static final String JOB_MAX_RETRY_EXECUTE = "job.maxRetryExecute";
	public static final String JOB_DELAY_MIN_EXECUTE = "job.delayMinutesExecute";
	public static final String JOB_DEFAULT_MIN_EXECUTE = "job.defaultMinutesExecute";
	
	public static final String EMAIL_SERVER_USER="email.server.user";
	public static final String EMAIL_SERVER_PASSWORD="email.server.password";
	public static final String EMAIL_SERVER_SENDER="email.server.sender";
	
	public static final String EMAIL_SERVER_SMTP_HOST="email.server.mail.smtp.host";
	public static final String EMAIL_SERVER_SMTP_START_TLS="email.server.mail.smtp.starttls.enable";
	public static final String EMAIL_SERVER_SMTP_AUTH="email.server.mail.smtp.auth";
	public static final String EMAIL_SERVER_SMTP_PORT="email.server.mail.smtp.port";
	
	public static final String REMINDER_WEEKLY_COLLECT_HOURS="reminder.weekly.collect.hour";
	public static final String REMINDER_WEEKLY_COLLECT_MIN="reminder.weekly.collect.minute";
	public static final String REMINDER_WEEKLY_PAY_HOURS="reminder.weekly.pay.hour";
	public static final String REMINDER_WEEKLY_PAY_MIN="reminder.weekly.pay.minute";
	public static final String REMINDER_DAILY_ORDER_HOURS="reminder.daily.order.hour";
	public static final String REMINDER_DAILY_ORDER_MIN="reminder.daily.order.minute";
	public static final String REMINDER_DAILY_PREORDER_HOURS="reminder.daily.preorder.hour";
	public static final String REMINDER_DAILY_PREORDER_MIN="reminder.daily.preorder.minute";
	
	private SettingsBundleUtils(){
		
	}
	
	public static String getSetting(String key) {
		return properties.getString(key);
	}
	
	public static String getSettingConfigDir() {
		return getSetting(CONFIG_PATH);
	}
	
	
	public static Integer getJobMaxRetryExecute() {
		return Integer.parseInt(getSetting(JOB_MAX_RETRY_EXECUTE));
	}
	
	public static Integer getJobDelayMinExecute() {
		return Integer.parseInt(getSetting(JOB_DELAY_MIN_EXECUTE));
	}
	
	public static Integer getJobDefaultMinExecute() {
		return Integer.parseInt(getSetting(JOB_DEFAULT_MIN_EXECUTE));
	}
	

}
