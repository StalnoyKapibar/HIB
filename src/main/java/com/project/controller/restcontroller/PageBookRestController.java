package com.project.controller.restcontroller;

import com.project.model.Book;
import com.project.service.abstraction.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Swagger2GetBookForPageBook")
@RestController
@AllArgsConstructor
@RequestMapping("/page/{id}")
public class PageBookRestController {
    BookService bookService;

    @ApiOperation(value = "Get book for book-page", response = Book.class, tags = "getBookById")
    @GetMapping()
    public ResponseEntity<Book> getBook(@ApiParam(value = " ID of the required book", required = true)@PathVariable("id") long id){
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
