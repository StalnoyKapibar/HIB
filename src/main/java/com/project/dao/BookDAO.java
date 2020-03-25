package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.PageableBookDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDAO {
    List<BookDTO> getAllBookDTO();

    void addBook(BookDTO bookDTO);

    void deleteBookById(long id);

    Book getBookById(long id);

    BookDTO getBookByIdLocale(long id);

    void updateBook(BookDTO bookDTO);

    List<BookDTO20> get20BookDTO(String locale);

    BookDTO getBookDTOById(long id);

    String getLastIdOfBook();

    PageableBookDTO getPageBookDTOByPageable(Pageable pageable);

    String getQuantityBook();
}
