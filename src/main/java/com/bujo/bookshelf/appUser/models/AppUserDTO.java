package com.bujo.bookshelf.appUser.models;

public class AppUserDTO {
	private final String username;
	private final String password;
	
	public AppUserDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	
}
