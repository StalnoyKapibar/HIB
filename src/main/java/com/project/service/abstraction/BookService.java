package com.project.service.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Book> getAllBookDTO();

    Book getById(Long id);

    void addBook(Book book);

    BookPageAdminDto getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    void deleteBookById(Long id);

    void updateBook(Book book);

    List<BookDTO> get20BookDTO(String locale);

    BookDTO getBookBySearchRequest(LocaleString localeString, String locale);

    BookNewDTO getBookBySearchRequest(OriginalLanguage originalLanguage, boolean isShow);

    BookNewDTO getBookBySearchRequest(String req, OriginalLanguage originalLanguage, Long priceFrom, Long priceTo, String yearOfEdition, Long pages, String searchBy, String category);

    List<BookNewDTO> getBooksBySearchParameters(Long priceFrom, Long priceTo, String yearOfEdition, Long pages, String searchBy, String category);

    Book getBookById(Long id);

    List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang);

    String getLastIdOfBook();

    BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang);

    BookPageDto getBookPageByPageable(Pageable pageable);
}
