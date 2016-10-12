/**
 * 
 */
package com.atraxo.f4f.convertor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.atraxo.f4f.facade.AccountFacade;
import com.atraxo.f4f.model.account.Account;

@FacesConverter(forClass=Account.class,value="accountConverter")
public class AccountConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value){
		if ( value != null && StringUtils.isNumeric(value) ) {
			AccountFacade accountFacade = new AccountFacade();
			int id = Integer.parseInt(value);
			return accountFacade.findById(id);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value){
		if ( value != null ){
			Account account = (Account)value;
			return Integer.toString(account.getId());
		}
		return null;
	}

}
