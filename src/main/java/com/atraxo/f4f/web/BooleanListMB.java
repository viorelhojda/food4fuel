package com.atraxo.f4f.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.atraxo.f4f.convertor.BooleanValueConverter;

@SessionScoped
@ManagedBean(name="booleanListMB")
public class BooleanListMB implements Serializable {

	private static final long serialVersionUID = 8847469160903724513L;
	
	private transient List<Object> booleanList;
	private transient BooleanValueConverter converter;
	private Boolean selectedValue;
	
	public BooleanListMB(){
		converter = new BooleanValueConverter();
		booleanList = new ArrayList<>();
		
		booleanList.add(Boolean.TRUE);
		booleanList.add(Boolean.FALSE);
		
		setSelectedValue(null);
	}
	
	public Boolean getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Boolean selectedValue) {
		this.selectedValue = selectedValue;
	}

	public List<Object> getBooleanList(){
		return booleanList;
	}
	
	public String getBooleanPositiveValue(){
		return converter.getAsString(null, null, Boolean.TRUE);
	}
	
	public String getBooleanNegativeValue(){
		return converter.getAsString(null, null, Boolean.FALSE);
	}

	public String getBooleanString(Object value) {
		return converter.getAsString(null, null, value);
	}
	
}
