package com.project.controller;

import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.PageableBookDTO;
import com.project.service.BookService;
import com.project.service.StorageService;

import com.project.util.BookDTOWithFieldsForTable;
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
    public BookDTO getBookDTOById(@PathVariable("id") long id) {
        BookDTO bookDTO = bookService.getBookDTOById(id);
        return bookDTO;
    }

    @GetMapping("/admin/pageable/{page1}")
    public PageableBookDTO getWelcomeLocaleDTOByLocale(@PathVariable("page1") int page1) {
        Pageable pageable0 = PageRequest.of(page1, 10, Sort.by(
                Sort.Order.asc("id")));
        PageableBookDTO pageableBookDTO = bookService.getPageBookDTOByPageable(pageable0);
        return pageableBookDTO;
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
    public List<String> getVarBookDTOForBuildTableInAdminPanel(BookDTOWithFieldsForTable bookDTOWithFieldsForTable) {
        return bookDTOWithFieldsForTable.getFields();
    }

    @GetMapping("/admin/del/{idForDeleteBook}")
    public void delBook(@PathVariable("idForDeleteBook") long idForDeleteBook) {
        storageService.deletePaperById(idForDeleteBook);
        bookService.deleteBookById(idForDeleteBook);
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
