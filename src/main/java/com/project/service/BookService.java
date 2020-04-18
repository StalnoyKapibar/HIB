package com.project.service;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBookDTO();

    Book getById(Long id);

    void addBook(BookDTO bookDTO);

    PageableBookDTO getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    void deleteBookById(Long id);

    void updateBook(BookDTO bookDTO);

    List<BookDTO20> get20BookDTO(String locale);

    BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale);

    @Deprecated
    BookDTO getBookDTOById(Long id);

    String getLastIdOfBook();

    BookDTO getBookByIdLocale(Long x);

    BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang);
}
