package com.project.util;

import com.google.api.services.gmail.Gmail;
import com.nimbusds.jose.util.Base64URL;
import net.minidev.json.JSONUtil;

import java.io.IOException;
import java.util.Date;

public class ParseGmail {

    private static long currentTimestamp = new Date().getTime();
    private static int interval = 60000;

    public static void start(Gmail gmail) throws IOException {
        //gmail.users().messages().list("me").setQ("after:" + 1591618537).execute()
        //gmail.users().messages().list("me").setQ("after:" + 1591618537).execute().getMessages().get(3)
        //gmail.users().messages().get("me", "172943d02e20989f").execute()
        //base64URL = new Base64URL(fullMessage.getPayload().getParts().get(0).getBody().getData());
        //text = base64URL.decodeToString();
        gmail.users().messages().list("me").setQ("after:" + currentTimestamp).execute();

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
