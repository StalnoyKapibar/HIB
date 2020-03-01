package com.project.service;

import com.project.dao.BookDAO;
import com.project.dao.BookDTORepository;
import com.project.model.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
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
    public void delBook(long id) {
        bookDAO.delBookById(id);
    }
}
