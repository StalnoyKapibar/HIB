package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.model.Book;
import com.project.model.BookDTO20;
import com.project.model.BookNewDTO;
import com.project.model.PageableBookDTO;
import com.project.search.BookSearch;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.StorageService;
import com.project.util.BookDTOWithFieldsForTable;
import org.apache.commons.io.FileUtils;
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
import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;
    private final BookSearch bookSearch;
    private final HibParser hibParser;
    private final StorageService storageService;

    @Autowired
    public BookController(BookService bookService, BookSearch bookSearch,
                          HibParser hibParser, StorageService storageService) {
        this.bookService = bookService;
        this.bookSearch = bookSearch;
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
    public PageableBookDTO getWelcomeLocaleDTOByLocale(@PathVariable int page, @RequestParam boolean disabled) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.asc("id")));
        PageableBookDTO pageableBookDTO = bookService.getPageBookDTOByPageable(pageable, disabled);
        return pageableBookDTO;
    }

    @GetMapping("/getPageBooks")
    public List<Book> getPageBooks() {
        return bookService.getAllBookDTO();
    }

    @PostMapping("/admin/add")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
        String lastId = bookService.getLastIdOfBook();
        storageService.createNewPaperForImages(lastId);
        storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(lastId, book.getListImage());
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
    }

    @GetMapping("/user/get20BookDTO/{locale}")
    public List<BookDTO20> getWelcomeLocaleDTOByLocaleSize20(@PathVariable("locale") String locale) {
        List<BookDTO20> page = bookService.get20BookDTO(locale);
        return page;
    }

    @GetMapping(value = "/api/book/{id}", params = "locale")
    public BookNewDTO getNewBookDTOByIdAndLang(@PathVariable Long id, @RequestParam("locale") String lang) {
        return bookService.getNewBookDTOByIdAndLang(id, lang);
    }

    @GetMapping("/searchResult")
    public List<BookDTO20> search(@RequestParam(value = "request") String req, @RequestParam(value = "LANG") String locale) {
        return bookSearch.search(req, locale);
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
}
