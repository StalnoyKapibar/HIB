package com.project.service.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Book> getAllBookDTO();

    Book getById(Long id);

    void addBook(Book book);

    PageableBookDTO getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    void deleteBookById(Long id);

    void updateBook(Book book);

    List<BookDTO20> get20BookDTO(String locale);

    BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale);

    BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale, Long priceFrom, Long priceTo, String yearOfEdition, Long pages);

    List<BookDTO20> getBooksBySearchParameters(String locale, Long priceFrom, Long priceTo, String yearOfEdition, Long pages);

    Book getBookById(Long id);

    List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang);

    String getLastIdOfBook();

    BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang);
}
