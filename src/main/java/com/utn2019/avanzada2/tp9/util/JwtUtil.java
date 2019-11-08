package com.utn2019.avanzada2.tp9.util;

import static com.utn2019.avanzada2.tp9.util.SecurityConstants.EXPIRATION_TIME_MILLIS;
import static com.utn2019.avanzada2.tp9.util.SecurityConstants.SECRET;

import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class JwtUtil {
    public static String buildJwt(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
                .signWith(SECRET)
                .compact();
    }
}
