package org.firebase.web;

import org.firebase.dto.UserDto;
import org.firebase.service.FirebaseUserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response; 
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    FirebaseUserService userService;

    @POST
    public Response addUser(UserDto user) {
        try {
            String message = userService.addUserToFirestore(user);
            return Response.ok(message).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") String userId) {
        try {
            UserDto user = userService.getUserFromFirestore(userId);
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response updateUser(UserDto user) {
        try {
            String message = userService.updateUserInFirestore(user);
            return Response.ok(message).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String userId) {
        try {
            String message = userService.deleteUserFromFirestore(userId);
            return Response.ok(message).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
