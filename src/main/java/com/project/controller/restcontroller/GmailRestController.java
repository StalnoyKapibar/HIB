package com.project.controller.restcontroller;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.nimbusds.jose.util.Base64URL;
import com.project.controller.restcontroller.emailUtil.emailParser.EmailParser;
import com.project.model.ContactsOfOrderDTO;
import com.project.model.FeedbackRequest;
import com.project.model.MessageDTO;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.websocket.server.PathParam;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*")
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
            gmailErrorMessage.add(new MessageDTO("", "", "noGmailAccess", subject, EmailParser.getInstance("")));
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
            gmailErrorMessage.add(new MessageDTO("", "", "noGmailAccess", "", EmailParser.getInstance("")));
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
        MessageDTO messageDTO = new MessageDTO(message.getId(), "me", messageText, subject, EmailParser.getInstance("me"));
        return messageDTO;
    }

    @PostMapping(value = "/gmailFeedBack/{userId}/messages")
    public MessageDTO sendMessageFeedBack(@PathVariable("userId") String userId, @RequestBody String messageText) throws IOException, MessagingException {
        String subjectTextResult = messageText.split("&nbsp")[0].substring(1);
        String messageTextResult = messageText.split("&nbsp")[1].substring(0, messageText.split("&nbsp")[1].length() - 1);
        MimeMessage mimeMessage = getMessage(gmail.users().getProfile("me").getUserId(), userId, "", messageTextResult);
        mimeMessage.setSubject(subjectTextResult, "UTF-8");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mimeMessage.writeTo(baos);
        String encodedEmail = Base64URL.encode(baos.toByteArray()).toString();
        Message message = new Message();
        message.setRaw(encodedEmail);
        message = gmail.users().messages().send("me", message).execute();
        message = gmail.users().messages().get("me", message.getId()).execute();
        MessageDTO messageDTO = new MessageDTO(message.getThreadId(), "me", messageText, "", EmailParser.getInstance("me"));
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
            map.put(message.getId(), new MessageDTO(message.getId(), userId, "", "", EmailParser.getInstance(userId)));
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
                text = getTextBodyofMailWhithoutQuote(fullMessage, messageDTO.getSender());
                messageDTO.setText(text);
                messageDTO.setSubject(getSubject(fullMessage));
                chat.add(messageDTO);
            }
        }
        if (chat.isEmpty()) {
            List<MessageDTO> endChat = new ArrayList<>();
            endChat.add(new MessageDTO("0", "", "chat end", "", EmailParser.getInstance("")));
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

    private String getSubject(Message fullMessage) {
        for (int i = 0; i < fullMessage.getPayload().getHeaders().size(); i++) {
            String header = fullMessage.getPayload().getHeaders().get(i).getName();
            if (header.equalsIgnoreCase("Subject")) {
                return fullMessage.getPayload().getHeaders().get(i).getValue();
            }
        }
        return "";
    }

    //используется на странице с ордерами
    @PostMapping(value = "/admin/unreademails")
    Map<String, Boolean> getUnreadEmails(@RequestBody ContactsOfOrderDTO[] contactsOfOrderDTO) throws IOException {
        Map<String, Boolean> unreadEmails = new HashMap<>();
        if (gmail != null) {
            unreadEmails.put("gmailAccess", true);
            for (ContactsOfOrderDTO order : contactsOfOrderDTO) {
                if (gmail.users().messages().list("me")
                        .setQ("(" + "subject:" + "\"Order №" + order.getId() + "\"" + "from:" + order.getEmail() + " is:unread" + ")")
                        .execute().getResultSizeEstimate() != 0) {
                    unreadEmails.put(Long.toString(order.getId()), true);
                } else {
                    unreadEmails.put(Long.toString(order.getId()), false);
                }
            }
        } else {
            unreadEmails.put("gmailAccess", false);
        }
        return unreadEmails;
    }

    //используется для генерации списка с непрочитанными сообщениями в фидбэках
    @PostMapping(value = "/admin/unreadgmail")
    Map<Long, List<String>> getUnreadContent(@RequestBody List<FeedbackRequest> tmp) {
        Map<Long, List<String>> unreadcontent = new HashMap<>();
        if (gmail != null) {
            for (FeedbackRequest item : tmp) {
                ListMessagesResponse listMessagesResponse = new ListMessagesResponse();
                try {
                    listMessagesResponse = gmail.users().messages().list("me").setQ("from:" + item.getSenderEmail() + " is:unread").execute();
                    //находит список все писем определенного имейла
                    if (listMessagesResponse.getResultSizeEstimate() != 0) {
                        //костыль направленный, на то, что бы  в список сообщений добавлялись, непрочитанные сообщение с опред темой
                        for (Message message : listMessagesResponse.getMessages()) {
                            Message msg = gmail.users().messages().get("me", message.getId()).execute();
                            String subject = getSubject(msg);
                            if (subject.contains("Feedback №" + item.getId())) {
                                String email = msg.getPayload().getHeaders().stream()
                                        .filter(headers -> headers.getName().equals("From")).findFirst().get().getValue().split("<")[1].replace(">", "");
                                String snippet = getTextBodyofMailWhithoutQuote(msg, email);
                                String strRegEx = "<[^>]*>";
                                snippet = snippet.replaceAll(strRegEx, "");
                                snippet = snippet.replace("&nbsp;", "");
                                snippet = snippet.replace("&amp;", "&");
                                List<String> content = Arrays.asList(email, snippet, subject);
                                unreadcontent.put(item.getId(), content);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return unreadcontent;
    }

    @GetMapping(value = "/admin/markasread")
    Map<String, Boolean> markAsRead(@PathParam("email") String email, @PathParam("feedbackId") String feedbackId) throws IOException {
        Map<String, Boolean> markAsRead = new HashMap<>();
        if (gmail != null) {
            ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(new ArrayList<String>(Arrays.asList("UNREAD")));
            ListMessagesResponse lmr = gmail.users().messages().list("me").setQ("from:" + email + " is:unread").execute();
            if (lmr.getResultSizeEstimate() > 0) {
                for (Message message : lmr.getMessages()) {
                    Message msg = gmail.users().messages().get("me", message.getId()).execute();
                    String subject = getSubject(msg);
                    if (subject.contains("Feedback №" + feedbackId)) {
                        gmail.users().messages().modify("me", message.getId(), mods).execute();
                        markAsRead.put("markasread", true);
                    }
                }
            } else {
                markAsRead.put("markasread", false);
            }
        } else {
            markAsRead.put("gmailAccess", false);
        }
        return markAsRead;
    }

    // отметить сообщение в заказе прочитанным
    @GetMapping(value = "/admin/markmessageasread/{email}/{order}")
    Map<String, Boolean> markAsReadMessage(@PathVariable("email") String userId,
                                           @PathVariable("order") String subject) throws IOException {
        Map<String, Boolean> markAsRead = new HashMap<>();
        if (gmail != null) {
            ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(new ArrayList<>(Arrays.asList("UNREAD")));
            List<Message> messagesFromUser = gmail.users()
                    .messages()
                    .list("me")
                    .setQ("(" + "subject:" + "\"Order №" + subject + "\"" + "from:" + userId + " is:unread" + ")")
                    .execute()
                    .getMessages();
            if (messagesFromUser != null) {
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

    private String getTextBodyofMailWhithoutQuote(Message fullMessage, String email) {
        Base64URL base64URL;
        String text = fullMessage.getSnippet();
        if (fullMessage.getPayload().getParts() != null) {
            if (fullMessage.getPayload().getParts().get(0).getBody().getData() != null) {
                if (fullMessage.getPayload().getParts().get(1).getBody().getData() == null) {
                    base64URL = new Base64URL(fullMessage.getPayload().getParts().get(0).getBody().getData());
                } else {
                    base64URL = new Base64URL(fullMessage.getPayload().getParts().get(1).getBody().getData());
                }
                text = base64URL.decodeToString();
            }
        } else if (fullMessage.getPayload().getBody().getData() != null) {
            base64URL = new Base64URL(fullMessage.getPayload().getBody().getData());
            text = base64URL.decodeToString();
            if (text.startsWith("\"") && text.startsWith("\"", text.length() - 1)) {
                text = text.substring(1, text.length() - 1);
            }
        }
        text = EmailParser.getInstance(email).getMessageTextWithoutRe(text);
        return text;
    }
}