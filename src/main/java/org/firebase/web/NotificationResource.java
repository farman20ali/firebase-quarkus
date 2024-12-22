package org.firebase.web;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.firebase.dto.NotificationRequest;
import org.firebase.service.FirebaseService;
import org.firebase.service.NotificationService; 

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/notifications")
public class NotificationResource {

    @Inject
    FirebaseService firebaseService;
     @Inject
    NotificationService notificationService;

 
    @POST
    @Path("/token-to-topic")
    public Response subscribeToTopic(NotificationRequest request) {
        List<String> tokens =null;
        if(request.getToken()!=null&& !request.getToken().isEmpty()){
           tokens=Collections.singletonList(request.getToken());
        }else{
            tokens=request.getTokenList();
        }
       
        String result = notificationService.subscribeToTopic(tokens, request.getTopic()!=null?request.getTopic():"all-users");
        return Response.ok(result).build();
    }


    @POST
    @Path("/send-to-all")
    public Response sendToAllUsers(NotificationRequest notificationRequest) {
        try {
            notificationService.sendToAllUsers("all-users",notificationRequest);
            return Response.ok("Notification sent to all users").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getCause().getMessage()).build();
        }
    }

    @POST
    @Path("/send-to-user")
    public Response sendToUser(NotificationRequest notificationRequest) {
        try {
            List<String> tokens =Collections.singletonList(notificationRequest.getToken());
            notificationService.sendToUser(tokens, notificationRequest);
            return Response.ok("Notification sent to user " + notificationRequest.getUserId()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getCause().getMessage()).build();
        }
    }
  
}
