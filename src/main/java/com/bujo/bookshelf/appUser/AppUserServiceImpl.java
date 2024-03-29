package com.bujo.bookshelf.appUser;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.appUser.models.AppUserDTO;
import com.bujo.bookshelf.appUser.models.AppUserDetails;
import com.bujo.bookshelf.appUser.models.AppUserRole;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;

import java.util.Optional;

/**
 * AppUserServiceImpl is a service class that provides methods to create and load {@link AppUser} objects.
 *
 * @author skylar
 */
@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
	private final AppUserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final AppUserValidation validation;
	
	public AppUserServiceImpl(AppUserRepository repository, PasswordEncoder passwordEncoder, AppUserValidation validation) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.validation = validation;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = repository.findByUsername(username).orElse(null);
		
		if (appUser == null || !appUser.isEnabled()) {
			throw new UsernameNotFoundException(username + " not found");
		}
		
		return new AppUserDetails(appUser);
	}

	@Override
	public Result<AppUserDetails> create(AppUserDTO appUserDto) {
		Result<AppUserDetails> result = validation.validate(appUserDto.username(), appUserDto.password());
		if (!result.isSuccess()) {
			return result;
		}
		
		String password = passwordEncoder.encode(appUserDto.password());
		AppUser appUser = new AppUser(appUserDto.username(), password, AppUserRole.USER);
		
		try {
			appUser = repository.save(appUser);
			result.setPayload(new AppUserDetails(appUser));
		} catch (DataIntegrityViolationException exception) {
			result.addMessage(ActionStatus.INVALID, "the provided username already exists");
		}
		
		return result;
	}

	@Override
	public Optional<AppUser> findById(Long appUserId) {
		return repository.findById(appUserId);
	}
}
