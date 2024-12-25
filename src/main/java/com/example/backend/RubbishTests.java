package com.example.backend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.authenticaction.BCrypt;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class RubbishTests {
    public static void main(String[] args) {
        String uri = "mongodb+srv://ikeholy65:WE38JqNJm3RretKy@destiny.p6zil.mongodb.net/?retryWrites=true&w=majority&appName=destiny";
        MongoClient client = MongoClients.create(uri);
        MongoDatabase database = client.getDatabase("destiny");

        // Create a collection if it doesn't exist
        if (!database.listCollectionNames().into(new ArrayList<>()).contains("users")) {
            database.createCollection("users");
            System.out.println("Collection 'users' created.");
        } else {
            System.out.println("Collection 'users' already exists.");
        }

        client.close();
    }

}
