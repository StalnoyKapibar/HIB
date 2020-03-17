package com.project.service;

import com.project.dao.BookDAO;
import com.project.dao.BookDTORepository;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.LocaleString;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private BookDAO bookDAO;

    private BookDTORepository bookDTORepository;

    @Override
    public List<BookDTO> getAllBookDTO() {
        return bookDAO.getAllBookDTO();
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        bookDAO.addBook(bookDTO);
    }

    @Override
    public Page<BookDTO> findAll(Pageable pageable) {
        return bookDTORepository.findAll(pageable);
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
    public BookDTO getBookBySearchRequest(LocaleString locale) {
        return bookDAO.getBookBySearchRequest(locale);
    }
}
