package com.project.dao;

import com.project.dao.abstraction.BookDao;
import com.project.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDaoImpl extends AbstractDao<Long, Book> implements BookDao {

    BookDaoImpl(){
        super(Book.class);
    }

    public BookDTO getBookDTOFromBook(Book book){
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

    public Book getBookFromBookDTO(BookDTO bookDTO){
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

    @Override
    public String getQuantityBook() {
        return entityManager.createQuery("SELECT COUNT (1) FROM Book").getSingleResult().toString();
    }

    @Override
    @SuppressWarnings("all")
    public BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang) {
        String hql = ("Select new com.project.model.BookNewDTO(b.id, b.nameLocale.LOC, " +
                "b.authorLocale.LOC, b.desc.LOC, b.edition.LOC, b.yearOfEdition, b.pages," +
                " b.price, b.originalLanguage, b.coverImage) FROM Book b WHERE id = :id").replaceAll("LOC", lang);
        BookNewDTO bookNewDTO = entityManager.createQuery(hql, BookNewDTO.class).setParameter("id", id).getSingleResult();
        List<Image> images = entityManager
                .createNativeQuery(" select id, name_image " +
                                "from image i " +
                                "inner join book_list_image bi " +
                                "on i.id = list_image_id " +
                                "where bi.book_id = :id",
                        Image.class).setParameter("id", id).getResultList();
        bookNewDTO.setImageList(images);
        return bookNewDTO;
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
    public List<BookDTO20> get20BookDTO(String locale) {
        String query = ("SELECT new com.project.model.BookDTO20(b.id, b.nameLocale.LOC, b.authorLocale.LOC, b.price, b.coverImage)" +
                "FROM Book b ORDER BY RAND()")
                .replaceAll("LOC", locale);
        List<BookDTO20> listBookDTO = entityManager.createQuery(query, BookDTO20.class).setMaxResults(20).getResultList();
        return listBookDTO;
    }

    @Override
    public BookDTO getBookDTOById(long id) {
        return getBookDTOFromBook(findById(id));
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
