package com.example.backend.authenticaction;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.UUID;

@Path("/login")
public class Authenticator {
    private final String key = "dev.destiny.web.lab4";
    private final String issuer = "destiny";
    private final Algorithm algorithm = Algorithm.HMAC256(key);
   private final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(String jsonBody, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        Users user = null;
        try {
            user = mapper.readValue(jsonBody, Users.class);
            String token = issueToken(user.getUsername(), user.getEmail());
            return Response.ok(token).build();
        } catch (Exception e) {

        }
        return null;
    }

    public void authenticate(String jwtToken){
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            System.out.println(decodedJWT.getClaims());
        } catch (JWTVerificationException e) {
           throw new JWTVerificationException(e.getMessage());
        }
    }

    private String issueToken(String username,String email) {


        return JWT.create()
                .withIssuer(issuer)
                .withSubject("client auth")
                .withClaim("username", username)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 300000L)) // 5 minutes (300,000 milliseconds)
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);
    }
}
