package com.example.backend.controller;

import com.example.backend.authenticaction.Secured;
import com.example.backend.model.Request;
import com.example.backend.model.Result;
import com.example.backend.service.ResultService;
import com.example.backend.util.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/results")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResultController {
    @EJB
    private ResultService resultService;

    @GET
    @Secured
    public List<Result> getAllResults(@Context HttpHeaders headers){
        System.out.println("get all points called");
        return resultService.getAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Secured
    public Response addPoint(String jsonBody, @Context HttpServletRequest request, @Context HttpServletResponse response, @Context HttpHeaders headers) {
        //  List<Point> message = new ArrayList<>();
        System.out.println("add point called");
        Response.Status status = Response.Status.CREATED;
        Response.status(Response.Status.CREATED);

        String message = null;
        Request req = null;
        try {
            // Parse the JSON body into a Map or a custom class (e.g., Point)
            ObjectMapper objectMapper = new ObjectMapper();
             req =  objectMapper.readValue(jsonBody,Request.class);
             if (!paramsAreGood(req)){
                 return Response
                         .status(Response.Status.BAD_REQUEST)
                         .entity("one of the parameters are not within range")
                         .build();
             }
            System.out.println(req);
            message = req.toString() + " has been received";
            // Save the point to the database and get the updated list of points
            resultService.addResult(Checker.check(req));
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

    @DELETE
    @Secured
    @Produces("application/json")
    public List<Result> clearAll(){
        resultService.clearAll();
        return resultService.getAll();
    }


    @OPTIONS
    @Path("{path : .*}")
    public Response preflight() {
        System.out.println("preflight called");
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }


    public boolean paramsAreGood(Request request){
        return request.getR() >= -4 && request.getR() <= 4 &&
                request.getX()  <= 4 && request.getX() >= -4 &&
                request.getY()  >= -3 && request.getY() <= 5;

    }


}
