package com.project.dao;

import com.project.dao.GenericDAO.AbstractDAO;
import com.project.dao.GenericDAO.IGenericDao;
import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDAO extends IGenericDao<Long, Book> {

    BookDTO getBookDTOFromBook(Book book);

    Book getBookFromBookDTO(BookDTO bookDTO);

    BookDTO getBookByIdLocale(long id);

    List<BookDTO20> get20BookDTO(String locale);

    BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale);

    BookDTO getBookDTOById(long id);

    String getLastIdOfBook();

    PageableBookDTO getPageBookDTOByPageable(Pageable pageable);

    String getQuantityBook();

    BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang);
}
