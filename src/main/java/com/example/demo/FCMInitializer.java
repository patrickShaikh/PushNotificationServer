package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FCMInitializer {
    private Logger log = LoggerFactory.getLogger(FCMInitializer.class);
    private List<FirebaseApp> apps = new ArrayList<>();

    @Value("${adminSdkPath}")
    private String adminSdkPath;

    @PostConstruct
    public void init() {
        FileInputStream firebaseCredentials = null;
        try {
            firebaseCredentials = new FileInputStream(adminSdkPath);
        } catch (FileNotFoundException e) {
            log.error("could not read json file");
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(firebaseCredentials))
                    .build();
        } catch (IOException e) {
            log.error("could not init");
            e.printStackTrace();
        }

        apps.add(FirebaseApp.initializeApp(options));
    }
}
