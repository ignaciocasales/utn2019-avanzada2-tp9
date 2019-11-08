package com.utn2019.avanzada2.tp9.config.auth;

import static com.utn2019.avanzada2.tp9.util.JwtUtil.buildJwt;
import static com.utn2019.avanzada2.tp9.util.SecurityConstants.HEADER_STRING;
import static com.utn2019.avanzada2.tp9.util.SecurityConstants.SECRET;
import static com.utn2019.avanzada2.tp9.util.SecurityConstants.TOKEN_PREFIX;
import static java.time.ZoneId.systemDefault;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class JWTRefreshFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        ofNullable(req)
                .map(r -> r.getHeader(HEADER_STRING))
                .filter(header -> header.startsWith(TOKEN_PREFIX))
                .ifPresent(token -> {
                    try {
                        final Claims claims = Jwts.parser()
                                .setSigningKey(SECRET)
                                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                                .getBody();
                        final LocalDateTime expiration = claims.getExpiration()
                                .toInstant()
                                .atZone(systemDefault())
                                .toLocalDateTime();
                        if (Instant.now().atZone(systemDefault()).toLocalDateTime().isAfter(expiration.minus(59, MINUTES))) {
                            final String jwt = buildJwt(claims.getSubject());
                            res.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
                            res.setContentType(APPLICATION_JSON_VALUE);
                        }
                    } catch (Exception ignored) {
                    }
                });
        chain.doFilter(req, res);
    }
}
