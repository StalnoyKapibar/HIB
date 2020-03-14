package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.BookNewDTO;
import com.project.service.BookService;
import lombok.AllArgsConstructor;
import lombok.var;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@AllArgsConstructor
@RestController
public class BookController {

    private BookService bookService;

    @GetMapping("/admin/pageable/{page1}")
    public Page<BookNewDTO> getWelcomeLocaleDTOByLocale(@PathVariable("page1") int page1) {
        Pageable pageable0 = PageRequest.of(page1, 10, Sort.by(
                Sort.Order.asc("id")));
        Page<BookNewDTO> page = bookService.findAll(pageable0);
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

    @RequestMapping(value = "/sid", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public void getImage(HttpServletResponse response) throws IOException {

        var imgFile = new Resource("img/tmp/qwe.jpg");

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }



    }