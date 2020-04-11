package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.search.BookSearch;
import com.project.model.PageableBookDTO;
import com.project.service.BookService;
import com.project.service.StorageService;
import com.project.util.BookDTOWithFieldsForTable;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
public class BookController {

    private BookService bookService;
    private BookSearch bookSearch;
    private HibParser hibParser;
    private StorageService storageService;

    @PostMapping("/admin/deleteImg")
    public HttpStatus deleteImageByPath(@RequestBody String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpStatus.OK;
    }

    @PutMapping("/loadFile")
    public BookDTO loadFile(@RequestBody String book) {
        BookDTO bookDTO = hibParser.getBookFromJSON(book);
        return bookDTO;
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
    public HttpStatus addNewBook(@RequestBody BookDTO bookDTO) {
        bookService.addBook(bookDTO);
        return HttpStatus.OK;
    }

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
        storageService.cutImagesFromTmpPaperToNewPaperByLastIdBook(lastId, bookDTO.getImageList());
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

    @GetMapping("/user/get20BookDTO/{locale}")
    public List<BookDTO20> getWelcomeLocaleDTOByLocaleSize20(@PathVariable("locale") String locale) {
        List<BookDTO20> page = bookService.get20BookDTO(locale);
        return page;
    }

    @GetMapping("/page/id/{x}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("x") long x) {
        BookDTO bookDTO = bookService.getBookDTOById(x);
        return ResponseEntity.ok(bookDTO);
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
