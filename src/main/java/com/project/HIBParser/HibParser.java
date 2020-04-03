package com.project.HIBParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.BookDTO;
import com.project.model.Image;
import com.project.model.LocaleString;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class HibParser {

    @Autowired
    private ObjectMapper objectMapper;

    private LocaleString initLocaleString(JsonNode node) {
        return new LocaleString(node.get("ru").asText(), node.get("en").asText(),
                node.get("fr").asText(), node.get("it").asText(), node.get("de").asText(),
                node.get("cs").asText(), node.get("gr").asText());
    }

    public BookDTO getBookFromJSON(String json) {
        BookDTO book = new BookDTO();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            book.setName(initLocaleString(jsonNode.get("name")));
            book.setAuthor(initLocaleString(jsonNode.get("author")));
            book.setDesc(initLocaleString(jsonNode.get("desc")));
            book.setEdition(initLocaleString(jsonNode.get("edition")));
            book.setYearOfEdition(jsonNode.get("yearOfEdition").asText());
            book.setPages(jsonNode.get("pages").asLong());
            book.setPrice(jsonNode.get("price").asInt());

            String avatarPath = "img/" + book.getName().getEn().replaceAll(" ","") + "/" + "avatar.jpg";
            byte[] decodedBytes = Base64.getDecoder().decode(jsonNode.get("avatar").asText());
            FileUtils.writeByteArrayToFile(new File(avatarPath), decodedBytes);
            JsonNode listBytes = jsonNode.get("additionalPhotos");
            book.setCoverImage(avatarPath);

            List<Image>listImage = new ArrayList<>();
            for (int i = 0; i < listBytes.size(); i++) {
                String additionalPhoto = "img/" + book.getName().getEn() + "/" + i + ".jpg";
                byte[] tmpDecodedBytes = Base64.getDecoder().decode(listBytes.get(i).asText());
                FileUtils.writeByteArrayToFile(new File(additionalPhoto), tmpDecodedBytes);
                listImage.add(new Image(0, additionalPhoto));
            }
            book.setImageList(listImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }
}
