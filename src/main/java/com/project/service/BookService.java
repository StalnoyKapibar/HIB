package com.project.service;

import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBookDTO();
    void addBook(BookDTO bookDTO);
    Page<BookDTO> findAll(Pageable pageable);
    void deleteBookById(long id);
    void updateBook(BookDTO bookDTO);
    List<BookDTO20> get20BookDTO(String locale);
    BookDTO getBookByIdLocale(long id);
}
