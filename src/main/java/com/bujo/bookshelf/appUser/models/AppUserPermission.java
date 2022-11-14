package com.bujo.bookshelf.appUser.models;

public enum AppUserPermission {
	USER_READ("user:read"),
	USER_WRITE("user:write"),
	BOOK_READ("book:read"),
	BOOK_WRITE("book:write");
	
	private final String permission;
	
	AppUserPermission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
}
