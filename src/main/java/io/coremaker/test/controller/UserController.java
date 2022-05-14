package io.coremaker.test.controller;

import io.coremaker.test.domain.request.LoginRequest;
import io.coremaker.test.domain.request.UserCreateRequest;
import io.coremaker.test.domain.response.CustomToken;
import io.coremaker.test.filter.JwtProvider;
import io.coremaker.test.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtProvider jwtProvider, AuthenticationManager manager) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = manager;
    }

    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        ResponseEntity.ok(new CustomToken(jwtProvider.generateToken(authentication)));
        return ResponseEntity.ok(jwtProvider.generateToken(authentication));
    }
}
