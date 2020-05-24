package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.model.*;
import com.project.search.BookSearch;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.StorageService;
import com.project.util.BookDTOWithFieldsForTable;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    private final BookService bookService;
    private final HibParser hibParser;
    private final StorageService storageService;
    private final static Logger LOGGER = LoggerFactory.getLogger(BookController.class.getName());

    @Autowired
    public BookController(BookService bookService, BookSearch bookSearch,
                          HibParser hibParser, StorageService storageService) {
        this.bookService = bookService;
        this.hibParser = hibParser;
        this.storageService = storageService;
    }

    @PostMapping("/admin/deleteImg")
    public HttpStatus deleteImageByPath(@RequestBody String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    @PutMapping("/loadFile")
    public Book loadFile(@RequestBody String bookAsJson) {
        return hibParser.getBookFromJSON(bookAsJson);
    }

    @PostMapping("/admin/loadImg/{name}")
    public String addNewImage(@PathVariable("name") String name, @RequestBody byte[] file) {
        long id = Long.parseLong(bookService.getLastIdOfBook()) + 1;
        String path = "img/book" + id;
        if (name.equals("avatar")) {
            path += "/" + name + ".jpg";
        } else {
            File dir = new File(path);
            if (dir.exists()) {
                path += "/" + dir.listFiles().length + ".jpg";
            } else {
                path += "/" + "0.jpg";
            }
        }
        try {
            FileUtils.writeByteArrayToFile(new File(path), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @PutMapping("/admin/addBook")
    public HttpStatus addNewBook(@RequestBody Book book) {
        bookService.addBook(book);
        return HttpStatus.OK;
    }

    @GetMapping("/api/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/api/admin/pageable/{page}")
    public BookPageAdminDto getWelcomeLocaleDTOByLocale(@PathVariable int page, @RequestParam boolean disabled) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.asc("id")));
        BookPageAdminDto pageableBookDTO = bookService.getPageBookDTOByPageable(pageable, disabled);
        return pageableBookDTO;
    }

    @GetMapping("/getPageBooks")
    public List<Book> getPageBooks() {
        return bookService.getAllBookDTO();
    }

    @PostMapping("/admin/add")
    public void addBook(@RequestBody Book book) {
        if(book.getCoverImage() == null) {
            book.setCoverImage("book.jpg");
            bookService.addBook(book);
            String lastId = bookService.getLastIdOfBook();
            storageService.createNewPaperForImages(lastId);
            storageService.copyDefaultPhotoToFolder(lastId);
        } else {
            bookService.addBook(book);
            String lastId = bookService.getLastIdOfBook();
            storageService.createNewPaperForImages(lastId);
            storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(lastId, book.getListImage());
        }
    }

    @GetMapping("/getVarBookDTO")
    @Autowired
    public List<String> getVarBookDTOForBuildTableInAdminPanel(BookDTOWithFieldsForTable bookDTOWithFieldsForTable) {
        return bookDTOWithFieldsForTable.getFields();
    }

    @GetMapping("/admin/del/{id}")
    public void delBook(@PathVariable long id) {
        storageService.deletePaperById(id);
        bookService.deleteBookById(id);
    }

    @PostMapping("/admin/edit")
    public void editBook(@RequestBody Book book) {
        bookService.updateBook(book);
        storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(String.valueOf(book.getId()), book.getListImage());
    }

    @GetMapping("/user/get20BookDTO/{locale}")
    public List<BookDTO> getWelcomeLocaleDTOByLocaleSize20(@PathVariable("locale") String locale) {
        List<BookDTO> page = bookService.get20BookDTO(locale);
        return page;
    }

    @GetMapping(value = "/api/book/{id}", params = "locale")
    public BookNewDTO getNewBookDTOByIdAndLang(@PathVariable Long id, @RequestParam("locale") String lang) {
        return bookService.getNewBookDTOByIdAndLang(id, lang);
    }

    @PostMapping("/admin/upload")
    public String fileUpload(@RequestBody MultipartFile file) {
        return storageService.saveImage(file);
    }

    @PostMapping("/admin/download")
    public ResponseEntity<Resource> downloadFile(@RequestBody String nameImageDownloaded) {
        Resource file = storageService.loadAsResource(nameImageDownloaded);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + file.getFilename()).body(file);
    }

    @PostMapping("/admin/deleteImage")
    public void deleteImageByFileName(@RequestBody String nameDeleteImage) {
        storageService.deleteImageByFileName(nameDeleteImage);
    }

    @PostMapping("/admin/deleteImageByEditPage")
    public void deleteImageByFileNameByEditPage(@RequestBody String nameDeleteImageByEditPage) {
        storageService.deleteImageByFileNameByEditPage(nameDeleteImageByEditPage);
    }

    @PostMapping("/admin/deleteImageFromDB")
    public void deleteImageByFromDB(@RequestBody String nameDeleteImageByFromDB) {
        storageService.deleteImageByFromDB(nameDeleteImageByFromDB);
    }

    @PostMapping("/admin/uploadByEditPage")
    public HttpStatus fileUploadByEditPage(@RequestBody MultipartFile file, String idPaperForSaveImages) {
        storageService.saveImageByEditBook(file, idPaperForSaveImages);
        return HttpStatus.OK;
    }

    @GetMapping("/admin/doesFolderTmpExist")
    public void doesFolderTmpExist() {
        if (!storageService.doesFolderTmpExist()) {
            storageService.createTmpFolderForImages();
        }
    }

    @GetMapping(value = "/api/book", params = {"limit", "start"})
    public BookPageDto getBookDtoByLimitAndAmountAndStart(@RequestParam Map<String, String> params) {
        Pageable pageable = PageRequest.of(Integer.parseInt(params.get("start")),
                Integer.parseInt(params.get("limit")), Sort.by(Sort.Order.asc("id")));
        return bookService.getBookPageByPageable(pageable);
    }

    @GetMapping("/api/book/lastOrderedBooks")
    public List<Long> getAllLastOrderedBooks() {
        return bookService.getAllLastOrderedBooks();
    }
}
