package com.project.service;

import com.project.dao.abstraction.BookDao;
import com.project.model.*;
import com.project.service.abstraction.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDAO;

    @Override
    public List<BookDto> getAllBookDTO() {
        return bookDAO.findAll().stream().map(book -> bookDAO.getBookDTOFromBook(book)).collect(Collectors.toList());
    }

    @Override
    public void addBook(BookDto bookDTO) {
        bookDAO.add(bookDAO.getBookFromBookDTO(bookDTO));
    }

    @Override
    public PageableBookDto getPageBookDTOByPageable(Pageable pageable) {
        return bookDAO.getPageBookDTOByPageable(pageable);
    }

    @Override
    public void deleteBookById(long id) {
        bookDAO.deleteById(id);
    }

    @Override
    public BookDto getBookByIdLocale(long id) {
        return bookDAO.getBookByIdLocale(id);
    }

    @Override
    public BookNewDto getNewBookDTOByIdAndLang(Long id, String lang) {
        return bookDAO.getNewBookDTObyIdAndLang(id, lang);
    }

    @Override
    public void updateBook(BookDto bookDTO) {
        bookDAO.update(bookDAO.getBookFromBookDTO(bookDTO));
    }

    @Override
    public List<BookDto20> get20BookDTO(String locale) {
        return bookDAO.get20BookDTO(locale);
    }

    @Override
    public BookDto20 getBookBySearchRequest(LocaleString localeString, String locale) {
        return bookDAO.getBookBySearchRequest(localeString, locale);
    }

    @Override
    public BookDto getBookDTOById(long id) {
        return bookDAO.getBookDTOById(id);
    }

    @Override
    public String getLastIdOfBook() {
        return bookDAO.getLastIdOfBook();
    }
}
