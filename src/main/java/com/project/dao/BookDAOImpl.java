package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public  class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public List<BookDTO> getAllBookDTO() {
        String temp = "Select new com.project.model.BookDTO(b.id, b.nameLocale, b.authorLocale) FROM Book b";
        List<BookDTO> listBookDTO = entityManager.createQuery(temp, BookDTO.class).getResultList();
        return listBookDTO;
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthorLocale(bookDTO.getAuthor());
        book.setNameLocale(bookDTO.getName());
        entityManager.persist(book);
    }

    @Override
    public void delBookById(long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public Book getUserById(long id) {
       return entityManager.find(Book.class, id);
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setNameLocale(bookDTO.getName());
        book.setAuthorLocale(bookDTO.getAuthor());
        entityManager.merge(book);
    }
}
