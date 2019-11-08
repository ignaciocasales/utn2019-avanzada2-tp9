package com.utn2019.avanzada2.tp9.util;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.security.Keys.secretKeyFor;

import java.security.Key;

public class SecurityConstants {
    public static final long EXPIRATION_TIME_MILLIS = 3_600_000; // 1 hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final Key SECRET = secretKeyFor(HS256);
}