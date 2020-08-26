package com.project.controller.restcontroller;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.nimbusds.jose.util.Base64URL;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@NoArgsConstructor
@RequestMapping("/admin")
public class ParseGmailController {

    public static Gmail gmail;

    @PostMapping (value = "/unreademails")
    Map<String, Boolean> getUnreadEmails(@RequestBody List<String> emails) throws IOException {
        Map<String, Boolean> unreadEmails = new HashMap<>();
        if (gmail != null) {
            unreadEmails.put("gmailAccess", true);
            for (String email: emails) {
                if (gmail.users().messages().list("me").setQ("from:" + email + " is:unread").execute()
                        .getResultSizeEstimate() != 0) {
                    unreadEmails.put(email, true);
                } else {
                    unreadEmails.put(email, false);
                }
            }
        } else {
            unreadEmails.put("gmailAccess", false);
        }
        return unreadEmails;
    }

    @PostMapping (value = "/unreadgmail")
    Map<String, List<String>> getUnreadContent (@RequestBody List<String> emails) {
        Map<String, List<String>> unreadcontent = new HashMap<>();
        List<Message> messagesResponse = new ArrayList<>();
        if (gmail != null) {
            emails.forEach(email -> {
                ListMessagesResponse listMessagesResponse = new ListMessagesResponse();
                try {
                    listMessagesResponse = gmail.users().messages().list("me").setQ("from:" + email + " is:unread").execute();
                    if (listMessagesResponse.getResultSizeEstimate() != 0){
                        messagesResponse.add(gmail.users().messages().get("me", listMessagesResponse.getMessages().get(0).getId()).execute());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            messagesResponse.forEach(message -> {
                String email = message.getPayload().getHeaders().stream()
                        .filter(headers -> headers.getName().equals("From")).findFirst().get().getValue().split("<")[1].replace(">", "");
                String subject = message.getPayload().getHeaders().stream()
                        .filter(headers -> headers.getName().equals("Subject")).findFirst().get().getValue();
                String snippet = message.getSnippet();
                List<String> content = Arrays.asList(subject, snippet);
                unreadcontent.put(email, content);
            });
        }
        return unreadcontent;
    }

    @GetMapping(value = "/markasread")
    Map<String, Boolean> markAsRead (@PathParam("email") String email) throws IOException {
        Map<String, Boolean> markAsRead = new HashMap<>();
        if (gmail != null){
            ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(new ArrayList<String>(Arrays.asList("UNREAD")));
            gmail.users().messages().list("me").setQ("from:" + email + " is:unread").execute()
                    .getMessages().stream().forEach(message -> {
                try {
                    gmail.users().messages().modify("me", message.getId(), mods).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            markAsRead.put("markasread", true);
        } else {
            markAsRead.put("gmailAccess", false);
        }
        return markAsRead;
    }

    // method I'm working on
    @GetMapping(value = "/markmessageasread/{email}/{order}")
    Map<String, Boolean> markAsReadMessage (@PathVariable("email") String userId,
                                            @PathVariable("order") String subject) throws IOException {
        Map<String, Boolean> markAsRead = new HashMap<>();
        if (gmail != null){
            ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(new ArrayList<>(Arrays.asList("UNREAD")));
            List<Message> messagesFromUser = gmail.users()
                    .messages()
                    .list("me")
                    .setQ("(" + "subject:" + "\"Order №" + subject + "\"" + "from:" + userId + " is:unread" + ")")
                    .execute()
                    .getMessages();
            if (messagesFromUser != null){
                messagesFromUser.stream()
                                .forEach(message -> {
                                        try {
                                            gmail.users().messages().modify("me", message.getId(), mods).execute();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });
                markAsRead.put("markasread", true);
            } else {
                markAsRead.put("markasread", false);
            }
        } else {
            markAsRead.put("gmailAccess", false);
        }
        return markAsRead;
    }

    public void modifyMessage(String userId, String messageId) throws IOException {
        List<String> lblIDRemove=new ArrayList<String>();
        lblIDRemove.add("UNREAD");
        ModifyMessageRequest mods = new ModifyMessageRequest()
                .setAddLabelIds(null)
                .setRemoveLabelIds(lblIDRemove);
        Message message = gmail.users().messages().modify(userId, messageId, mods).execute();
    }



}