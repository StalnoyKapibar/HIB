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
    public List<BookDTO> getAllBookDTO() {
        return bookDAO.findAll().stream().map(book -> bookDAO.getBookDTOFromBook(book)).collect(Collectors.toList());
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        bookDAO.add(bookDAO.getBookFromBookDTO(bookDTO));
    }

    @Override
    public PageableBookDTO getPageBookDTOByPageable(Pageable pageable) {
        return bookDAO.getPageBookDTOByPageable(pageable);
    }

    @Override
    public void deleteBookById(long id) {
        bookDAO.deleteById(id);
    }

    @Override
    public BookDTO getBookByIdLocale(long id) {
        return bookDAO.getBookByIdLocale(id);
    }

    @Override
    public BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang) {
        return bookDAO.getNewBookDTObyIdAndLang(id, lang);
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        bookDAO.update(bookDAO.getBookFromBookDTO(bookDTO));
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        return bookDAO.get20BookDTO(locale);
    }

    @Override
    public BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale) {
        return bookDAO.getBookBySearchRequest(localeString, locale);
    }

    @Override
    public BookDTO getBookDTOById(long id) {
        return bookDAO.getBookDTOById(id);
    }

    @Override
    public String getLastIdOfBook() {
        return bookDAO.getLastIdOfBook();
    }
}
