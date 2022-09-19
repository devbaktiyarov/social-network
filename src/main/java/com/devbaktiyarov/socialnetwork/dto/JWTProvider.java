package com.devbaktiyarov.socialnetwork.dto;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTProvider {

    private final String jwtAccessSecret;
    private final String issuer;

    public JWTProvider(
            @Value("${jwt.secret}") String jwtAccessSecret,
            @Value("${jwt.issuer}") String issuer) {
        this.jwtAccessSecret = jwtAccessSecret;
        this.issuer = issuer;
    }

    public String generateToken(String username) {

        Date tokenExparation = Date.from(ZonedDateTime
                .now()
                .plusMinutes(60)
                .toInstant());

        return JWT.create()
                .withSubject("Auth details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(tokenExparation)
                .sign(Algorithm.HMAC256(jwtAccessSecret));

    }

    public String validateToken(String token) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtAccessSecret))
                .withSubject("Auth details")
                .withIssuer(issuer)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

}
