package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import com.project.model.BookDTO20;
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
    public String getQuantityRowBookInDb() {
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
    public Page<BookDTO> getPageBookDTOByPageable(Pageable pageable) {
        int numberPage = pageable.getPageNumber() + 1;
        int limitBookDTOOnPage = pageable.getPageSize();
        int maxNumberId = numberPage * limitBookDTOOnPage;
        int minNumberId = maxNumberId - 10;
        String sortTypeTmp = String.valueOf(pageable.getSort());
        String sortingObject = sortTypeTmp.split(":")[0];
        String typeOfSorting = sortTypeTmp.split(" ")[1];
        String temp = "Select b " +
                "FROM Book b ORDER BY sortingObject typeOfSorting"
                        .replaceAll("sortingObject", sortingObject)
                        .replaceAll("typeOfSorting", typeOfSorting);
        List<Book> list = entityManager.createQuery(temp, Book.class)
                .setFirstResult(minNumberId)
                .setMaxResults(maxNumberId)
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

        Page<BookDTO> page0 = new Page<BookDTO>() {

            @Override
            public Iterator<BookDTO> iterator() {
                return null;
            }

            @Override
            public void forEach(Consumer<? super BookDTO> action) {
            }

            @Override
            public Spliterator<BookDTO> spliterator() {
                return null;
            }

            @Override
            public int getTotalPages() {
                return (int) Math.ceil(Float.parseFloat(getQuantityRowBookInDb()) / limitBookDTOOnPage);
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public int getNumber() {
                return pageable.getPageNumber();
            }

            @Override
            public int getSize() {
                return pageable.getPageSize();
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<BookDTO> getContent() {
                return bookDTOList;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable getPageable() {
                return null;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Stream<BookDTO> stream() {
                return null;
            }

            @Override
            public <U> Page<U> map(Function<? super BookDTO, ? extends U> function) {
                return null;
            }

            @Override
            public <R> Streamable<R> flatMap(Function<? super BookDTO, ? extends Stream<? extends R>> mapper) {
                return null;
            }

            @Override
            public Streamable<BookDTO> filter(Predicate<? super BookDTO> predicate) {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Streamable<BookDTO> and(Supplier<? extends Stream<? extends BookDTO>> stream) {
                return null;
            }

            @Override
            public Streamable<BookDTO> and(BookDTO... others) {
                return null;
            }

            @Override
            public Streamable<BookDTO> and(Iterable<? extends BookDTO> iterable) {
                return null;
            }

            @Override
            public Streamable<BookDTO> and(Streamable<? extends BookDTO> streamable) {
                return null;
            }

            @Override
            public List<BookDTO> toList() {
                return null;
            }

            @Override
            public Set<BookDTO> toSet() {
                return null;
            }

            @Override
            public Stream<BookDTO> get() {
                return null;
            }

            @Override
            public Pageable nextOrLastPageable() {
                return null;
            }

            @Override
            public Pageable previousOrFirstPageable() {
                return null;
            }
        };
        return page0;
    }
}
