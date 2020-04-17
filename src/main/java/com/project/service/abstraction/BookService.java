package com.project.service.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBookDTO();

    void addBook(BookDto bookDTO);

    PageableBookDto getPageBookDTOByPageable(Pageable pageable);

    void deleteBookById(long id);

    void updateBook(BookDto bookDTO);

    List<BookDto20> get20BookDTO(String locale);

    BookDto20 getBookBySearchRequest(LocaleString localeString, String locale);

    BookDto getBookDTOById(long id);

    String getLastIdOfBook();

    BookDto getBookByIdLocale(long x);

    BookNewDto getNewBookDTOByIdAndLang(Long id, String lang);
}
