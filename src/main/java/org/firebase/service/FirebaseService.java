package org.firebase.service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import com.google.firebase.cloud.FirestoreClient;



import org.firebase.util.ConstantValues;

import java.io.FileInputStream;

import com.google.cloud.firestore.Firestore;

@ApplicationScoped
public class FirebaseService {

    private Firestore firestore;
    private FirebaseDatabase realtimeDatabase;
    
    // void init(@Observes StartupEvent ev) 
    @PostConstruct
    void init()
     {
        try (FileInputStream serviceAccount = new FileInputStream(ConstantValues.firebaseServicesPath)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(ConstantValues.databaseURL)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            this.firestore = FirestoreClient.getFirestore();
            this.realtimeDatabase = FirebaseDatabase.getInstance();
        } catch (Exception e) {
            throw new RuntimeException("Firebase initialization failed", e);
        }
    }

    public Firestore getFirestore() {
        return firestore;
    }

    public FirebaseDatabase getRealtimeDatabase() {
        return realtimeDatabase;
    }

    public FirebaseMessaging getMessaging() {
        return FirebaseMessaging.getInstance();
    }
}
