/**
 * 
 */
package com.atraxo.f4f.convertor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.atraxo.f4f.facade.RoleFacade;
import com.atraxo.f4f.model.permission.Role;

@FacesConverter(forClass=Role.class,value="roleConverter")
public class RoleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if ( value != null && StringUtils.isNumeric(value) ) {
			RoleFacade roleFacade = new RoleFacade();
			int id = Integer.parseInt(value);
			Role role = roleFacade.findById(id);
			return role;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if ( value != null ){ 
			Role role = (Role)value;
			return role.getId()+"";
		}
		return null;
	}

}
