package com.project.controller;

import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.service.BookService;
import com.project.service.StorageService;
import com.project.util.VarBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private StorageService storageService;

    @GetMapping("/getBookDTOById/{id}")
    public BookDTO getBookDTOById(@PathVariable("id") long id){
        BookDTO bookDTO = bookService.getBookDTOById(id);
        return bookDTO;
    }

    @GetMapping("/admin/pageable/{page1}")
    public Page<BookDTO> getWelcomeLocaleDTOByLocale(@PathVariable("page1") int page1) {
        Pageable pageable0 = PageRequest.of(page1, 10, Sort.by(
                Sort.Order.asc("id")));
        Page<BookDTO> page = bookService.getPageBookDTOByPageable(pageable0);
        return page;
    }

    @GetMapping("/getPageBooks")
    public List<BookDTO> getPageBooks() {
        return bookService.getAllBookDTO();
    }

    @PostMapping("/admin/add")
    public void addBook(@RequestBody BookDTO bookDTO) {
        bookService.addBook(bookDTO);
        String lastId = bookService.getLastIdOfBook();
        storageService.createNewPaperForImages(lastId);
        storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(lastId);
    }

    @GetMapping("/getVarBookDTO")
    @Autowired
    public List<String> getVarBookDTO(VarBookDTO varBookDTO) {
        return varBookDTO.getFields();
    }

    @GetMapping("/admin/del/{x}")
    public void delBook(@PathVariable("x") long x) {
        bookService.deleteBookById(x);
    }

    @PostMapping("/admin/edit")
    public void editBook(@RequestBody BookDTO bookDTO) {
        bookService.updateBook(bookDTO);
    }

    @GetMapping("/admin/get20BookDTO/{locale}")
    public List<BookDTO20> getWelcomeLocaleDTOByLocaleSize20(@PathVariable("locale") String locale) {
        List<BookDTO20> page = bookService.get20BookDTO(locale);
        return page;
    }

    @PostMapping("/admin/upload")
    public HttpStatus fileUpload(@RequestBody MultipartFile file) {
        storageService.saveImage(file);
        return HttpStatus.OK;
    }

    @PostMapping("/admin/download")
    public ResponseEntity<Resource> downloadFile (@RequestBody String x){
        Resource file = storageService.loadAsResource(x);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + file.getFilename()).body(file);
    }

    @PostMapping("/admin/deleteImage")
    public void deleteImageByFileName(@RequestBody String x) {
        storageService.deleteImageByFileName(x);
    }

    @PostMapping("/admin/deleteImageByEditPage")
    public void deleteImageByFileNameByEditPage(@RequestBody String x) {
        storageService.deleteImageByFileNameByEditPage(x);
    }

    @PostMapping("/admin/uploadByEditPage")
    public HttpStatus fileUploadByEditPage(@RequestBody MultipartFile file, String x) {
        storageService.saveImageByEditBook(file, x);
        return HttpStatus.OK;
    }
}
