package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.PageableBookDTO;
import org.springframework.data.domain.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Repository
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String getQuantityBook() {
        return entityManager.createQuery("SELECT COUNT (1) FROM Book").getSingleResult().toString();
    }

    @Override
    public List<BookDTO> getAllBookDTO() {
        String temp = "Select new com.project.model.BookDTO(b.id, b.nameLocale, b.authorLocale, b.coverImage) FROM Book b";
        List<BookDTO> listBookDTO = entityManager.createQuery(temp, BookDTO.class).getResultList();
        return listBookDTO;
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthorLocale(bookDTO.getAuthor());
        book.setNameLocale(bookDTO.getName());
        book.setCoverImage(bookDTO.getCoverImage());
        book.setListImage(bookDTO.getImageList());
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
        book.setCoverImage(bookDTO.getCoverImage());
        book.setListImage(bookDTO.getImageList());
        entityManager.merge(book);
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        String query = ("SELECT new com.project.model.BookDTO20(b.id, b.nameLocale.LOC, b.authorLocale.LOC, b.fileName)" +
                "FROM Book b ORDER BY RAND()")
                .replaceAll("LOC", locale);
        List<BookDTO20> listBookDTO = entityManager.createQuery(query, BookDTO20.class).setMaxResults(20).getResultList();
        return listBookDTO;
    }

    @Override
    public BookDTO getBookDTOById(long id) {
        Book book = getBookById(id);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getNameLocale());
        bookDTO.setAuthor(book.getAuthorLocale());
        bookDTO.setCoverImage(book.getCoverImage());
        bookDTO.setImageList(book.getListImage());
        return bookDTO;
    }

    @Override
    public String getLastIdOfBook() {
        String temp = "SELECT max(b.id) FROM Book b";
        return entityManager.createQuery(temp).getSingleResult().toString();
    }

    @Override
    public PageableBookDTO getPageBookDTOByPageable(Pageable pageable) {
        int numberPage = pageable.getPageNumber();
        int limitBookDTOOnPage = pageable.getPageSize();
        int minNumberId = limitBookDTOOnPage * numberPage;
        String sortTypeTmp = String.valueOf(pageable.getSort());
        String sortingObject = sortTypeTmp.split(":")[0];
        String typeOfSorting = sortTypeTmp.split(" ")[1];
        String temp = "Select b " +
                "FROM Book b ORDER BY sortingObject typeOfSorting"
                        .replaceAll("sortingObject", sortingObject)
                        .replaceAll("typeOfSorting", typeOfSorting);
        List<Book> list = entityManager.createQuery(temp, Book.class)
                .setFirstResult(minNumberId)
                .setMaxResults(limitBookDTOOnPage)
                .getResultList();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (Book book : list) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setName(book.getNameLocale());
            bookDTO.setAuthor(book.getAuthorLocale());
            bookDTO.setCoverImage(book.getCoverImage());
            bookDTO.setImageList(book.getListImage());
            bookDTOList.add(bookDTO);
        }
        PageableBookDTO pageableBookDTO = new PageableBookDTO();
        pageableBookDTO.setListBookDTO(bookDTOList);
        pageableBookDTO.setNumberPages(pageable.getPageNumber());
        pageableBookDTO.setPageableSize(pageable.getPageSize());
        pageableBookDTO.setTotalPages((int) Math.ceil(Float.parseFloat(getQuantityBook()) / limitBookDTOOnPage));
        return pageableBookDTO;
    }
}
