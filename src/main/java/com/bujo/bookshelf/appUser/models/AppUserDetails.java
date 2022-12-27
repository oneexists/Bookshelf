package com.bujo.bookshelf.appUser.models;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUserDetails implements UserDetails {
	/**
	 * YYYYMMVVV - year, month, version
	 */
	@Serial
	private static final long serialVersionUID = 202211001L;
	private final Set<? extends GrantedAuthority> grantedAuthorities;
	private final Long appUserId;
	private final String username;;
	private final String password;
	private final boolean isAccountNonExpired;
	private final boolean isAccountNonLocked;
	private final boolean isCredentialsNonExpired;
	private final boolean isEnabled;
	
	public AppUserDetails(AppUser appUser) {
		this.appUserId = appUser.getAppUserId();
		this.username = appUser.getUsername();
		this.password = appUser.getPassword();
		this.grantedAuthorities = appUser.getUserRole().getGrantedAuthorities();
		this.isAccountNonExpired = appUser.isAccountNonExpired();
		this.isAccountNonLocked = appUser.isAccountNonLocked();
		this.isCredentialsNonExpired = appUser.isCredentialsNonExpired();
		this.isEnabled = appUser.isEnabled();
	}
	
	public AppUserDetails(Long appUserId, String username, AppUserRole appUserRole) {
		this.appUserId = appUserId;
		this.username = username;
		this.password = null;
		this.grantedAuthorities = appUserRole.getGrantedAuthorities();
		this.isEnabled = true;
		this.isCredentialsNonExpired = true;
		this.isAccountNonLocked = true;
		this.isAccountNonExpired = true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	public Long getAppUserId() {
		return appUserId;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
