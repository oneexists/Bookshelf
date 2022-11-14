package com.bujo.bookshelf.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bujo.bookshelf.appUser.AppUserService;
import com.bujo.bookshelf.appUser.models.AppUserDTO;
import com.bujo.bookshelf.appUser.models.AppUserDetails;
import com.bujo.bookshelf.response.Result;

@RestController
@ConditionalOnWebApplication
public class AuthenticationController {
	private final AuthenticationManager authenticationManager;
	private final JwtConverter converter;
	private final AppUserService service;
	
	public AuthenticationController(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService service) {
		this.authenticationManager = authenticationManager;
		this.converter = converter;
		this.service = service;
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
				responseBody.put("jwt_token", jwtToken);

				return new ResponseEntity<>(responseBody, HttpStatus.OK);
			}
		} catch (AuthenticationException exception) {
			SecurityContextHolder.clearContext();
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/create_account")
	public ResponseEntity<?> createAccount(@RequestBody AppUserDTO appUserDto) {
		Result<AppUserDetails> result = service.create(appUserDto);
		
		if (!result.isSuccess()) {
			return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
		}
		
		Map<String, Long> responseBody = new HashMap<>();
		responseBody.put("app_user_id", result.getPayload().getAppUserId());
		
		return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@AuthenticationPrincipal AppUserDetails appUser) {
		String jwt = converter.getTokenFromUser(appUser);
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("jwt_token", jwt);
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
}
