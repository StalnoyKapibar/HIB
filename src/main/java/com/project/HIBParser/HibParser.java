package com.project.HIBParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.*;
import com.project.service.CategoryService;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.StorageService;
import com.project.util.TransliterateUtil;
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
    private final CategoryService categoryService;

    private static final String AVATAR = "avatar.jpg";
    private static final String PATH_TO_TMP = "./img/tmp/";

    public HibParser(BookService bookService, ObjectMapper objectMapper, StorageService storageService, CategoryService categoryService) {
        this.bookService = bookService;
        this.objectMapper = objectMapper;
        this.storageService = storageService;
        this.categoryService = categoryService;
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

    public String getTmpFolderName(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public Book getBookFromJSON(String json, String HIBFileName) {
        String bookTmpFolder = getTmpFolderName();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Image> listImage = new ArrayList<>();
        listImage.add(new Image(AVATAR));
        String avatarPath = PATH_TO_TMP + bookTmpFolder + "/" + AVATAR;
        byte[] decodedBytes = Base64.getDecoder().decode(jsonNode.get("avatar").asText());
        writeImgToFile(avatarPath, decodedBytes);

        JsonNode listPicsBytes = jsonNode.get("additionalPhotos");
        for (int i = 0; i < listPicsBytes.size(); i++) {
            String additionalPhoto = PATH_TO_TMP + bookTmpFolder + "/" + i + ".jpg";
            byte[] tmpDecodedBytes = Base64.getDecoder().decode(listPicsBytes.get(i).asText());
            writeImgToFile(additionalPhoto, tmpDecodedBytes);
            listImage.add(new Image(i + ".jpg"));
        }

        //Категория под id-номеру "1" - "Uncategorized" (без категории). При смене идентификатора данной категории в базе необходимо сменить его и в строке ниже
        Category category  = categoryService.getCategoryById(1L);

        OriginalLanguage courier = new OriginalLanguage();
        courier.setName(bookTmpFolder);
        courier.setAuthor(HIBFileName);

        return Book.builder()
                .name(initLocaleString(jsonNode.get("name")))
                .author(initLocaleString(jsonNode.get("author")))
                .description(initLocaleString(jsonNode.get("desc")))
                .edition(initLocaleString(jsonNode.get("edition")))
                .yearOfEdition(jsonNode.get("yearOfEdition").asText())
                .pages(jsonNode.get("pages").asLong())
                .price(jsonNode.get("price").asLong())
                .originalLanguageName(jsonNode.get("originalLanguage").asText())
                .originalLanguage(courier)  //Данная строка записывает в объект OriginalLanguage книги имя временной папки для картинок и название
                                            //HIB-файла, из которого извлечена книга.
                                            //Объект носит временный характер и будет перезаписан на нужное значение при добавлении книги в базу.
                .coverImage(AVATAR)
                .listImage(listImage)
                .isShow(true)
                .category(category).build();
    }

    public void saveBook(Book book) {
        String picsFolder = book.getOriginalLanguage().getName();
        bookService.addBook(book);
        String lastId = bookService.getLastIdOfBook();
        storageService.createNewPaperForImages(lastId);
        storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(lastId, picsFolder, book.getListImage());
    }

    public void clearTemp(String folderName) {
        storageService.clearPaperTmp(folderName);
    }
}
