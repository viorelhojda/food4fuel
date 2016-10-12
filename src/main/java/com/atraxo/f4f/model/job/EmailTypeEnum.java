package com.atraxo.f4f.model.job;

/**
 * This enumeration defines the existing types of emails
 * @author vhojda
 *
 */
public enum EmailTypeEnum {
	
	WELCOME( "WelcomeDefault"),
	ORDER_FOOD("OrderFood"),
	COLLECT_MONEY("CollectMoney"),
	PAY_MONEY("PayMoney"),
	PRE_ORDER_FOOD("PreOrderFood"),
	ERROR("ERROR");
	
	private String type;
	
	/**
	 * @param labelMsgKey
	 * @param activateFailed
	 */
	private EmailTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}



	
}
