package com.devbaktiyarov.socialnetwork.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devbaktiyarov.socialnetwork.dto.JWTProvider;
import com.devbaktiyarov.socialnetwork.dto.UserDTO;
import com.devbaktiyarov.socialnetwork.entity.User;
import com.devbaktiyarov.socialnetwork.service.RegistrationService;
import com.devbaktiyarov.socialnetwork.validation.UserValidator;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;
    private final JWTProvider jwtProvider;

   
    public AuthController(RegistrationService registrationService, UserValidator userValidator,
            ModelMapper modelMapper, JWTProvider jwtProvider) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
        this.modelMapper = modelMapper;
        this.jwtProvider = jwtProvider;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{username}")
    public ResponseEntity<List<UserDTO>> findByUsername(@PathVariable String username) {
        List<User> sourceUsers = registrationService.findByUsername(username);
        
        List<UserDTO> outcomeUsers = sourceUsers.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.FOUND).body(outcomeUsers);
    }

    @PostMapping("/")
    public ResponseEntity<String> registerUser(@RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");

        registrationService.register(user);

        String token = jwtProvider.generateToken(user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/")
    public ResponseEntity<String> deleteAccount(@RequestBody User user) {
        registrationService.deleteAccount(user);
        return ResponseEntity.status(HttpStatus.OK).body("Your account successfully deleted");
    }
}
