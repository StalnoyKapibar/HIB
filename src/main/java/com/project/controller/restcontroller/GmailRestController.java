package com.project.controller.restcontroller;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.nimbusds.jose.util.Base64URL;
import com.project.model.MessageDTO;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;


@RestController
@NoArgsConstructor
public class GmailRestController {

    public static String code;

    public static Gmail gmail;

    @GetMapping(value = "/gmail/{userId}/messages")
    public List<MessageDTO> getMessages(@PathVariable("userId") String userId) throws IOException {
        if (gmail == null) {
            List<MessageDTO> gmailErrorMessage = new ArrayList<>();
            gmailErrorMessage.add(new MessageDTO("", "", "noGmailAccess"));
            return gmailErrorMessage;
        }

        ListMessagesResponse responseFromUser = gmail.users().messages().list("me").setQ("from:" + userId).execute();
        ListMessagesResponse responseFromAdmin = gmail.users().messages().list("me").setQ("to:" + userId).execute();
        List<Message> messagesFromUser = new ArrayList<>();
        List<Message> messagesFromAdmin = new ArrayList<>();
        Map<String, MessageDTO> messageMap = new TreeMap<>();

        fillMessageMap(responseFromUser, messagesFromUser, messageMap, userId);
        fillMessageMap(responseFromAdmin, messagesFromAdmin, messageMap, gmail.users().getProfile("me").getUserId());
        return formChat(messageMap);
    }

    @PostMapping(value = "/gmail/{userId}/messages")
    public MessageDTO sendMessage(@PathVariable("userId") String userId, @RequestBody String messageText) throws IOException, MessagingException {
        MimeMessage mimeMessage = getMessage(gmail.users().getProfile("me").getUserId(), userId, messageText);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mimeMessage.writeTo(baos);
        String encodedEmail = Base64URL.encode(baos.toByteArray()).toString();
        Message message = new Message();
        message.setRaw(encodedEmail);
        message = gmail.users().messages().send("me", message).execute();
        message = gmail.users().messages().get("me", message.getId()).execute();
        MessageDTO messageDTO = new MessageDTO(message.getThreadId(), "me", messageText);
        return messageDTO;
    }

    private Map<String, MessageDTO> fillMessageMap(ListMessagesResponse response, List<Message> messages, Map<String, MessageDTO> map, String userId) throws IOException {
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = gmail.users().messages().list("me").setQ("from:" + userId)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        for (Message message : messages) {
            map.put(message.getThreadId(), new MessageDTO(message.getId(), userId, ""));
        }
        return map;
    }

    private List<MessageDTO> formChat(Map<String, MessageDTO> map) throws IOException {
        List<MessageDTO> chat = new ArrayList<>();

        for (MessageDTO message : map.values()) {
            Message fullMessage = gmail.users().messages().get("me", message.getMessageThreadId()).execute();

            Base64URL base64URL;
            if (fullMessage.getPayload().getParts() != null) {
                base64URL = new Base64URL(fullMessage.getPayload().getParts().get(0).getBody().getData());
            } else {
                base64URL = new Base64URL(fullMessage.getPayload().getBody().getData());
            }
            message.setText(base64URL.decodeToString());

            chat.add(message);
        }
        return chat;
    }

    private MimeMessage getMessage(String from, String to, String text) throws MessagingException {
        Properties prop = new Properties();
        Session session = Session.getDefaultInstance(prop);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.setContent(text, "text/plain");
        mimeMessage.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        return mimeMessage;
    }
}
