package com.himanshu.authenticationservice.controller;

import com.himanshu.authenticationservice.entity.AuthenticationResponse;
import com.himanshu.authenticationservice.entity.Users;
import com.himanshu.authenticationservice.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Users request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody Users request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}

