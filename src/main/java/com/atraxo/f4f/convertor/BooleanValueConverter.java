package com.atraxo.f4f.convertor;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;

@FacesConverter(value="booleanValueConverter")
public class BooleanValueConverter implements Converter,Serializable {
	private static final long serialVersionUID = -8263437925284373709L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value){
		if ( value != null ){
			if ( value.equals(MessageBundleUtils.getMessage(Constants.MSG_YES))) {
				return Boolean.TRUE;
			}
			else if ( value.equals(MessageBundleUtils.getMessage(Constants.MSG_NO))) {
				return Boolean.FALSE;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value){
		String retValue = "";
		if ( value != null && value instanceof Boolean){
			Boolean boolvalue = (Boolean)value;
			if ( boolvalue ){
				retValue = MessageBundleUtils.getMessage(Constants.MSG_YES);
			}
			else{
				retValue = MessageBundleUtils.getMessage(Constants.MSG_NO);
			}
		}
		return retValue;
	}
	
}
