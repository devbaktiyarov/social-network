package com.devbaktiyarov.socialnetwork.security.jwt.domain;

import lombok.Data;

@Data
public class JWTRequest {
    private String username;
    private String password;
}
