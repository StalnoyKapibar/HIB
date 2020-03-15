package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.BookNewDTO;
import com.project.model.LocaleString;
import com.project.service.BookService;
import lombok.AllArgsConstructor;
import lombok.var;
import org.apache.commons.io.IOUtils;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
       //  BookDTO bookDTO1 = new BookDTO(x, new LocaleString("Робинзон Крузо", "Robinson Cruso","","","","",""), new LocaleString("Даниель Дефо", "Daniel Defo","","","","", ""));
        // bookService.addBook(bookDTO1);
        BookDTO bookDTO = bookService.getBookByIdLocale(x);
        return ResponseEntity.ok(bookDTO);
    }

    @RequestMapping(value = "/sid/id/{x}", method = RequestMethod.GET)
    public ResponseEntity<List<byte[]>> getImage(HttpServletResponse response, @PathVariable("x") long x) throws IOException {

        List<byte[]> resources = new ArrayList<>();
        //var imgFile = new ClassPathResource("static/images/book_example.jpg");
       InputStream resourceFile = loadAsResource("qwe.jpg", x).getInputStream();

       // response.setContentType(MediaType.IMAGE_JPEG_VALUE);
      //  StreamUtils.copy(resourceFile.getInputStream(), out.getOutputStream());
       resources.add(IOUtils.toByteArray(resourceFile));
      //  resources.add(IOUtils.toByteArray(resourceFile));

      //  resources.add("sdfs");
      //  resources.add("Sdfasdf");
        return ResponseEntity.ok(resources);
    }



    public Resource loadAsResource(String filename, long x) {
            Resource resource=null;
        final Path path = Paths.get("img/"+x+"/");
        try {
            Path file = path.resolve(filename);
            file.toFile();
             resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;

            }

        } catch (MalformedURLException e) {

        }
       return resource;
    }

    }