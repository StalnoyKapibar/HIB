package com.project.dao;

import com.project.dao.abstraction.BookDao;
import com.project.model.*;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Primary
@Repository
public class BookDaoImpl extends AbstractDao<Long, Book> implements BookDao {

    BookDaoImpl() {
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
                " b.price, b.originalLanguageName, b.coverImage) FROM Book b WHERE id = :id").replaceAll("LOC", lang);
        BookNewDTO bookNewDTO = entityManager.createQuery(hql, BookNewDTO.class).setParameter("id", id).getSingleResult();
        bookNewDTO.setImageList(getBookImageListById(id));
        return bookNewDTO;
    }

    @SuppressWarnings("unchecked")
    protected List<Image> getBookImageListById(Long id) {
        return entityManager
                .createNativeQuery("SELECT id, name_image " +
                                "FROM image i " +
                                "INNER JOIN book_list_image bi " +
                                "ON i.id = list_image_id " +
                                "WHERE bi.book_id = :id",
                        Image.class).setParameter("id", id).getResultList();
    }

    @Override
    public BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale) {
        String hql = ("SELECT new com.project.model.BookDTO20(b.id, b.name.LOC, b.author.LOC, b.price, b.coverImage)" +
                "FROM Book b where (b.name=:name or b.author=:name) AND b.isShow = true  ")
                .replaceAll("LOC", locale);
        List<BookDTO20> list = entityManager.createQuery(hql, BookDTO20.class).setParameter("name", localeString).getResultList();
        if (list.size() != 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale, Long priceFrom, Long priceTo, String yearOfEdition, Long pages, String searchBy, String category) {
        String hql = ("SELECT new com.project.model.BookDTO20(b.id, b.name.LOC, b.author.LOC, b.price, b.coverImage)" +
                "FROM Book b where b.isShow = true AND" +
                "(((b.name = :name or b.author = :name) and :searchBy = 'name-author') OR" +
                "(b.name = :name and :searchBy = 'name') OR" +
                "(b.author = :name and :searchBy = 'author')) AND" +
                "(b.pages = :pages or :pages is null) AND" +
                "(b.yearOfEdition = :yearOfEdition or :yearOfEdition = 'null') AND" +
                "(b.category.categoryName = :category or :category = 'undefined') AND" +
                "((b.price >= :priceFrom and b.price <= :priceTo) OR (b.price >= :priceFrom and :priceTo = 0) OR (:priceFrom = 0 and b.price <= :priceTo) OR (:priceFrom = 0 and :priceTo = 0))")
                .replaceAll("LOC", locale);
        List<BookDTO20> list = entityManager.createQuery(hql, BookDTO20.class)
                .setParameter("name", localeString)
                .setParameter("pages", pages)
                .setParameter("yearOfEdition", yearOfEdition)
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .setParameter("category", category)
                .setParameter("searchBy", searchBy)
                .getResultList();
        if (list.size() != 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<BookDTO20> getBooksBySearchParameters(String locale, Long priceFrom, Long priceTo, String yearOfEdition, Long pages, String searchBy, String category) {

        String hql = ("SELECT new com.project.model.BookDTO20(b.id, b.name.LOC, b.author.LOC, b.price, b.coverImage)" +
                "FROM Book b where b.isShow = true AND" +
                "(b.pages = :pages or :pages is null) AND" +
                "(b.yearOfEdition = :yearOfEdition or :yearOfEdition = 'null') AND" +
                "(b.category.categoryName = :category or :category = 'undefined') AND" +
                "((b.price >= :priceFrom and b.price <= :priceTo) OR (b.price >= :priceFrom and :priceTo = 0) OR (:priceFrom = 0 and b.price <= :priceTo) OR (:priceFrom = 0 and :priceTo = 0))")
                .replaceAll("LOC", locale);
        List<BookDTO20> list = entityManager.createQuery(hql, BookDTO20.class)
                .setParameter("pages", pages)
                .setParameter("yearOfEdition", yearOfEdition)
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .setParameter("category", category)
                .getResultList();
        return list;
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        String hql = ("SELECT new com.project.model.BookDTO20(b.id, b.name.LOC, b.author.LOC, b.price, b.coverImage)" +
                "FROM Book b WHERE b.isShow = true or b.isShow = null ORDER BY RAND()")
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
                "FROM Book b WHERE b.isShow = :disabled ORDER BY sortingObject typeOfSorting"
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

    @Override
    public List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang) {
        String hql = "SELECT new com.project.model.BookDTOForCategories(b.id, b.name.en, " +
                "b.author.en, b.edition.en, b.yearOfEdition, b.price, b.pages, " +
                "b.coverImage, b.category) FROM Book b WHERE b.category.id =:categoryId AND b.isShow = true".replaceAll("LOC", lang);

        return entityManager.createQuery(hql, BookDTOForCategories.class).setParameter("categoryId", categoryId).getResultList();
    }
}
