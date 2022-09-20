package com.devbaktiyarov.socialnetwork.controller;

import com.devbaktiyarov.socialnetwork.entity.User;
import com.devbaktiyarov.socialnetwork.service.RegistrationService;
import com.devbaktiyarov.socialnetwork.validation.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {


    private final RegistrationService registrationService;
    private final UserValidator userValidator;

    public RegistrationController(RegistrationService registrationService, UserValidator userValidator) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }

    @PostMapping("/")
    public ResponseEntity<String> registerUser(@RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");

        registrationService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }
}
