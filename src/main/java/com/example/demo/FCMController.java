package com.example.demo;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FCMController {
    private Logger log = LoggerFactory.getLogger(FCMInitializer.class);
    @Value("${deviceToken}")
    private String deviceToken = "";

    @GetMapping
    public void send() {
        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle("title")
                                .setBody("body")
                                .build())
                .setToken(deviceToken)
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("could not send push notification");
            e.printStackTrace();
        }

        log.info("Successfully sent message: " + response);
    }
}
