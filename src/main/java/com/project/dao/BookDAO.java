package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDAO {
    List<BookDTO> getAllBookDTO();
    void addBook(BookDTO bookDTO);
    void delBookById(long id);
    Book getUserById(long id);
    void updateBook(BookDTO bookDTO);
}
