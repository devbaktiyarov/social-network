package com.devbaktiyarov.socialnetwork.controller;

import com.devbaktiyarov.socialnetwork.dto.AuthenticationDTO;
import com.devbaktiyarov.socialnetwork.security.AuthDetails;
import com.devbaktiyarov.socialnetwork.security.jwt.JWTResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.devbaktiyarov.socialnetwork.security.jwt.JWTProvider;
import com.devbaktiyarov.socialnetwork.service.RegistrationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    private final JWTProvider jwtProvider;

   
    public AuthController(RegistrationService registrationService, JWTProvider jwtProvider) {
        this.registrationService = registrationService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody AuthenticationDTO user) {
        String token = jwtProvider.generateToken(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new JWTResponse(token));
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(Authentication authentication) {
        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
        registrationService.deleteAccount(authDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("Your account successfully deleted");
    }
}
