package com.utn2019.avanzada2.tp9.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.utn2019.avanzada2.tp9.domain.User;
import com.utn2019.avanzada2.tp9.dto.AuthMessage;
import com.utn2019.avanzada2.tp9.service.UsersService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @PostMapping(value = "/login")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Bad username and/or password")
    })
    public AuthMessage login(@RequestBody User user) {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }

    @PostMapping(path = "/sign-up", consumes = APPLICATION_JSON_VALUE)
    public void signUp(@RequestBody User user) {
        usersService.save(user);
    }

    @GetMapping(path = "/users/identities")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The user does not exists"),
            @ApiResponse(code = 409, message = "The user already exists")
    })
    public ResponseEntity<Object> identities(@RequestParam(name = "email", required = false) String email) {
        return usersService.existsByEmail(email) ? ResponseEntity.status(CONFLICT).build() : ResponseEntity.noContent().build();
    }
}
