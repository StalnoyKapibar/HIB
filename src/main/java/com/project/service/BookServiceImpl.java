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
        book.setLastBookOrdered(false);
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
    public BookPageDto getBookPageByPageable(Pageable pageable, String lang) {
        return bookDAO.getBookPageByPageable(pageable, lang);
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
    public Book getBookById(Long id) {
        return bookDAO.findById(id);
    }

    @Override
    public List<BookNewDTO> getBooksByCategoryId(Long categoryId) {
        return bookDAO.getBooksByCategoryId(categoryId);
    }

    @Override
    public List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang) {
        return bookDAO.getBooksByCategoryId(categoryId, lang);
    }
    @Override
    public Long getCountBooksByCategoryId(Long categoryId, boolean isShow) {
        return bookDAO.getCountBooksByCategoryId(categoryId, isShow);
    }

    @Override
    public String getLastIdOfBook() {
        return bookDAO.getLastIdOfBook();
    }

    @Override
    public List<BookNewDTO> getAllBooksSearchPage() {
        return bookDAO.getAllBooksSearchPage();
    }

    @Override
    public List<Long> getAllLastOrderedBooks() {
        return bookDAO.getAllLastOrderedBooks();
    }

    @Override
    public void setLastOrderedBooks(List<Long> list) {
        bookDAO.setLastOrderedBooks(list);
    }

    @Override
    public List<Long> getAllAvailableBooks() {
        return bookDAO.getAllAvailableBooks();
    }
    
    @Override
    public List<BookNewDTO> getAllLightBookDtoForSearch() {
        return bookDAO.getAllLightBookDtoForSearch();
    }

    @Override
    public Long getSizeOfTotalBooks() {
        return bookDAO.getSizeOfTotalBooks();
    }

    @Override
    public void deleteBook(Long id){
        bookDAO.deleteBook(id);
    }

    @Override
    public List<Book> getAllBooksByCategoryId(Long id) {
        return bookDAO.getAllBooksByCategoryId(id);
    }
}
