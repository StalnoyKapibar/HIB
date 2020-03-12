package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

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
    public void deleteBookById(long id) {
        entityManager.remove(getBookById(id));
    }

    @Override
    public Book getBookById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public BookDTO getBookByIdLocale(long id) {
        String query = ("SELECT new com.project.model.BookDTO(b.id, b.nameLocale," +
                " b.authorLocale) FROM Book b where b.id=" + id);
        return entityManager.createQuery(query, BookDTO.class).getSingleResult();
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setNameLocale(bookDTO.getName());
        book.setAuthorLocale(bookDTO.getAuthor());
        entityManager.merge(book);
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        String query = "SELECT new com.project.model.BookDTO20(b.id, b.nameLocale.LOC, b.authorLocale.LOC) FROM Book b ORDER BY RAND()"
                .replaceAll("LOC", locale);
        List<BookDTO20> listBookDTO = entityManager.createQuery(query, BookDTO20.class).setMaxResults(20).getResultList();
        return listBookDTO;
    }
}
