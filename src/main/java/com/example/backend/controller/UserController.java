package com.example.backend.controller;

import com.example.backend.model.Users;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    @EJB
    UserService userService;


    @POST
    public Response register(String jsonBody, @Context HttpServletRequest request, @Context HttpServletResponse response, @Context HttpHeaders headers) {
        System.out.println("add point called");
        Response.Status status = Response.Status.CREATED;
        Response.status(Response.Status.CREATED);

        String message = null;
        Users newUser = null;
        try {
            // Parse the JSON body into a Map or a custom class (e.g., Point)
            ObjectMapper objectMapper = new ObjectMapper();
            newUser = objectMapper.readValue(jsonBody, Users.class);
            // Save the user to the database and get the updated list of points
            message = userService.register(newUser);
        } catch (NumberFormatException | NullPointerException ex) {
            ex.printStackTrace();
            status = Response.Status.BAD_REQUEST;
            message = ex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            status = Response.Status.INTERNAL_SERVER_ERROR;
            message = ex.toString();
        }

        return Response
                .status(status)
                .entity(message)
                .build();
    }
}


