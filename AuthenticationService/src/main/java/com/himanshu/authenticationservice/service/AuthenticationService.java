package com.himanshu.authenticationservice.service;

import com.himanshu.authenticationservice.entity.AuthenticationResponse;
import com.himanshu.authenticationservice.entity.Users;
import com.himanshu.authenticationservice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Service

public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(Users request){
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole(request.getRole());
        user = userRepository.save(user);
        String token = jwtService.generate_Token(user);
        return new AuthenticationResponse(token);
    }
    //Check what is happening here
    public AuthenticationResponse authenticate(Users request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            Users user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtService.generate_Token(user);
            return new AuthenticationResponse(token);
        } else {
            throw new RuntimeException("invalid access");
        }
    }



}