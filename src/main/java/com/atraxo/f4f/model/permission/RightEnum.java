package com.atraxo.f4f.model.permission;

import com.atraxo.f4f.util.CodeConstants;
import com.atraxo.f4f.util.WebPage;


public enum RightEnum {
	JOB_VIEW(CodeConstants.CSS_STYLE_PERMISSION_VIEW, null, WebPage.PAGE_JOBS),
	JOB_UPDATE(CodeConstants.CSS_STYLE_PERMISSION_UPDATE, CodeConstants.CSS_STYLE_PERMISSION_MARGIN_LEFT, null),
	USER_VIEW(CodeConstants.CSS_STYLE_PERMISSION_VIEW, null, WebPage.PAGE_USERS),
	USER_UPDATE(CodeConstants.CSS_STYLE_PERMISSION_UPDATE, CodeConstants.CSS_STYLE_PERMISSION_MARGIN_LEFT, null),
	USER_CREATE(CodeConstants.CSS_STYLE_PERMISSION_CREATE, CodeConstants.CSS_STYLE_PERMISSION_MARGIN_LEFT, null),
	ROLE_VIEW(CodeConstants.CSS_STYLE_PERMISSION_VIEW, null, WebPage.PAGE_ROLES),
	ROLE_UPDATE(CodeConstants.CSS_STYLE_PERMISSION_UPDATE, CodeConstants.CSS_STYLE_PERMISSION_MARGIN_LEFT, null),
	ROLE_CREATE(CodeConstants.CSS_STYLE_PERMISSION_CREATE, CodeConstants.CSS_STYLE_PERMISSION_MARGIN_LEFT, null),
	MENU_SETUP(CodeConstants.CSS_STYLE_PERMISSION_VIEW, null, WebPage.PAGE_MENU_SETUP),
	ORDER_FOOD(CodeConstants.CSS_STYLE_PERMISSION_VIEW, null, WebPage.PAGE_ORDER_FOOD),
	COLLECT_MONEY(CodeConstants.CSS_STYLE_PERMISSION_VIEW, null, WebPage.PAGE_COLLECT_MONEY);
	
	private String cssColorStyle;
	private String cssMarginStyle;
	private String webPage;
	
	private RightEnum(String cssColorStyle, String cssMarginStyle, String webPage) {
		this.cssColorStyle = cssColorStyle;
		this.cssMarginStyle = cssMarginStyle;
		this.webPage = webPage;
	}
	
	public String getCssColorStyle() {
		return cssColorStyle;
	}

	public String getCssMarginStyle() {
		return cssMarginStyle;
	}
	
	public String getCssStyle(){
		return cssMarginStyle + cssColorStyle;
	}

	public String getWebPage() {
		return webPage;
	}
	
}
