package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
import com.project.model.LocaleString;
import com.project.model.PageableBookDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    private BookDTO getBookDTOFromBook(Book book){
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getNameLocale())
                .author(book.getAuthorLocale())
                .desc(book.getDesc())
                .edition(book.getEdition())
                .yearOfEdition(book.getYearOfEdition())
                .pages(book.getPages())
                .price(book.getPrice())
                .coverImage(book.getCoverImage())
                .originalLanguage(book.getOriginalLanguage())
                .imageList(book.getListImage())
                .build();
    }

    private Book getBookFromBookDTO(BookDTO bookDTO){
        return Book.builder()
                .authorLocale(bookDTO.getAuthor())
                .nameLocale(bookDTO.getName())
                .desc(bookDTO.getDesc())
                .edition(bookDTO.getEdition())
                .yearOfEdition(bookDTO.getYearOfEdition())
                .pages(bookDTO.getPages())
                .price(bookDTO.getPrice())
                .coverImage(bookDTO.getCoverImage())
                .originalLanguage(bookDTO.getOriginalLanguage())
                .listImage(bookDTO.getImageList())
                .build();
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String getQuantityBook() {
        return entityManager.createQuery("SELECT COUNT (1) FROM Book").getSingleResult().toString();
    }

    @Override
    public List<BookDTO> getAllBookDTO() {
        String temp = "Select new com.project.model.BookDTO(b.id, b.nameLocale, b.authorLocale, b.coverImage, b.price, b.listImage) FROM Book b";
        List<BookDTO> listBookDTO = entityManager.createQuery(temp, BookDTO.class).getResultList();
        return listBookDTO;
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        entityManager.persist(getBookFromBookDTO(bookDTO));
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
        return entityManager.createQuery("SELECT new com.project.model.BookDTO(b.id, b.nameLocale, b.authorLocale, b.coverImage, b.listImage) FROM Book b where b.id=:id", BookDTO.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale) {
        String query = ("SELECT new com.project.model.BookDTO20(b.id, b.nameLocale.LOC, b.authorLocale.LOC, b.price, b.coverImage)" +
                "FROM Book b where b.nameLocale=:name or b.authorLocale=:name ")
                .replaceAll("LOC", locale);
        return entityManager.createQuery(query, BookDTO20.class).setParameter("name", localeString).getSingleResult();
    }



    @Override
    public void updateBook(BookDTO bookDTO) {
        entityManager.merge(getBookFromBookDTO(bookDTO));
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        String query = ("SELECT new com.project.model.BookDTO20(b.id, b.nameLocale.LOC, b.authorLocale.LOC, b.price, b.coverImage)" +
                "FROM Book b ORDER BY RAND()")
                .replaceAll("LOC", locale);
        List<BookDTO20> listBookDTO = entityManager.createQuery(query, BookDTO20.class).setMaxResults(20).getResultList();
        return listBookDTO;
    }

    @Override
    public BookDTO getBookDTOById(long id) {
        Book book = getBookById(id);
        return getBookDTOFromBook(book);
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
            bookDTOList.add(getBookDTOFromBook(book));
        }
        PageableBookDTO pageableBookDTO = new PageableBookDTO();
        pageableBookDTO.setListBookDTO(bookDTOList);
        pageableBookDTO.setNumberPages(pageable.getPageNumber());
        pageableBookDTO.setPageableSize(pageable.getPageSize());
        pageableBookDTO.setTotalPages((int) Math.ceil(Float.parseFloat(getQuantityBook()) / limitBookDTOOnPage));
        return pageableBookDTO;
    }
}
