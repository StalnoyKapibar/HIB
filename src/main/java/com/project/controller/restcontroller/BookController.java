package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.model.*;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.FeedbackRequestService;
import com.project.service.abstraction.OrderService;
import com.project.service.abstraction.StorageService;
import com.project.util.BookDTOWithFieldsForTable;
import org.apache.commons.io.FileUtils;
import org.infinispan.partitionhandling.impl.LostDataCheck;
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
import java.nio.file.*;
import java.util.*;

@RestController
public class BookController {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookController.class.getName());
    private final BookService bookService;
    private final HibParser hibParser;
    private final StorageService storageService;
    private final FeedbackRequestService feedbackRequestService;
    private final OrderService orderService;

    @Autowired
    public BookController(BookService bookService, HibParser hibParser, StorageService storageService, FeedbackRequestService feedbackRequestService, OrderService orderService) {
        this.bookService = bookService;
        this.hibParser = hibParser;
        this.storageService = storageService;
        this.feedbackRequestService = feedbackRequestService;
        this.orderService = orderService;
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

    @PostMapping("/admin/deleteTmpCover")
    public void deleteCover(@RequestBody String path) {
        File cover = new File("img/tmp/" + path);
        cover.delete();
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
    public void addBook(@RequestBody Book book, @RequestParam(name = "pics") String picsFolderName) {
        if (book.getCoverImage() == null) {
            book.setCoverImage("");
            bookService.addBook(book);
            String lastId = bookService.getLastIdOfBook();
            storageService.createNewPaperForImages(lastId);
            storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(lastId, picsFolderName, book.getListImage());
        } else {
            bookService.addBook(book);
            String lastId = bookService.getLastIdOfBook();
            storageService.createNewPaperForImages(lastId);
            storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(lastId, picsFolderName, book.getListImage());
        }
    }

    @PostMapping("admin/addNoPics")
    public void addBookNoPics(@RequestBody Book book) {
        book.setCoverImage("");
        bookService.addBook(book);
        String lastId = bookService.getLastIdOfBook();
        storageService.createNewPaperForImages(lastId);
    }

    @GetMapping("/getVarBookDTO")
    @Autowired
    public List<String> getVarBookDTOForBuildTableInAdminPanel(BookDTOWithFieldsForTable bookDTOWithFieldsForTable) {
        return bookDTOWithFieldsForTable.getFields();
    }

    @GetMapping("/admin/del/{id}")
    public void delBook(@PathVariable long id) {
        int feedbackCount = feedbackRequestService.findAllUnreadRequestsByBookId(id).size();
        int orderCount = orderService.findAllUncompletedOrdersByBookId(id).size();
        if (feedbackCount == 0 && orderCount == 0) {
            storageService.deletePaperById(id);
            feedbackRequestService.deleteFeedbackRequestByIbBook(id);
            bookService.deleteBook(id);
        }
    }

    @PostMapping("/admin/edit")
    public void editBook(@RequestBody Book book, @RequestParam("pics") String picsFolderName) {
        bookService.updateBook(book);
        storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(String.valueOf(book.getId()), picsFolderName, book.getListImage());
    }

    @PostMapping("/admin/editNoPics")
    public void editBookNoPics(@RequestBody Book book) {
        bookService.updateBook(book);
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

    @GetMapping("/getPicsFolderName")
    public String getPicsFolderName() {
        return hibParser.getTmpFolderName();
    }

    @PostMapping("/admin/upload")
    public String fileUpload(@RequestBody MultipartFile file,  @RequestParam("pics") String picsFolderName) {
        return storageService.saveImage(file, picsFolderName);
    }

    @PostMapping("/admin/uploadMulti")
    public void fileUpload(@RequestPart("files") MultipartFile[] files,  @RequestParam("pics") String picsFolderName) {
        for (MultipartFile file : files) {
            storageService.saveImage(file, picsFolderName);
        }
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
    public void deleteImageByFromDB(@RequestBody Long id) {
        storageService.deleteImageByFromDB(id);
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

    @GetMapping(value = "/api/book", params = {"limit", "start", "locale"})
    public BookPageDto getBookDtoByLimitAndAmountAndStart(@RequestParam Map<String, String> params) {
        Pageable pageable = PageRequest.of(Integer.parseInt(params.get("start")),
                Integer.parseInt(params.get("limit")), Sort.by(Sort.Order.desc("id")));
        return bookService.getBookPageByPageable(pageable, params.get("locale"));
    }

    @GetMapping("/api/book/lastOrderedBooks")
    public List<Long> getAllLastOrderedBooks() {
        return bookService.getAllLastOrderedBooks();
    }

    @GetMapping("/api/book/booksAvailability")
    public List<Long> getAllAvailableBooks() {
        return bookService.getAllAvailableBooks();
    }


    @GetMapping(value = "/api/allBookForLiveSearch")
    public List<BookNewDTO> getAllLightBookDtoForSearch() {
        return bookService.getAllLightBookDtoForSearch();
    }

    @GetMapping("/api/admin/book-count")
    public Long getTotalBooks() {
        return bookService.getSizeOfTotalBooks();
    }
}
