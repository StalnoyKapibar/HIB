package com.project.dao.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao extends GenericDao<Long, Book> {

    BookNewDTO getNewBookDTOInOtherLangByIdAndLang(Long id, String lang);

    BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale);

    String getLastIdOfBook();

    PageableBookDTO getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    String getQuantityBook();

    String getBookOriginalLanguageById(Long id);

    BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang);

    List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang);
}
