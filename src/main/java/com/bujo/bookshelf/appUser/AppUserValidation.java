package com.bujo.bookshelf.appUser;

import org.springframework.stereotype.Component;

import com.bujo.bookshelf.appUser.models.AppUserDetails;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;

@Component
public class AppUserValidation {
	public Result<AppUserDetails> validate(String username, String password) {
		Result<AppUserDetails> result = new Result<>();
		
		if (isBlankString(username)) {
			result.addMessage(ActionStatus.INVALID, "username is required");
		}
		
		if (isBlankString(password)) {
			result.addMessage(ActionStatus.INVALID, "password is required");
		}
		
		if (!result.isSuccess()) {
			return result;
		}
		
		if (username.length() > 2000 || username.length() < 3) {
			result.addMessage(ActionStatus.INVALID, "invalid username length");
		}
		
		if (!isValidPassword(password)) {
			result.addMessage(ActionStatus.INVALID, "password must be at least 8 characters and contain a digit, "
					+ "a letter, and a special character");
		}
		
		return result;
	}
	
	public boolean isBlankString(String string ) {
		return string == null || string.isBlank();
	}
	
	public boolean isValidPassword(String password) {
		if (password.length() < 8) {
			return false;
		}
		
		int digits = 0;
		int letters = 0;
		int others = 0;
		
		for (char c : password.toCharArray()) {
			if (Character.isDigit(c)) {
				digits++;
			} else if (Character.isLetter(c)) {
				letters++;
			} else {
				others++;
			}
		}
		return (digits > 0) & (letters > 0) && (others > 0);
	}
}
