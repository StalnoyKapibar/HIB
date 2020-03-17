package com.project.service;

import com.project.dao.BookDAO;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.PageableBookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    @Override
    public List<BookDTO> getAllBookDTO() {
        return bookDAO.getAllBookDTO();
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        bookDAO.addBook(bookDTO);
    }

    @Override
    public PageableBookDTO getPageBookDTOByPageable(Pageable pageable) {
        return bookDAO.getPageBookDTOByPageable(pageable);
    }

    @Override
    public void deleteBookById(long id) {
        bookDAO.deleteBookById(id);
    }

    public BookDTO getBookByIdLocale(long id) {
        return bookDAO.getBookByIdLocale(id);
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        bookDAO.updateBook(bookDTO);
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        return bookDAO.get20BookDTO(locale);
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
