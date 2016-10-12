package com.atraxo.f4f.model.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserPreferences{

	@Basic
	@Column(name="BOO_REMIND_PREORDER")
	private Boolean remindPreOrder;
	
	@Basic
	@Column(name="BOO_REMIND_PAY")
	private Boolean remindPayUp;
	
	@Basic
	@Column(name="BOO_REMIND_COLLECT")
	private Boolean remindCollect;
	
	/**
	 * 
	 */
	public UserPreferences(){
		remindPreOrder = Boolean.TRUE;
		remindPayUp = Boolean.TRUE;
		remindCollect = Boolean.TRUE;
	}

	public Boolean getRemindPreOrder() {
		return remindPreOrder;
	}

	public void setRemindPreOrder(Boolean remindPreOrder) {
		this.remindPreOrder = remindPreOrder;
	}

	public Boolean getRemindPayUp() {
		return remindPayUp;
	}

	public void setRemindPayUp(Boolean remindPayUp) {
		this.remindPayUp = remindPayUp;
	}

	public Boolean getRemindCollect() {
		return remindCollect;
	}

	public void setRemindCollect(Boolean remindCollect) {
		this.remindCollect = remindCollect;
	}
	
	
}
