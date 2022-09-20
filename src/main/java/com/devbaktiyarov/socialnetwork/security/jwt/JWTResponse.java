package com.devbaktiyarov.socialnetwork.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTResponse {
    private final String type = "Bearer";
    private String accessToken;
}
