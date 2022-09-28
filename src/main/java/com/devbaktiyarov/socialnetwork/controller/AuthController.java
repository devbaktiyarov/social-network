package com.devbaktiyarov.socialnetwork.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devbaktiyarov.socialnetwork.security.jwt.domain.JWTRequest;
import com.devbaktiyarov.socialnetwork.security.jwt.domain.JWTResponse;
import com.devbaktiyarov.socialnetwork.security.jwt.domain.RefreshJWTRequest;
import com.devbaktiyarov.socialnetwork.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest jwtRequest) {
        return ResponseEntity.ok(authService.login(jwtRequest));
    }

    @PostMapping("/access")
    public ResponseEntity<JWTResponse> getNewAccessToken(@RequestBody RefreshJWTRequest jwtRequest) {
        return ResponseEntity.ok(authService.getAccessToken(jwtRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTResponse> getNewRefreshToken(@RequestBody RefreshJWTRequest jwtRequest) {
        return ResponseEntity.ok(authService.getRefreshToken(jwtRequest));
    }

}
