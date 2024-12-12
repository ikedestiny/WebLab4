package com.example.backend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

public class RubbishTests {
    public static void main(String[] args) {
        Algorithm algorithm = Algorithm.HMAC256("baeldung");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Baeldung")
                .build();

        String jwtToken = JWT.create()
                .withIssuer("Baeldung")
                .withSubject("Baeldung Details")
                .withClaim("userId", "1234")
                .withClaim("email","e@e.ru")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
                .withJWTId(UUID.randomUUID()
                        .toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);

        //System.out.println(jwtToken);


        try {
            DecodedJWT decodedJWT = verifier.verify("oifuhhudhu");
            System.out.println(decodedJWT.getClaims());
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
    }
}
