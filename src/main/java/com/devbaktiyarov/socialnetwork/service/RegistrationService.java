package com.devbaktiyarov.socialnetwork.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devbaktiyarov.socialnetwork.entity.User;
import com.devbaktiyarov.socialnetwork.repository.UserRepository;

@Service
public class RegistrationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    public List<User> findByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    public void deleteAccount(User user) {
        userRepository.deleteByUsername(user.getUsername());
    }
    
}
