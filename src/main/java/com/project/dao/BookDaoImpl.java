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

    @Override
    public String getQuantityBook() {
        return entityManager
                .createQuery("SELECT COUNT (1) FROM Book")
                .getSingleResult()
                .toString();
    }

    @Override
    @SuppressWarnings("all")
    public BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang) {
        String hql = ("SELECT new com.project.model.BookNewDTO(b.id, b.name.LOC, " +
                "b.author.LOC, b.description.LOC, b.edition.LOC, b.yearOfEdition, b.pages," +
                " b.price, b.originalLanguage, b.coverImage) FROM Book b WHERE id = :id").replaceAll("LOC", lang);
        BookNewDTO bookNewDTO = entityManager.createQuery(hql, BookNewDTO.class).setParameter("id", id).getSingleResult();
        List<Image> images = entityManager
                .createNativeQuery("SELECT id, name_image " +
                                "FROM image i " +
                                "INNER JOIN book_list_image bi " +
                                "ON i.id = list_image_id " +
                                "WHERE bi.book_id = :id",
                        Image.class).setParameter("id", id).getResultList();
        bookNewDTO.setImageList(images);
        return bookNewDTO;
    }

    @Override
    public BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale) {
        String hql = ("SELECT new com.project.model.BookDTO20(b.id, b.name.LOC, b.author.LOC, b.price, b.coverImage)" +
                "FROM Book b where b.name=:name or b.author=:name ")
                .replaceAll("LOC", locale);
        return entityManager.createQuery(hql, BookDTO20.class).setParameter("name", localeString).getSingleResult();
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        String hql = ("SELECT new com.project.model.BookDTO20(b.id, b.name.LOC, b.author.LOC, b.price, b.coverImage)" +
                "FROM Book b WHERE b.disabled = false or b.disabled = null ORDER BY RAND()")
                .replaceAll("LOC", locale);
        return entityManager.createQuery(hql, BookDTO20.class).setMaxResults(20).getResultList();
    }

    @Override
    public String getLastIdOfBook() {
        String hql = "SELECT max(b.id) FROM Book b";
        return entityManager.createQuery(hql).getSingleResult().toString();
    }

    @Override
    public PageableBookDTO getPageBookDTOByPageable(Pageable pageable, boolean disabled) {
        int limitBookDTOOnPage = pageable.getPageSize();
        int minNumberId = limitBookDTOOnPage * pageable.getPageNumber();
        String sortTypeTmp = String.valueOf(pageable.getSort());
        String sortingObject = sortTypeTmp.split(":")[0];
        String typeOfSorting = sortTypeTmp.split(" ")[1];
        String hql = "SELECT b " +
                "FROM Book b WHERE b.disabled = :disabled ORDER BY sortingObject typeOfSorting"
                        .replaceAll("sortingObject", sortingObject)
                        .replaceAll("typeOfSorting", typeOfSorting);
        List<Book> bookDTOList = entityManager.createQuery(hql, Book.class)
                .setParameter("disabled", disabled)
                .setFirstResult(minNumberId)
                .setMaxResults(limitBookDTOOnPage)
                .getResultList();
        PageableBookDTO pageableBookDTO = new PageableBookDTO();
        pageableBookDTO.setListBookDTO(bookDTOList);
        pageableBookDTO.setNumberPages(pageable.getPageNumber());
        pageableBookDTO.setPageableSize(pageable.getPageSize());
        pageableBookDTO.setTotalPages((int) Math.ceil(Float.parseFloat(getQuantityBook()) / limitBookDTOOnPage));
        return pageableBookDTO;
    }
}
