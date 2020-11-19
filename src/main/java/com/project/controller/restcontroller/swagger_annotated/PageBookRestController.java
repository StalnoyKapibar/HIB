package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.Book;
import com.project.service.abstraction.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "REST-API документ для отображения страницы книги")
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("api/page/{id}")
public class PageBookRestController {
    BookService bookService;

    @ApiOperation(value = "Получить книгу для отображения страницы книги"
            , response = Book.class
            , tags = "getBookById")
//    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping()
    public ResponseEntity<Book> getBook(@ApiParam(value = " id книги", required = true)@PathVariable("id") long id){
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
