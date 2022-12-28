package com.bujo.bookshelf.appUser.models;

import org.springframework.security.core.userdetails.User;

public class AppUserDetails extends User {
	private final Long appUserId;

	public AppUserDetails(AppUser appUser) {
		super(appUser.getUsername(),
				appUser.getPassword(),
				appUser.isEnabled(),
				appUser.isAccountNonExpired(),
				appUser.isCredentialsNonExpired(),
				appUser.isAccountNonLocked(),
				appUser.getUserRole().getGrantedAuthorities());
		this.appUserId = appUser.getAppUserId();
	}

	public Long getAppUserId() {
		return appUserId;
	}
}
