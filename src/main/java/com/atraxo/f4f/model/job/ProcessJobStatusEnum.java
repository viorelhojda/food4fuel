package com.atraxo.f4f.model.job;

import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;

/**
 * This enumeration defines the existing statuses of a job process
 * @author vhojda
 *
 */
public enum ProcessJobStatusEnum {
	
	ACTIVE( Constants.ENUM_JOB_STATUS_ACTIVE, Constants.CSS_STYLE_COLOR_NEW), 
	SUSPENDED( Constants.ENUM_JOB_STATUS_SUSPENDED, Constants.CSS_STYLE_COLOR_PENDING), 
	PAUSED( Constants.ENUM_JOB_STATUS_PAUSED, Constants.CSS_STYLE_COLOR_ASSIGNED),
	EXPIRED( Constants.ENUM_JOB_STATUS_EXPIRED, Constants.CSS_STYLE_COLOR_PROCESSED),
	EXECUTED( Constants.ENUM_JOB_STATUS_EXECUTED, Constants.CSS_STYLE_COLOR_CLOSED),
	FAILED( Constants.ENUM_JOB_STATUS_FAILED, Constants.CSS_STYLE_COLOR_FAILED);
	
	private String labelMsgKey;
	private String cssColorStyle;
	

	private ProcessJobStatusEnum(String labelMsgKey, String cssColorStyle) {
		this.labelMsgKey = labelMsgKey;
		this.cssColorStyle = cssColorStyle;
	}
	
	public String getLabelMessage() {
		return MessageBundleUtils.getMessage(labelMsgKey);
	}
	
	public String getCssColorStyle() {
		return cssColorStyle;
	}
	
	public boolean canBePaused(){
		return this.equals(ACTIVE);
	}
	
	public boolean canBeResumed(){
		return this.equals(PAUSED);
	}
	
	public boolean canBeRescheduled(){
		return this.equals(ACTIVE) || this.equals(EXPIRED);
	}
	
	public boolean canBeSuspended(){
		return this.equals(ACTIVE);
	}
	
}
