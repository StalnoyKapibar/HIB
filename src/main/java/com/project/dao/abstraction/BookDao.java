package com.project.dao.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao extends GenericDao<Long, Book> {

    List<BookDTO> get20BookDTO(String locale);

    long getQuantityBooksBySearchRequest(String request, Long priceFrom, Long priceTo,
                                         String yearOfEditionFrom, String yearOfEditionTo, Long pagesFrom, Long pagesTo, String searchBy, List<Long> categories);

    List<BookNewDTO> getBookBySearchRequest(String req, boolean isShow);

    BookSearchPageDTO getBookBySearchRequest(String request, Long priceFrom, Long priceTo,
                                             String yearOfEditionFrom, String yearOfEditionTo, Long pagesFrom, Long pagesTo, String searchBy, List<Long> categories, Pageable pageable);


    String getLastIdOfBook();

    BookPageAdminDto getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    String getQuantityBook();

    BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang);

    List<BookNewDTO> getBooksByCategoryId(Long categoryId);

    List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang);

    Long getCountBooksByCategoryId(Long categoryId);

    BookPageDto getBookPageByPageable(Pageable pageable);

    List<BookNewDTO> getAllBooksSearchPage();

    List<Long> getAllLastOrderedBooks();

    void setLastOrderedBooks(List<Long> list);

    List<BookNewDTO> getAllLightBookDtoForSearch();

    Long getSizeOfTotalBooks();

}
