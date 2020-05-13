package com.project.service;

import com.project.dao.abstraction.BookDao;
import com.project.model.*;
import com.project.service.abstraction.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDAO;

    @Override
    public List<Book> getAllBookDTO() {
        return bookDAO.findAll();
    }

    @Override
    public Book getById(Long id) {
        return bookDAO.findById(id);
    }

    @Override
    public void addBook(Book book) {
        bookDAO.add(book);
    }

    @Override
    public BookPageAdminDto getPageBookDTOByPageable(Pageable pageable, boolean disabled) {
        return bookDAO.getPageBookDTOByPageable(pageable, disabled);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDAO.deleteById(id);
    }

    @Override
    public BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang) {
        return bookDAO.getNewBookDTObyIdAndLang(id, lang);
    }

    @Override
    public BookPageDto getBookPageByPageable(Pageable pageable) {
        return bookDAO.getBookPageByPageable(pageable);
    }

    @Override
    public void updateBook(Book book) {
        bookDAO.update(book);
    }

    @Override
    public List<BookDTO> get20BookDTO(String locale) {
        return bookDAO.get20BookDTO(locale);
    }

    @Override
    public BookDTO getBookBySearchRequest(LocaleString localeString, String locale) {
        return bookDAO.getBookBySearchRequest(localeString, locale);
    }

    @Override
    public BookNewDTO getBookBySearchRequest(OriginalLanguage originalLanguage, boolean isShow) {
        return bookDAO.getBookBySearchRequest(originalLanguage,isShow);
    }

    @Override
    public BookNewDTO getBookBySearchRequest(String req, OriginalLanguage originalLanguage, Long priceFrom, Long priceTo, String yearOfEdition, Long pages, String searchBy, String category) {
        return bookDAO.getBookBySearchRequest(req, originalLanguage, priceFrom, priceTo, yearOfEdition, pages, searchBy, category);
    }

    @Override
    public List<BookNewDTO> getBooksBySearchParameters(Long priceFrom, Long priceTo, String yearOfEdition, Long pages, String searchBy, String category) {
        return bookDAO.getBooksBySearchParameters(priceFrom, priceTo, yearOfEdition, pages, searchBy, category);
    }

    @Override
    public Book getBookById(Long id) {
        return bookDAO.findById(id);
    }

    @Override
    public List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang) {
        return bookDAO.getBooksByCategoryId(categoryId, lang);
    }

    @Override
    public String getLastIdOfBook() {
        return bookDAO.getLastIdOfBook();
    }
}
