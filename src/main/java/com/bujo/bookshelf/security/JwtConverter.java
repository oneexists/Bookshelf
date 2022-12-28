package com.bujo.bookshelf.security;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.bujo.bookshelf.appUser.models.AppUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;

import static com.bujo.bookshelf.security.SecurityConstants.*;

@Component
public class JwtConverter {
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String getTokenFromUser(AppUserDetails user) {
		String authorities = user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(DELIMITER));
		return Jwts.builder()
				.setIssuer(ISSUER)
				.setSubject(user.getUsername())
				.claim(APP_USER_ID_KEY, user.getAppUserId())
				.claim(AUTHORITIES, authorities)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
				.signWith(key)
				.compact();
	}

	private Jws<Claims> getClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.requireIssuer(ISSUER)
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token.substring(TOKEN_PREFIX.length()));
		} catch (JwtException exception) {
			SecurityContextHolder.clearContext();
			throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
		}
	}

	public String getSubject(String token) {
		return getClaims(token).getBody().getSubject();
	}

	public Long getAppUserIdClaimFromToken(String token) {
		return Long.parseLong(getClaims(token).getBody().get(APP_USER_ID_KEY).toString());
	}

	public String refreshToken(String token) {
		if (token == null || !token.startsWith(TOKEN_PREFIX)) {
			return null;
		}
		
		Jws<Claims> jws = getClaims(token);
		String username = jws.getBody().getSubject();
		Long appUserId = Long.parseLong(jws.getBody().get(APP_USER_ID_KEY).toString());
		String authString = String.valueOf(jws.getBody().get(AUTHORITIES));

		return Jwts.builder()
				.setIssuer(ISSUER)
				.setSubject(username)
				.claim(APP_USER_ID_KEY, appUserId)
				.claim(AUTHORITIES, authString)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
				.signWith(key)
				.compact();
	}

	public Set<GrantedAuthority> getAuthorities(String token) {
		Jws<Claims> jws = getClaims(token);
		String authString = String.valueOf(jws.getBody().get(AUTHORITIES));
		return Arrays.stream(authString.split(DELIMITER))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	public Authentication getAuthentication(UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return authentication;
	}
}
