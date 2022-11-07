package com.bujo.bookshelf.appUser.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "app_user")
public class AppUser implements Serializable {
	/**
	 * YYYYMMVVV - year, month, version
	 */
	private static final long serialVersionUID = 202211001L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long appUserId;
	@Column(unique = true)
	private String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private AppUserRole userRole;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;

	public AppUser() {
	}

	public AppUser(String username, String password, AppUserRole userRole) {
		this.username = username;
		this.password = password;
		this.userRole = userRole;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.isEnabled = true;
	}

	public Long getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AppUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(AppUserRole userRole) {
		this.userRole = userRole;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(appUserId, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled,
				password, userRole, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUser other = (AppUser) obj;
		return Objects.equals(appUserId, other.appUserId) && isAccountNonExpired == other.isAccountNonExpired
				&& isAccountNonLocked == other.isAccountNonLocked
				&& isCredentialsNonExpired == other.isCredentialsNonExpired && isEnabled == other.isEnabled
				&& Objects.equals(password, other.password) && userRole == other.userRole
				&& Objects.equals(username, other.username);
	}
	
	
}
