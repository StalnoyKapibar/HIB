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
    public Book getById(Long id) {
        return bookDAO.findById(id);
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        bookDAO.add(bookDAO.getBookFromBookDTO(bookDTO));
    }

    @Override
    public PageableBookDTO getPageBookDTOByPageable(Pageable pageable, boolean disabled) {
        return bookDAO.getPageBookDTOByPageable(pageable, disabled);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDAO.deleteById(id);
    }

    @Override
    public BookDTO getBookByIdLocale(Long id) {
        return bookDAO.getBookByIdLocale(id);
    }

    @Override
    public BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang) {
        return bookDAO.getNewBookDTObyIdAndLang(id, lang);
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        Book book = bookDAO.findById(bookDTO.getId());
        book.setCoverImage(bookDTO.getCoverImage());
        book.setListImage(bookDTO.getImageList());
        book.setNameLocale(bookDTO.getName());
        book.setAuthorLocale(bookDTO.getAuthor());
        book.setDesc(bookDTO.getDesc());
        book.setEdition(bookDTO.getEdition());
        bookDAO.update(book);
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
    public BookDTO getBookDTOById(Long id) {
        return bookDAO.getBookDTOById(id);
    }

    @Override
    public String getLastIdOfBook() {
        return bookDAO.getLastIdOfBook();
    }
}
