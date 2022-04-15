package com.rdtj.redditjbe.constants;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String RAFALE = "Rafale, a GFA class";
    public static final String ANYBODY = "Anybody";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page!";
    public static final String ACCESS_DENIED_MESSAGE = "You don't have the access to this page!";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/api/v1/auth/log-in", "/api/v1/auth/register"};
//    public static final String[] PUBLIC_URLS = {"**"};

}
