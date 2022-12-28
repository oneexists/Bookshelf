package com.bujo.bookshelf.security;

public class SecurityConstants {
    private static final int EXPIRATION_MINUTES = 15;
    public static final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to view this page";
    public static final String APP_USER_ID_KEY = "app_user_id";
    public static final String AUTHORITIES = "authorities";
    public static final String DELIMITER = ",";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ISSUER = "bujo-bookshelf";
    public static final String JWT_TOKEN_HEADER = "jwt_token";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

    public static final String[] PUBLIC_URLS = { "/api/appUsers", "/authenticate" };
}
