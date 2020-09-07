package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.dao.HibFileDaoImpl;
import com.project.dao.abstraction.HibFileDao;
import com.project.model.Book;
import com.project.model.HibFileDto;
import com.project.model.LocaleString;
import com.project.model.OriginalLanguage;
import com.project.service.abstraction.HibFileService;
import com.project.util.TransliterateUtil;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoadHibFilesRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadHibFilesRestController.class.getName());
    private final HibFileService hibFileService;
    private final TransliterateUtil transliterateUtil;
    private final HibParser hibParser;

    @PostMapping("/api/admin/upload-new-hibs")
    public void uploadNewHIBs(@RequestPart("hib") MultipartFile[] jsons) throws IOException {
        //LOGGER.info("POST request '/api/admin/get-book-from-json' with {}", book);
        hibFileService.bulkLoading(jsons);
    }

    @GetMapping("/api/admin/get-all-existing-hibs")
    public List<Book> getAllExistingHIBs() {
        return hibFileService.getAllBooks();
    }

    @GetMapping("/api/admin/delete-HIB-file")
    public void deleteHIBFile(@RequestParam("name") String hibName) {
        hibFileService.deleteByName(hibName);
    }

    @PostMapping("/api/admin/get-original-lang-for-hib")
    public OriginalLanguage getOrigLangForHIB(@RequestBody Book book) {
        OriginalLanguage originalLanguage = new OriginalLanguage();
        Method getStringInOriginalLanguageName;
        if (book.getOriginalLanguageName() == null) book.setOriginalLanguageName("OTHER");
        if (!book.getOriginalLanguageName().equalsIgnoreCase("OTHER")) {
            try {
                String bookName, bookAuthor, bookEdition;
                getStringInOriginalLanguageName = LocaleString.class.getMethod("get" + book.getOriginalLanguageName().charAt(0)
                        + Character.toLowerCase(book.getOriginalLanguageName().charAt(1)));
                bookName = (String) getStringInOriginalLanguageName.invoke(book.getName());
                bookAuthor = (String) getStringInOriginalLanguageName.invoke(book.getAuthor());
                bookEdition = (String) getStringInOriginalLanguageName.invoke(book.getEdition());
                originalLanguage.setName(bookName);
                originalLanguage.setAuthor(bookAuthor);
                originalLanguage.setEdition(bookEdition);
                originalLanguage.setNameTranslit(transliterateUtil.transliterate(bookName));
                originalLanguage.setAuthorTranslit(transliterateUtil.transliterate(bookAuthor));
                originalLanguage.setEditionTranslit(transliterateUtil.transliterate(bookEdition));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.error("Reflection error occurred", e);
            }
        }
        return originalLanguage;
    }

    @PostMapping("/api/admin/upload-book")
    public void uploadBook(@RequestBody Book book) {
        //LOGGER.info("POST request '/api/admin/upload-book' with {}", book);
        hibParser.saveBook(book);
    }

    @GetMapping("/api/bookFromHib")
    public Book getBookFromHIBName(@RequestParam("name") String hibName) {
        return hibFileService.getBookFromHibFileByName(hibName);
    }

    @PostMapping("/api/admin/upload-all-books")
    public void uploadAllBooks(@RequestBody List<Book> books) {
        //LOGGER.info("POST request '/api/admin/upload-all-books' with {}", books);
        for (Book book : books) {
            hibParser.saveBook(book);
        }
        hibFileService.deleteAllHIBs();
    }

    @PostMapping("/api/admin/clear-temp-pics")
    public void clearTempPics(@RequestBody String folder) {
        //LOGGER.info("POST request '/api/admin/clear-temp-pics' with {}", folder);
        hibParser.clearTemp(folder);
    }

    @PostMapping("/api/admin/clear-left-temp-pics")
    public void clearLeftTempPics(@RequestBody String[] folders) {
        //LOGGER.info("POST request '/api/admin/clear-left-temp-pics' with {}", folders);
        for (String folder : folders){
            hibParser.clearTemp(folder);
        }
    }
}
