package com.project.dao;

import com.project.model.*;
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

    BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale);

    BookDTO getBookDTOById(long id);

    String getLastIdOfBook();

    PageableBookDTO getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    String getQuantityBook();

    BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang);
}
