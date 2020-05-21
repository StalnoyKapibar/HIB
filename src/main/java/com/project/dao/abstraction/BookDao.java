package com.project.dao.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao extends GenericDao<Long, Book> {

    List<BookDTO> get20BookDTO(String locale);

    BookNewDTO getBookBySearchRequestAdvanced(OriginalLanguage originalLanguage);

    BookNewDTO getBookBySearchRequest(OriginalLanguage originalLanguage, boolean isShow);

    BookNewDTO getBookBySearchRequest(String name, String translitName, OriginalLanguage originalLanguage, Long priceFrom, Long priceTo, String yearOfEdition, Long pages, String searchBy, List<String> category);

    List<BookNewDTO> getBooksBySearchParameters(Long priceFrom, Long priceTo, String yearOfEdition, Long pages, List<String> category);

    String getLastIdOfBook();

    BookPageAdminDto getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    String getQuantityBook();

    BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang);

    List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang);

    Long getCountBooksByCategoryId(Long categoryId);

    BookPageDto getBookPageByPageable(Pageable pageable);

    List<BookNewDTO> getAllBooksSearchPage();
}
