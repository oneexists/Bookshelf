package com.bujo.bookshelf.appUser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bujo.bookshelf.appUser.models.AppUserDTO;
import com.bujo.bookshelf.appUser.models.AppUserDetails;
import com.bujo.bookshelf.response.Result;

public interface AppUserService {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	Result<AppUserDetails> create(AppUserDTO appUserDto);
}
