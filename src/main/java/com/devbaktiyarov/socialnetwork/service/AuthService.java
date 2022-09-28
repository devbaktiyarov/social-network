package com.devbaktiyarov.socialnetwork.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devbaktiyarov.socialnetwork.entity.User;
import com.devbaktiyarov.socialnetwork.exception.AuthException;
import com.devbaktiyarov.socialnetwork.repository.UserRepository;
import com.devbaktiyarov.socialnetwork.security.jwt.JWTProvider;
import com.devbaktiyarov.socialnetwork.security.jwt.domain.JWTRequest;
import com.devbaktiyarov.socialnetwork.security.jwt.domain.JWTResponse;
import com.devbaktiyarov.socialnetwork.security.jwt.domain.RefreshJWTRequest;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository,
                       JWTProvider jwtProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }


    public JWTResponse login(JWTRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if (user.isPresent()) {
            if (passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                String accessToken = jwtProvider.generateAccessToken(user.get().getUsername());
                String refreshToken = jwtProvider.generateRefreshToken(user.get().getUsername());
                return new JWTResponse(accessToken, refreshToken);
            } else {
                throw new AuthException("Wrong username or password");
            }
        }
        return new JWTResponse(null, null);
    }


    public JWTResponse getAccessToken(RefreshJWTRequest refreshRequest) {
        String username = jwtProvider.validateRefreshToken(refreshRequest.getRefreshToken());
        if (username != null) {
            String accessToken = jwtProvider.generateAccessToken(username);
            return new JWTResponse(accessToken, null);
        }
        return new JWTResponse(null, null);
    }

    public JWTResponse getRefreshToken(RefreshJWTRequest refreshRequest) {
        String username = jwtProvider.validateRefreshToken(refreshRequest.getRefreshToken());
        if (username != null) {
            String refreshToken = jwtProvider.generateRefreshToken(username);
            return new JWTResponse(null, refreshToken);
        }
        return new JWTResponse(null, null);
    }

}    

