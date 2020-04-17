package com.project.dao.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao extends GenericDao<Long, Book> {

    BookDto getBookDTOFromBook(Book book);

    Book getBookFromBookDTO(BookDto bookDTO);

    BookDto getBookByIdLocale(long id);

    List<BookDto20> get20BookDTO(String locale);

    BookDto20 getBookBySearchRequest(LocaleString localeString, String locale);

    BookDto getBookDTOById(long id);

    String getLastIdOfBook();

    PageableBookDto getPageBookDTOByPageable(Pageable pageable);

    String getQuantityBook();

    BookNewDto getNewBookDTObyIdAndLang(Long id, String lang);
}
