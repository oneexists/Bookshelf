package com.bujo.bookshelf.security;

import static com.bujo.bookshelf.security.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtRequestFilter extends BasicAuthenticationFilter {
	private final JwtConverter converter;
	
	public JwtRequestFilter(AuthenticationManager authenticationManager, JwtConverter converter) {
		super(authenticationManager);
		this.converter = converter;
	}	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
			response.setStatus(OK.value());
		} else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
				setSecurityContext(authorizationHeader, request);
			}
		}
		filterChain.doFilter(request, response);
	}

	private void setSecurityContext(String authorizationHeader, HttpServletRequest request) {
		String username = converter.getSubject(authorizationHeader);
		Authentication authentication = getAuthentication(username, authorizationHeader, request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private Authentication getAuthentication(String username, String token, HttpServletRequest request) {
		Set<GrantedAuthority> authorities = converter.getAuthorities(token);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(username, null, authorities);
		return converter.getAuthentication(usernamePasswordAuthenticationToken, request);
	}
}
