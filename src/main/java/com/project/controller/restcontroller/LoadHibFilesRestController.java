package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.model.BookDTO;
import com.project.model.HibFileDto;
import com.project.service.abstraction.HibFileService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoadHibFilesRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadHibFilesRestController.class.getName());
    private final HibParser hibParser;
    private final HibFileService hibFileService;

    @PostMapping("/api/admin/upload-file")
    public BookDTO uploadFile(@RequestBody String book) {
        LOGGER.info("POST request '/api/upload-file' with {}", book);
        return hibParser.getBookFromJSON(book);
    }

    @GetMapping("/api/admin/hib-dto")
    public List<HibFileDto> getAllHibFileDto() {
        return hibFileService.getAllDto();
    }

    @GetMapping(value = "/api/admin/hib", params = "name")
    public BookDTO getBookDtoFromHibFileByName(@RequestParam String name) {
        return hibFileService.getBookDtoFromHibFileByName(name);
    }

    @PostMapping("/api/admin/upload-multiply-files")
    public void loadFile(@RequestParam("files") MultipartFile[] books, HttpServletResponse response) {
        LOGGER.info("POST request '/api/load-multiply-files' with {}", Arrays.toString(books));
        hibFileService.bulkLoading(books);
        try {
            response.sendRedirect("/admin/panel/books");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping(value = "/api/admin/hib/{name}")
    public void deleteHibFileByName(@PathVariable String name) {
        hibFileService.deleteByName(name);
    }

}
