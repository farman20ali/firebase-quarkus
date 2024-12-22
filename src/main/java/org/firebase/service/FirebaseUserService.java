package org.firebase.service;

import org.firebase.dto.UserDto; 
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class FirebaseUserService {

    @Inject
    FirebaseService firebaseService;

    public String addUserToFirestore(UserDto user) throws ExecutionException, InterruptedException {
        CollectionReference users = firebaseService.getFirestore().collection("users");
        DocumentReference docRef = users.document();
        user.setId(docRef.getId());
        WriteResult result = docRef.set(user).get();
        return "User added at: " + result.getUpdateTime();
    }

    public UserDto getUserFromFirestore(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firebaseService.getFirestore().collection("users").document(userId);
        return docRef.get().get().toObject(UserDto.class);
    }

    public String updateUserInFirestore(UserDto user) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firebaseService.getFirestore().collection("users").document(user.getId());
        WriteResult result = docRef.set(user).get();
        return "User updated at: " + result.getUpdateTime();
    }

    public String deleteUserFromFirestore(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firebaseService.getFirestore().collection("users").document(userId);
        WriteResult result = docRef.delete().get();
        return "User deleted at: " + result.getUpdateTime();
    }
}
