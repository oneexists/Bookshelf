package com.bujo.bookshelf.appUser;

import com.bujo.bookshelf.appUser.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bujo.bookshelf.appUser.models.AppUserDTO;
import com.bujo.bookshelf.appUser.models.AppUserDetails;
import com.bujo.bookshelf.response.Result;

import java.util.Optional;

public interface AppUserService {
	/**
	 * Loads an {@link AppUser} by its username.
	 *
	 * @param username the username of the {@link AppUser}
	 * @return the {@link UserDetails} of the {@link AppUser}
	 * @throws UsernameNotFoundException if the {@link AppUser} is not found or is not enabled
	 */
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	/**
	 * Creates a new {@link AppUser}.
	 *
	 * @param appUserDto the DTO containing the information for the new {@link AppUser}
	 * @return the result of the create operation, containing the newly created {@link AppUser} if successful
	 */
	Result<AppUserDetails> create(AppUserDTO appUserDto);

	/**
	 * Find an {@link AppUser} by its ID.
	 *
	 * @param appUserId the ID of the {@link AppUser}
	 * @return the {@link Optional} value of the {@link AppUser}
	 */
    Optional<AppUser> findById(Long appUserId);
}
