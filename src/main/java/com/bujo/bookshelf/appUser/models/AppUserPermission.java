package com.bujo.bookshelf.appUser.models;

public enum AppUserPermission {
	USER_READ("user:read"),
	USER_WRITE("user:write"),
	AUTHOR_WRITE("author:write"),
	AUTHOR_READ("author:read"),
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
