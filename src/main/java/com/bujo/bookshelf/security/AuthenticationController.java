package com.bujo.bookshelf.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bujo.bookshelf.appUser.models.AppUserDTO;
import com.bujo.bookshelf.appUser.models.AppUserDetails;

import static com.bujo.bookshelf.security.SecurityConstants.JWT_TOKEN_HEADER;

@RestController
@ConditionalOnWebApplication
public class AuthenticationController {
	private final AuthenticationManager authenticationManager;
	private final JwtConverter converter;

	public AuthenticationController(AuthenticationManager authenticationManager, JwtConverter converter) {
		this.authenticationManager = authenticationManager;
		this.converter = converter;
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<Map<String, String>> authenticate(@RequestBody AppUserDTO appUserDto) {
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(appUserDto.username(), appUserDto.password());
		
		try {
			Authentication authentication = authenticationManager.authenticate(token);
			
			if (authentication.isAuthenticated()) {
				String jwtToken = converter.getTokenFromUser((AppUserDetails) authentication.getPrincipal());
				Map<String, String> responseBody = new HashMap<>();
				responseBody.put(JWT_TOKEN_HEADER, jwtToken);

				return new ResponseEntity<>(responseBody, HttpStatus.OK);
			}
		} catch (AuthenticationException exception) {
			SecurityContextHolder.clearContext();
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		String jwt = converter.refreshToken(token);
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put(JWT_TOKEN_HEADER, jwt);
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
}
