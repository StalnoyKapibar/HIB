package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.model.BookDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoadHibFilesRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadHibFilesRestController.class.getName());
    private final HibParser hibParser;

    @PostMapping("/api/admin/upload-file")
    public BookDTO uploadFile(@RequestBody String book) {
        LOGGER.info("POST request '/api/upload-file' with {}", book);
        return hibParser.getBookFromJSON(book);
    }

    @PostMapping("/api/admin/upload-multiply-files")
    public void loadFile(@RequestParam("files") MultipartFile[] books, HttpServletResponse response) {
        LOGGER.info("POST request '/api/load-multiply-files' with {}", Arrays.toString(books));
        List<String> booksAsJsonList = new ArrayList<>();
        for (MultipartFile file : books) {
            try {
                booksAsJsonList.add(new String(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        hibParser.saveBooks(booksAsJsonList);

        try {
            response.sendRedirect("/admin/panel/books");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
