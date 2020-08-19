package com.project.controller.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.project.controller.restcontroller.GmailRestController;
import com.project.controller.restcontroller.ParseGmailController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@Lazy
@PropertySource("classpath:gmail.properties")
public class GmailController {

    @Value("${clientId}")
    private String clientId;
    @Value("${clientSecret}")
    private String clientSecret;
    @Value("${server.port}")
    private int port;
    @Value("${server.host}")
    private String adress;

    @GetMapping(value = "/gmail/admin", params = "code")
    public String handleGoogleAccess(@RequestParam("code") String response) throws IOException {
        GmailRestController.code = response;
        GoogleTokenResponse googleTokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(),
                new JacksonFactory(), clientId, clientSecret, response, adress+port+"/gmail/admin").execute();

        GoogleCredential googleCredential = new GoogleCredential.Builder()
                .setTransport(new NetHttpTransport())
                .setJsonFactory(new JacksonFactory())
                .setClientSecrets(clientId, clientSecret)
                .build()
                .setFromTokenResponse(googleTokenResponse);
        Gmail buildGmail = new Gmail.Builder(new NetHttpTransport(), new JacksonFactory(), googleCredential).build();
        GmailRestController.gmail = buildGmail;
        ParseGmailController.gmail = buildGmail;
        return "admin/admin-page";
    }

    @GetMapping(value = "/gmail/admin", params = "error")
    public String handleGoogleFailure(@RequestParam("error") String response) {
        GmailRestController.code = null;
        GmailRestController.gmail = null;
        return "admin/admin-page";
    }
}
