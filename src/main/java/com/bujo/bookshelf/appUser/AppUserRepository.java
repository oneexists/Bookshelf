package com.bujo.bookshelf.appUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bujo.bookshelf.appUser.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	Optional<AppUser> findByUsername(String username);
}
