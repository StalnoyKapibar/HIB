package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.model.Book;
import com.project.model.HibFileDto;
import com.project.service.abstraction.HibFileService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoadHibFilesRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadHibFilesRestController.class.getName());
    private final HibParser hibParser;

    @PostMapping("/api/admin/get-book-from-json")
    public Book getBookFromJSON(@RequestBody String book) {
        //LOGGER.info("POST request '/api/admin/get-book-from-json' with {}", book);
        return hibParser.getBookFromJSON(book);
    }

    @PostMapping("/api/admin/upload-book")
    public void uploadBook(@RequestBody Book book) {
        //LOGGER.info("POST request '/api/admin/upload-book' with {}", book);
        hibParser.saveBook(book);
    }

    @PostMapping("/api/admin/upload-all-books")
    public void uploadAllBooks(@RequestBody List<Book> books) {
        //LOGGER.info("POST request '/api/admin/upload-all-books' with {}", book);
        for (Book book : books) {
            hibParser.saveBook(book);
        }
    }

    @PostMapping("/api/admin/clear-temp-pics")
    public void clearTempPics(@RequestBody String folderList) {
        hibParser.clearTemp(folderList);
    }
}
