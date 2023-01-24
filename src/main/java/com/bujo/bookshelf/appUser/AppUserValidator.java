package com.bujo.bookshelf.appUser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bujo.bookshelf.appUser.models.AppUser;

/**
 * Validates {@link AppUser} entities before they are saved. This validator checks that the username and password fields
 * are not empty, that the username is between 3 and 100 characters long, and that the password is at least 8 characters
 * long and contains at least one digit, one letter, and one special character. If the validation fails, the appropriate
 * error code is added to the {@link Errors} object.
 *
 * @author skylar
 */
@Component("beforeSaveAppUserValidator")
public class AppUserValidator implements Validator {
	private static final String USERNAME_FIELD = "username";
	private static final String PASSWORD_FIELD = "password";
	private static final String EMPTY_CODE = ".empty";
	private static final String INVALID_CODE = ".invalid";

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
			errors.rejectValue(USERNAME_FIELD, USERNAME_FIELD + EMPTY_CODE);
			return;
		}

		if (validation.isBlankString(user.getPassword())) {
			errors.rejectValue(PASSWORD_FIELD, PASSWORD_FIELD + EMPTY_CODE);
			return;
		}
		
		if (user.getUsername().length() > 100 || user.getUsername().length() < 3) {
			errors.rejectValue(USERNAME_FIELD, USERNAME_FIELD + INVALID_CODE);
		}
		
		if (!validation.isValidPassword(user.getPassword())) {
			errors.rejectValue(PASSWORD_FIELD, PASSWORD_FIELD + INVALID_CODE);
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}

}
