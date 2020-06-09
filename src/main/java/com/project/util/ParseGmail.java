package com.project.util;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.project.service.abstraction.OrderService;
import com.project.service.abstraction.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service("ParseGmail")
public class ParseGmail {

    private static long currentTimestamp = Instant.now().getEpochSecond() - (60 * 60);
    private static int interval = 60000;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    OrderService orderService;

    public void start(Gmail gmail) throws IOException {

        ListMessagesResponse messagesResponse = gmail.users().messages().list("me").setQ("after:" + currentTimestamp).execute();
        List<Message> messageList = new ArrayList<>();
        List<List<MessagePartHeader>> headers = new ArrayList<>();
        List<String> emails = new ArrayList<>();
        List<Long> idsOfUsers = new ArrayList<>();

        if (messagesResponse.getResultSizeEstimate() > 1) {
            messagesResponse.getMessages().stream().forEach((message -> {
                try {
                    messageList.add(gmail.users().messages().get("me", message.getId()).execute());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            messageList.stream().forEach(message -> {
                headers.add(message.getPayload().getHeaders());
            });
            
            headers.stream().forEach(messagePartHeaders -> {
                messagePartHeaders.stream().forEach(messagePartHeader -> {
                    if (messagePartHeader.getName().equals("From")) {
                        emails.add(messagePartHeader.getValue().split("<")[1].replace(">", ""));
                    }
                });
            });

            emails.stream().forEach(email -> {
                if (userAccountService.emailExist(email)) {
                    idsOfUsers.add(userAccountService.getUserByEmail(email).getId());
                }
            });

            idsOfUsers.stream().forEach(id -> {
                if (orderService.getOrdersByUserId(id).size() > 0) {
                    
                }
            });

        } else {

        }

        /*while (true) {
            setTimeout(() -> {
                try {
                    gmail.users().messages().list("me").setQ("after:" + currentTimestamp).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, interval);
            currentTimestamp += interval;
        }*/
    }



    private static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

}
