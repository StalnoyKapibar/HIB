package com.project.controller.restcontroller;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;

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

    @GetMapping(value = "/markasread")
    Map<String, Boolean> markAsRead (@PathParam("email") String email) throws IOException {
        Map<String, Boolean> markAsRead = new HashMap<>();
        if (gmail != null){
            List<String> ids = new ArrayList<>();
            ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(new ArrayList<String>(Arrays.asList("UNREAD")));
            gmail.users().messages().list("me").setQ("from:" + email + " is:unread").execute()
            .getMessages().stream().forEach(message -> ids.add(message.getId()));
            for (String id: ids) {
                gmail.users().messages().modify("me", id, mods).execute();
            }
            markAsRead.put("markasread", true);
        } else {
            markAsRead.put("gmailAccess", false);
        }
        return markAsRead;
    }

}