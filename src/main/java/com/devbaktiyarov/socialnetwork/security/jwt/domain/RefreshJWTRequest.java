package com.devbaktiyarov.socialnetwork.security.jwt.domain;

import lombok.Data;

@Data
public class RefreshJWTRequest {
    private String refreshToken;
}
