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

    @GetMapping(value = "/gmail/{userId}/{subject}/{part}")
    public List<MessageDTO> getMessages(@PathVariable("userId") String userId, @PathVariable("subject") String subject, @PathVariable("part") String part) throws IOException {
        if (gmail == null) {
            List<MessageDTO> gmailErrorMessage = new ArrayList<>();
            gmailErrorMessage.add(new MessageDTO("", "", "noGmailAccess", subject));
            return gmailErrorMessage;
        }
        if (part.equals("0")) {
            ListMessagesResponse responseFromUser = gmail.users().messages().list("me").setQ("(" + "subject:" + "\"" + subject + "\"" + "from:" + userId + ")").execute();
            ListMessagesResponse responseFromAdmin = gmail.users().messages().list("me").setQ("(" + "subject:" + "\"" + subject + "\"" + "to:" + userId + ")").execute();
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

    @GetMapping(value = "/gmail/{userId}/{part}")
    public List<MessageDTO> getMessagesWithoutSubject(@PathVariable("userId") String userId, @PathVariable("part") String part) throws IOException {
        if (gmail == null) {
            List<MessageDTO> gmailErrorMessage = new ArrayList<>();
            gmailErrorMessage.add(new MessageDTO("", "", "noGmailAccess", ""));
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

    @PostMapping(value = "/gmail/{userId}/{subject}")
    public MessageDTO sendMessage(@PathVariable("userId") String userId, @PathVariable("subject") String subject, @RequestBody String messageText) throws IOException, MessagingException {
        if (subject.equals("noSubject")) {
            subject = "";
        }
        messageText = messageText.replace("\\n", "\r\n");
        messageText = messageText.substring(1, messageText.length() - 1);
        MimeMessage mimeMessage = getMessage(gmail.users().getProfile("me").getUserId(), userId, subject, messageText);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mimeMessage.writeTo(baos);
        String encodedEmail = Base64URL.encode(baos.toByteArray()).toString();
        Message message = new Message();
        message.setRaw(encodedEmail);
        message = gmail.users().messages().send("me", message).execute();
        message = gmail.users().messages().get("me", message.getId()).execute();
        messageText = messageText.replace("\r\n", "<br>");
        MessageDTO messageDTO = new MessageDTO(message.getId(), "me", messageText, subject);
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
            map.put(message.getId(), new MessageDTO(message.getId(), userId, "", ""));
        }
        return map;
    }

    private List<MessageDTO> formChat(String part) throws IOException {
        String text;
        int startId = Integer.parseInt(part) * 10;
        int endIdExclude = startId + 10;
        List<MessageDTO> chat = new ArrayList<>();

        for (int i = startId; i < endIdExclude; i++) {
            if (i < fullchat.size()) {
                MessageDTO messageDTO = fullchat.get(i);
                Message fullMessage = gmail.users().messages().get("me", messageDTO.getMessageId()).execute();
                Base64URL base64URL;
                if (fullMessage.getPayload().getParts() != null) {
                    if (fullMessage.getPayload().getParts().get(0).getBody().getData() == null) {
                        break;
                    }
                    if (fullMessage.getPayload().getParts().get(1).getBody().getData() == null) {
                        base64URL = new Base64URL(fullMessage.getPayload().getParts().get(0).getBody().getData());
                    } else {
                        base64URL = new Base64URL(fullMessage.getPayload().getParts().get(1).getBody().getData());
                    }
                    text = base64URL.decodeToString();
                } else {
                    if (fullMessage.getPayload().getBody().getData() == null) {
                        break;
                    }
                    base64URL = new Base64URL(fullMessage.getPayload().getBody().getData());
                    text = base64URL.decodeToString();
                }
                if (text.startsWith("\"") && text.startsWith("\"", text.length()-1)) {
                    text = text.substring(1, text.length()-1);
                }
                messageDTO.setText(text);
                messageDTO.setSubject(""); //здесь надо придумать, как достать и впихнуть тему сообщения
                chat.add(messageDTO);
            }
        }
        if (chat.isEmpty()) {
            List<MessageDTO> endChat = new ArrayList<>();
            endChat.add(new MessageDTO("0", "", "chat end", ""));
            return endChat;
        }
        return chat;
    }

    private MimeMessage getMessage(String from, String to, String subject, String text) throws MessagingException {
        Properties prop = new Properties();
        Session session = Session.getDefaultInstance(prop);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.setSubject(subject, "UTF-8");
        mimeMessage.setContent(text, "text/plain; charset=UTF-8");
        mimeMessage.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        return mimeMessage;
    }

    @PostMapping(value = "/gmailFeedBack/{userId}/messages")
    public MessageDTO sendMessageFeedBack(@PathVariable("userId") String userId, @RequestBody String messageText) throws IOException, MessagingException {
        String subjectTextResult = messageText.split("&nbsp")[0].substring(1);
        String messageTextResult = messageText.split("&nbsp")[1].substring(0,messageText.split("&nbsp")[1].length()-1);
        MimeMessage mimeMessage = getMessage(gmail.users().getProfile("me").getUserId(), userId, "", messageTextResult);
        mimeMessage.setSubject(subjectTextResult, "UTF-8");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mimeMessage.writeTo(baos);
        String encodedEmail = Base64URL.encode(baos.toByteArray()).toString();
        Message message = new Message();
        message.setRaw(encodedEmail);
        message = gmail.users().messages().send("me", message).execute();
        message = gmail.users().messages().get("me", message.getId()).execute();
        MessageDTO messageDTO = new MessageDTO(message.getThreadId(), "me", messageText, "");
        return messageDTO;
    }
}