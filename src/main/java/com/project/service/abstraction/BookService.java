package com.project.service.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBookDTO();

    void addBook(BookDTO bookDTO);

    PageableBookDTO getPageBookDTOByPageable(Pageable pageable);

    void deleteBookById(long id);

    void updateBook(BookDTO bookDTO);

    List<BookDTO20> get20BookDTO(String locale);

    BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale);

    BookDTO getBookDTOById(long id);

    String getLastIdOfBook();

    BookDTO getBookByIdLocale(long x);

    BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang);
}
