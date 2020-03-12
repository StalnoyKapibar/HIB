package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.BookNewDTO;
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
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookDTO> getAllBookDTO() {
        String temp = "Select new com.project.model.BookDTO(b.id, b.nameLocale, b.authorLocale, b.coverImage) FROM Book b";
        List<BookDTO> listBookDTO = entityManager.createQuery(temp, BookDTO.class).getResultList();
        return listBookDTO;
    }

    @Override
    public void addBook(BookNewDTO bookNewDTO) {
        Book book = new Book();
        book.setAuthorLocale(bookNewDTO.getAuthor());
        book.setNameLocale(bookNewDTO.getName());
        book.setCoverImage(bookNewDTO.getCoverImage());
        book.setListImage(bookNewDTO.getImageList());
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
    public void updateBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setNameLocale(bookDTO.getName());
        book.setAuthorLocale(bookDTO.getAuthor());
        entityManager.merge(book);
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        String query = "SELECT new com.project.model.BookDTO20(b.id, b.nameLocale.LOC, b.authorLocale.LOC, b.fileName) FROM Book b ORDER BY RAND()"
                .replaceAll("LOC", locale);
        List<BookDTO20> listBookDTO = entityManager.createQuery(query, BookDTO20.class).setMaxResults(20).getResultList();
        return listBookDTO;
    }

    @Override
    public BookNewDTO getBookDTOById(long id) {
        String temp = "Select new com.project.model.BookNewDTO(b.id, b.nameLocale, b.authorLocale, b.coverImage, b.listImage) FROM Book b where b.id =: id";
        BookNewDTO bookDTO = entityManager.createQuery(temp, BookNewDTO.class).setParameter("id", id).getSingleResult();
        return bookDTO;
    }

    @Override
    public String getLastIdOfBook() {
        String temp = "SELECT max(b.id) FROM Book b";
        return entityManager.createQuery(temp).getSingleResult().toString();
    }
}
