package com.bujo.bookshelf.appUser.models;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.bujo.bookshelf.appUser.models.AppUserPermission.*;

public enum AppUserRole {
	USER(Set.of(BOOK_READ, BOOK_WRITE)),
	ADMIN(Set.of(USER_READ, USER_WRITE));
	
	private final Set<AppUserPermission> permissions;
	
	AppUserRole(Set<AppUserPermission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<AppUserPermission> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
				.map(p -> new SimpleGrantedAuthority(p.getPermission()))
				.collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;
	}
}
