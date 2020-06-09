package com.project.util;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.nimbusds.jose.util.Base64URL;
import net.minidev.json.JSONUtil;

import java.io.IOException;
import java.time.Instant;

public class ParseGmail {

    private static long currentTimestamp = Instant.now().getEpochSecond() - (60 * 60 * 60);
    private static int interval = 60000;

    public static void start(Gmail gmail) throws IOException {
        //gmail.users().messages().list("me").setQ("after:" + 1591618537).execute()
        //gmail.users().messages().list("me").setQ("after:" + 1591618537).execute().getMessages().get(3)
        //gmail.users().messages().get("me", "17295a3db4e1e8a4").execute()
        //base64URL = new Base64URL(fullMessage.getPayload().getParts().get(0).getBody().getData());
        //text = base64URL.decodeToString();
        ListMessagesResponse messagesResponse = gmail.users().messages().list("me").setQ("after:" + currentTimestamp).execute();
        if (messagesResponse.getResultSizeEstimate() > 1) {
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
