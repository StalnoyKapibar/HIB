package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
public class BookController {

    private BookService bookService;

    @GetMapping("/admin/pageable/{page1}")
    public Page<BookDTO> getWelcomeLocaleDTOByLocale(@PathVariable("page1") int page1) {
        Pageable pageable0 = PageRequest.of(page1, 10, Sort.by(
                Sort.Order.asc("id")));
        Page<BookDTO> page = bookService.findAll(pageable0);
        return page;
    }

    @GetMapping("/getPageBooks")
    public List<BookDTO> getPageBooks() {
        return bookService.getAllBookDTO();
    }

    @PostMapping("/admin/add")
    public void addBook(@RequestBody BookDTO bookDTO) {
        bookService.addBook(bookDTO);
    }

//    @GetMapping("/getVarBookDTO")
//    @Autowired
//    public List<String> getVarBookDTO(VarBookDTO varBookDTO) {
//        return varBookDTO.getFields();
//    }

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

    @GetMapping("/page/id/{x}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("x") long x ) {
        //  BookDTO bookDTO1 = new BookDTO(x, new LocaleString("Робинзон Крузо", "Robinson Cruso","","","",""), new LocaleString("Даниель Дефо", "Daniel Defo","","","",""));
        //   bookService.addBook(bookDTO1);
        BookDTO bookDTO = bookService.getBookByIdLocale(x);
        return ResponseEntity.ok(bookDTO);
    }


    }