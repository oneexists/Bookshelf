package com.bujo.bookshelf.appUser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bujo.bookshelf.appUser.models.AppUser;

@Component("beforeSaveAppUserValidator")
public class AppUserValidator implements Validator {
	private final PasswordEncoder passwordEncoder;
	private final AppUserValidation validation;
	
	public AppUserValidator(PasswordEncoder passwordEncoder, AppUserValidation validation) {
		this.passwordEncoder = passwordEncoder;
		this.validation = validation;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AppUser.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AppUser user = (AppUser) target;
		
		if (validation.isBlankString(user.getUsername())) {
			errors.rejectValue("username", "username.empty");
		}
		
		if (validation.isBlankString(user.getPassword())) {
			errors.rejectValue("password", "password.empty");
		}
		
		if (user.getUsername().length() > 2000) {
			errors.rejectValue("username", "username.invalid");
		}
		
		if (!validation.isValidPassword(user.getPassword())) {
			errors.rejectValue("password", "password.invalid");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}

}
