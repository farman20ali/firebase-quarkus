package org.firebase.service;

import java.util.List; 
import org.firebase.dto.NotificationRequest;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;

import jakarta.enterprise.context.ApplicationScoped;
 
@ApplicationScoped
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public NotificationService(FirebaseService firebaseService) {
        this.firebaseMessaging = firebaseService.getMessaging();
    }

    /**
     * Send notification to a specific user.
     */
    public ApiFuture<String> sendToToken(String token, NotificationRequest request) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .build()
                )
                .build();

        return firebaseMessaging.sendAsync(message);
    }

    
    /**
     * Send notification to a specific user's devices.
     */
    public void sendToUser(List<String> tokens,NotificationRequest notificationRequest) {
        tokens.forEach(token ->{ try{sendToToken(token, notificationRequest).get();}catch(Exception e){System.out.println(e);};});
    }
    /**
     * Send notification to all users via topic subscription.
     */
    public ApiFuture<String> sendToAllUsers(String topic,NotificationRequest request) {
        Message message = Message.builder()
                .setTopic(topic)
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .build()
                )
                .build();

        return firebaseMessaging.sendAsync(message);
    }
 
 /**
     * Subscribes a list of tokens to a specific topic.
     *
     * @param tokens List of device tokens to subscribe.
     * @param topic  The topic to which tokens should be subscribed.
     * @return Response message.
     */
    public String subscribeToTopic(List<String> tokens, String topic) {
        try {
            TopicManagementResponse response = firebaseMessaging.subscribeToTopic(tokens, topic);
            // Handle successful subscriptions
            int successCount = response.getSuccessCount();
            // Handle invalid tokens
            response.getErrors().forEach(error -> {
                String invalidToken = tokens.get(error.getIndex());
                // Remove invalidToken from your database
                System.out.println("invalid token : "+invalidToken);
            });
            return "Successfully subscribed " + successCount + " tokens to topic: " + topic;
        } catch (Exception e) {
            throw new RuntimeException("Failed to subscribe to topic: " + e.getMessage(), e);
        }
    }
    

    /**
     * Unsubscribes a list of tokens from a specific topic.
     *
     * @param tokens List of device tokens to unsubscribe.
     * @param topic  The topic from which tokens should be unsubscribed.
     * @return Response message.
     */
    public String unsubscribeFromTopic(List<String> tokens, String topic) {
        try {
            TopicManagementResponse response = firebaseMessaging.unsubscribeFromTopic(tokens, topic);
            return "Successfully unsubscribed " + response.getSuccessCount() + " tokens from topic: " + topic;
        } catch (Exception e) {
            throw new RuntimeException("Failed to unsubscribe from topic: " + e.getMessage(), e);
        }
    }
}
