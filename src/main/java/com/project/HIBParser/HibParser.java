package com.project.HIBParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.Book;
import com.project.model.Category;
import com.project.model.Image;
import com.project.model.LocaleString;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.StorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class HibParser {

    private final BookService bookService;
    private final ObjectMapper objectMapper;
    private final StorageService storageService;

    private static final String AVATAR = "avatar.jpg";
    private static final String PATH_TO_TMP = "img/tmp/";

    public HibParser(BookService bookService, ObjectMapper objectMapper, StorageService storageService) {
        this.bookService = bookService;
        this.objectMapper = objectMapper;
        this.storageService = storageService;
    }

    private LocaleString initLocaleString(JsonNode node) {
        return new LocaleString(node.get("ru").asText(), node.get("en").asText(),
                node.get("fr").asText(), node.get("it").asText(), node.get("de").asText(),
                node.get("cs").asText(), node.get("gr").asText());
    }

    private void writeImgToFile(String path, byte[] bytes) {
        try {
            FileUtils.writeByteArrayToFile(new File(path), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Book getBookFromJSON(String json) {
        JsonNode jsonNode = null;
        long id = Long.parseLong(bookService.getLastIdOfBook()) + 1;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Image> listImage = new ArrayList<>();
        listImage.add(new Image(0, AVATAR));
        String avatarPath = PATH_TO_TMP + AVATAR;
        byte[] decodedBytes = Base64.getDecoder().decode(jsonNode.get("avatar").asText());
        writeImgToFile(avatarPath, decodedBytes);

        JsonNode listBytes = jsonNode.get("additionalPhotos");
        for (int i = 0; i < listBytes.size(); i++) {
            String additionalPhoto = PATH_TO_TMP + i + ".jpg";
            byte[] tmpDecodedBytes = Base64.getDecoder().decode(listBytes.get(i).asText());
            writeImgToFile(additionalPhoto, tmpDecodedBytes);
            listImage.add(new Image(0, i + ".jpg"));
        }

        Category category = new Category();
        category.setCategoryName(jsonNode.get("category").asText());

        return Book.builder()
                .id(id)
                .name(initLocaleString(jsonNode.get("name")))
                .author(initLocaleString(jsonNode.get("author")))
                .description(initLocaleString(jsonNode.get("desc")))
                .edition(initLocaleString(jsonNode.get("edition")))
                .yearOfEdition(jsonNode.get("yearOfEdition").asText())
                .pages(jsonNode.get("pages").asLong())
                .price(jsonNode.get("price").asLong())
                .originalLanguageName(jsonNode.get("originalLanguage").asText())
                .coverImage(AVATAR)
                .listImage(listImage)
                .category(category).build();
    }

    public void saveBooks(List<String> booksAsJson) {
        for (String json : booksAsJson) {
            Book book = getBookFromJSON(json);
            book.setShow(false);
            bookService.addBook(book);
            String lastId = bookService.getLastIdOfBook();
            storageService.createNewPaperForImages(lastId);
            storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(lastId, book.getListImage());
        }
    }
}
