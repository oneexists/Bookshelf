package com.bujo.bookshelf.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bujo.bookshelf.appUser.models.AppUserDetails;

import io.jsonwebtoken.JwtException;

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
		try {
			AppUserDetails appUser = converter.getUserFromToken(request.getHeader("Authorization"));
			if (appUser != null) {
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (JwtException exception) {
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(request, response);
	}
}
