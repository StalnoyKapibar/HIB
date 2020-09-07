package com.project.dao.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao extends GenericDao<Long, Book> {

    List<BookDTO> get20BookDTO(String locale);

    long getQuantityBooksBySearchRequest(String request, Long priceFrom, Long priceTo,
                                         Long yearOfEditionFrom, Long yearOfEditionTo,
                                         Long pagesFrom, Long pagesTo, String searchBy, List<Long> categories, boolean isShow);

    List<BookNewDTO> getBookBySearchRequest(String req, boolean isShow);

    BookSearchPageDTO getBookBySearchRequest(String request, Long priceFrom, Long priceTo,
                                             Long yearOfEditionFrom, Long yearOfEditionTo, Long pagesFrom,
                                             Long pagesTo, String searchBy, List<Long> categories, Pageable pageable, boolean isShow);


    String getLastIdOfBook();

    BookPageAdminDto getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    String getQuantityBook();

    BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang);

    List<BookNewDTO> getBooksByCategoryId(Long categoryId);

    List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang);

    Long getCountBooksByCategoryId(Long categoryId, boolean isShow);

    BookPageDto getBookPageByPageable(Pageable pageable, String lang);

    List<BookNewDTO> getAllBooksSearchPage();

    List<Long> getAllLastOrderedBooks();

    void setLastOrderedBooks(List<Long> list);

    List<Long> getAllAvailableBooks();

    List<BookNewDTO> getAllLightBookDtoForSearch();

    Long getSizeOfTotalBooks();

    void deleteBook(Long id);

    List<Book> getAllBooksByCategoryId(Long id);
}
