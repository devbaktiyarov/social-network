package com.devbaktiyarov.socialnetwork.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devbaktiyarov.socialnetwork.entity.User;
import com.devbaktiyarov.socialnetwork.repository.UserRepository;
import com.devbaktiyarov.socialnetwork.security.AuthDetails;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new AuthDetails(user.get());
    }
    
}
