package com.project.translate;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class HibTranslatorImp implements HibTranslator {

    @Value("${key}")
    private String key;

    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public String translate(String text, String lang) {
        byte out[] = ("text=" + text).getBytes();
        if (out.length > 10000) {
            return "Error. Text too long";
        }
        String finalUrl = baseUrl + "?lang=" + lang + "&key=" + key;
        try {
            URL url = new URL(finalUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Length", out.length + "");
            connection.setRequestProperty("Accept", "*/*");
            connection.getOutputStream().write(out);
            if (connection.getResponseCode() == 200) {
                JsonObject jobj = new JsonParser().parse(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
                JsonArray jarr = jobj.get("text").getAsJsonArray();
                String transText = jarr.get(0).getAsString();
                return transText.substring(0, transText.length() - 1);
            } else {
                return "Error. Site response non 200";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
