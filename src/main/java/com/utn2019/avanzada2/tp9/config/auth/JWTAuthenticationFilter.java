package com.utn2019.avanzada2.tp9.config.auth;

import static com.utn2019.avanzada2.tp9.util.JwtUtil.buildJwt;
import static com.utn2019.avanzada2.tp9.util.SecurityConstants.HEADER_STRING;
import static com.utn2019.avanzada2.tp9.util.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn2019.avanzada2.tp9.domain.User;
import com.utn2019.avanzada2.tp9.dto.AuthMessage;
import com.utn2019.avanzada2.tp9.dto.Message;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setAuthenticationFailureHandler((req, res, e) -> {
            res.setContentType(APPLICATION_JSON_VALUE);
            res.setStatus(UNAUTHORIZED.value());
            res.getWriter().write(new ObjectMapper().writeValueAsString(Message.builder().message(e.getMessage()).build()));
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            final User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        final String jwt = buildJwt(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        res.setContentType(APPLICATION_JSON_VALUE);
        res.getWriter().write(new ObjectMapper().writeValueAsString(AuthMessage.builder().jwt(jwt).build()));
    }
}
