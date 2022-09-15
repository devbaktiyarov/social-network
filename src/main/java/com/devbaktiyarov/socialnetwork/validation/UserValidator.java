package com.devbaktiyarov.socialnetwork.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.devbaktiyarov.socialnetwork.entity.User;
import com.devbaktiyarov.socialnetwork.repository.UserRepository;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .isPresent()) {
            errors.rejectValue("username", "User with this username already exists");
            errors.rejectValue("email", "User with this email already exists");
        }
    }
}