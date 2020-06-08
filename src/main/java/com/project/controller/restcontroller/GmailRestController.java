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

    private ArrayList<MessageDTO> fullchat;

    @GetMapping(value = "/gmail/{userId}/messages/{part}")
    public List<MessageDTO> getMessages(@PathVariable("userId") String userId, @PathVariable("part") String part) throws IOException {
        if (gmail == null) {
            List<MessageDTO> gmailErrorMessage = new ArrayList<>();
            gmailErrorMessage.add(new MessageDTO("", "", "noGmailAccess"));
            return gmailErrorMessage;
        }
        if (part.equals("0")) {
            ListMessagesResponse responseFromUser = gmail.users().messages().list("me").setQ("from:" + userId).execute();
            ListMessagesResponse responseFromAdmin = gmail.users().messages().list("me").setQ("to:" + userId).execute();
            List<Message> messagesFromUser = new ArrayList<>();
            List<Message> messagesFromAdmin = new ArrayList<>();
            Map<String, MessageDTO> messages = new TreeMap<>(Collections.reverseOrder());

            fillMessageMap(responseFromUser, messagesFromUser, messages, userId);
            fillMessageMap(responseFromAdmin, messagesFromAdmin, messages, gmail.users().getProfile("me").getUserId());
            fullchat = new ArrayList<>(messages.values());
        }

        List<MessageDTO> chat = formChat(part);

        return chat;
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

    private List<MessageDTO> formChat(String part) throws IOException {
        int startId = Integer.parseInt(part) * 3;
        int endIdExclude = startId + 3;
        List<MessageDTO> chat = new ArrayList<>();

        for (int i = startId; i < endIdExclude; i++) {
            if (i < fullchat.size()) {
                MessageDTO messageDTO = fullchat.get(i);
                Message fullMessage = gmail.users().messages().get("me", messageDTO.getMessageId()).execute();
                Base64URL base64URL;
                if (fullMessage.getPayload().getParts() != null) {
                    base64URL = new Base64URL(fullMessage.getPayload().getParts().get(0).getBody().getData());
                } else {
                    base64URL = new Base64URL(fullMessage.getPayload().getBody().getData());
                }
                messageDTO.setText(base64URL.decodeToString());
                chat.add(messageDTO);
            }
        }
        if (chat.isEmpty()) {
            List<MessageDTO> endChat = new ArrayList<>();
            endChat.add(new MessageDTO("0", "", "chat end"));
            return endChat;
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