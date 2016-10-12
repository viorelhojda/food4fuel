package com.atraxo.f4f.web.validator;

import java.io.UnsupportedEncodingException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.atraxo.f4f.model.user.User;
import com.atraxo.f4f.util.Constants;
import com.atraxo.f4f.util.MessageBundleUtils;
import com.atraxo.f4f.util.UserUtils;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value){
		try {
			String label = (String) component.getAttributes().get("label");

			String passwordFromApp = UserUtils.encryptMD5Password(value.toString());

			User user = UserUtils.getCurrentLoggedUser();

			if (!user.getAccount().getPassword().equals(passwordFromApp)){
				FacesMessage msg = new FacesMessage(label+"::"+MessageBundleUtils.getMessage(Constants.MSG_PASSWORD_NOT_OK));
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(e.getLocalizedMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}


	}

}
