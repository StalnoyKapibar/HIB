package com.project.dao;

import com.project.dao.abstraction.BookDao;
import com.project.model.*;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@Repository
@Transactional
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
                " b.price, b.originalLanguageName, b.coverImage, b.isShow) FROM Book b WHERE id = :id").replaceAll("LOC", lang);
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
    public BookSearchPageDTO getBookBySearchRequest(String request, Long priceFrom, Long priceTo, String yearOfEditionFrom, String yearOfEditionTo,
                                                    Long pagesFrom, Long pagesTo, String searchBy, List<Long> categories, Pageable pageable, boolean isShow) {
        int limitBookDTOOnPage = pageable.getPageSize();
        int minNumberId = limitBookDTOOnPage * pageable.getPageNumber();
        long amountOfBooks = getQuantityBooksBySearchRequest(request, priceFrom, priceTo, yearOfEditionFrom, yearOfEditionTo, pagesFrom, pagesTo, searchBy, categories, isShow);
        String name = ("%" + request + "%");
        String hql = ("SELECT new com.project.model.BookNewDTO(b.id, b.originalLanguage.name," +
                "b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit, b.description.en," +
                "b.originalLanguage.edition, b.originalLanguage.editionTranslit, b.yearOfEdition, b.pages, b.price, b.originalLanguageName, b.coverImage, b.category, b.isShow) " +
                "FROM Book b where (b.isShow = true or b.isShow = :isShow) AND " +
                "(((b.originalLanguage.name LIKE :name or b.originalLanguage.nameTranslit LIKE :name or " +
                "b.originalLanguage.author LIKE :name or b.originalLanguage.authorTranslit LIKE :name) and :searchBy = 'name-author') OR" +
                "((b.originalLanguage.name LIKE :name or b.originalLanguage.nameTranslit LIKE :name) and :searchBy = 'name') OR" +
                "((b.originalLanguage.author LIKE :name or b.originalLanguage.authorTranslit LIKE :name) and :searchBy = 'author')) AND" +
                "((b.pages >= :pagesFrom and b.pages <= :pagesTo) OR (b.pages >= :pagesFrom and :pagesTo is null) OR " +
                "(:pagesFrom is null and b.pages <= :pagesTo) OR (:pagesFrom is null and :pagesTo is null)) AND " +
                "((b.yearOfEdition >= :yearOfEditionFrom and b.yearOfEdition <= :yearOfEditionTo) OR (b.yearOfEdition >= :yearOfEditionFrom and :yearOfEditionTo = 'null') OR " +
                "(:yearOfEditionFrom = 'null' and b.yearOfEdition <= :yearOfEditionTo) OR (:yearOfEditionFrom = 'null' and :yearOfEditionTo = 'null')) AND " +
                "((b.category.id in :categories) or ('undefined' in :categories)) AND" +
                "((b.price >= :priceFrom and b.price <= :priceTo) OR (b.price >= :priceFrom and :priceTo = 0) OR " +
                "(:priceFrom = 0 and b.price <= :priceTo) OR (:priceFrom = 0 and :priceTo = 0)) ORDER BY b.id ASC");
        //b.isShow = true AND
        List<BookNewDTO> bookNewDTOList = entityManager.createQuery(hql, BookNewDTO.class)
                .setParameter("name", name)
                .setParameter("isShow", isShow)
                .setParameter("pagesFrom", pagesFrom)
                .setParameter("pagesTo", pagesTo)
                .setParameter("yearOfEditionFrom", yearOfEditionFrom)
                .setParameter("yearOfEditionTo", yearOfEditionTo)
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .setParameter("categories", categories)
                .setParameter("searchBy", searchBy)
                .setFirstResult(minNumberId)
                .setMaxResults(limitBookDTOOnPage)
                .getResultList();

        BookSearchPageDTO pageableBookSearchDTO = new BookSearchPageDTO();
        pageableBookSearchDTO.setBooks(bookNewDTOList);
        pageableBookSearchDTO.setNumberPages(pageable.getPageNumber());
        pageableBookSearchDTO.setSize(pageable.getPageSize());
        pageableBookSearchDTO.setAmountOfBooksInDb(amountOfBooks);
        pageableBookSearchDTO.setAmountOfPages((int) Math.ceil(Float.valueOf(amountOfBooks) / limitBookDTOOnPage));
        return pageableBookSearchDTO;
    }

    @Override
    public long getQuantityBooksBySearchRequest(String request, Long priceFrom, Long priceTo,
                                                String yearOfEditionFrom, String yearOfEditionTo, Long pagesFrom, Long pagesTo, String searchBy, List<Long> categories, boolean isShow) {
        String name = ("%" + request + "%");
        //todo число книг диз энд эвэл
        String hql = ("SELECT new com.project.model.BookNewDTO(b.id)" +
                "FROM Book b where (b.isShow = true or b.isShow = :isShow) AND " +
                "(((b.originalLanguage.name LIKE :name or b.originalLanguage.nameTranslit LIKE :name or " +
                "b.originalLanguage.author LIKE :name or b.originalLanguage.authorTranslit LIKE :name) and :searchBy = 'name-author') OR" +
                "((b.originalLanguage.name LIKE :name or b.originalLanguage.nameTranslit LIKE :name) and :searchBy = 'name') OR" +
                "((b.originalLanguage.author LIKE :name or b.originalLanguage.authorTranslit LIKE :name) and :searchBy = 'author')) AND" +
                "((b.pages >= :pagesFrom and b.pages <= :pagesTo) OR (b.pages >= :pagesFrom and :pagesTo is null) OR " +
                "(:pagesFrom is null and b.pages <= :pagesTo) OR (:pagesFrom is null and :pagesTo is null)) AND " +
                "((b.yearOfEdition >= :yearOfEditionFrom and b.yearOfEdition <= :yearOfEditionTo) OR (b.yearOfEdition >= :yearOfEditionFrom and :yearOfEditionTo = 'null') OR " +
                "(:yearOfEditionFrom = 'null' and b.yearOfEdition <= :yearOfEditionTo) OR (:yearOfEditionFrom = 'null' and :yearOfEditionTo = 'null')) AND " +
                "((b.category.id in :categories) or ('undefined' in :categories)) AND" +
                "((b.price >= :priceFrom and b.price <= :priceTo) OR (b.price >= :priceFrom and :priceTo = 0) OR " +
                "(:priceFrom = 0 and b.price <= :priceTo) OR (:priceFrom = 0 and :priceTo = 0))");
        List<BookNewDTO> list = entityManager.createQuery(hql, BookNewDTO.class)
                .setParameter("name", name)
                .setParameter("isShow", isShow)
                .setParameter("pagesFrom", pagesFrom)
                .setParameter("pagesTo", pagesTo)
                .setParameter("yearOfEditionFrom", yearOfEditionFrom)
                .setParameter("yearOfEditionTo", yearOfEditionTo)
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .setParameter("categories", categories)
                .setParameter("searchBy", searchBy)
                .getResultList();
        return Long.valueOf(list.size());
    }

    @Override
    public List<BookNewDTO> getBookBySearchRequest(String req, boolean isShow) {
        String name = "%" + req + "%";
        String hql = ("SELECT new com.project.model.BookNewDTO(b.id, b.originalLanguage.name," +
                "b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit," +
                "b.originalLanguage.edition, b.originalLanguage.editionTranslit, b.description.en, b.isShow)" +
                "FROM Book b where (b.originalLanguage.name like :name OR b.originalLanguage.nameTranslit like :name OR " +
                "b.originalLanguage.author like :name OR b.originalLanguage.authorTranslit like :name)");
        // AND (b.isShow =:show)
        List<BookNewDTO> list = entityManager
                .createQuery(hql, BookNewDTO.class)
                .setParameter("name", name)
                //.setParameter("show", isShow)
                .getResultList();
        return list;
    }

    @Override
    public List<BookNewDTO> getAllBooksSearchPage() {
        String hql = ("SELECT new com.project.model.BookNewDTO(b.id, b.originalLanguage.name, " +
                "b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit, b.description.en, " +
                "b.originalLanguage.edition, b.originalLanguage.editionTranslit, b.yearOfEdition, b.pages, b.price, b.originalLanguageName, b.coverImage, b.category, b.isShow)" +
                "FROM Book b");
        // where b.isShow = :show
        List<BookNewDTO> list = entityManager.createQuery(hql, BookNewDTO.class)
                //.setParameter("show", true)
                .getResultList();
        return list;
    }

    @Override
    public List<BookNewDTO> getAllLightBookDtoForSearch() {
        String hql = ("SELECT new com.project.model.BookNewDTO(b.id, b.originalLanguage.name, " +
                "b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit, b.originalLanguage.edition, b.originalLanguage.editionTranslit, b.description.en, b.isShow )" +
                "FROM Book b");
        // WHERE b.isShow = :show
        List<BookNewDTO> list = entityManager.createQuery(hql, BookNewDTO.class)
                //.setParameter("show", true)
                .getResultList();
        return list;
    }

    @Override
    public Long getSizeOfTotalBooks() {
        return (Long) entityManager
                .createQuery("SELECT count (b.id) FROM Book b ")
                .getSingleResult();
    }

    @Override
    public List<BookDTO> get20BookDTO(String locale) {
        String hql = ("SELECT new com.project.model.BookDTO(b.id, b.name.LOC, b.author.LOC, b.price, b.coverImage, b.isShow)" +
                "FROM Book b  ORDER BY RAND()")
        //WHERE b.isShow = true or b.isShow = null
                .replaceAll("LOC", locale);
        return entityManager.createQuery(hql, BookDTO.class).setMaxResults(20).getResultList();
    }

    @Override
    public String getLastIdOfBook() {
        String hql = "SELECT max(b.id) FROM Book b";
        return entityManager.createQuery(hql).getSingleResult().toString();
    }

    @Override
    public BookPageAdminDto getPageBookDTOByPageable(Pageable pageable, boolean disabled) {
        int limitBookDTOOnPage = pageable.getPageSize();
        int minNumberId = limitBookDTOOnPage * pageable.getPageNumber();
        String sortTypeTmp = String.valueOf(pageable.getSort());
        String sortingObject = sortTypeTmp.split(":")[0];
        String typeOfSorting = sortTypeTmp.split(" ")[1];
        String hql = "SELECT b " +
                "FROM Book b  ORDER BY sortingObject typeOfSorting"
                        //WHERE b.isShow = :disabled
                        .replaceAll("sortingObject", sortingObject)
                        .replaceAll("typeOfSorting", typeOfSorting);

        List<Book> bookDTOList = entityManager.createQuery(hql, Book.class)
                //.setParameter("disabled", disabled)
                .setFirstResult(minNumberId)
                .setMaxResults(limitBookDTOOnPage)
                .getResultList();
        BookPageAdminDto pageableBookDTO = new BookPageAdminDto();
        pageableBookDTO.setListBookDTO(bookDTOList);
        pageableBookDTO.setNumberPages(pageable.getPageNumber());
        pageableBookDTO.setPageableSize(pageable.getPageSize());
        pageableBookDTO.setTotalPages((int) Math.ceil(Float.parseFloat(getQuantityBook()) / limitBookDTOOnPage));
        return pageableBookDTO;
    }

    @Override
    public List<BookNewDTO> getBooksByCategoryId(Long categoryId) {
        String hql = ("SELECT new com.project.model.BookNewDTO(b.id, b.originalLanguage.name," +
                "b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit, b.description.en," +
                "b.originalLanguage.edition, b.originalLanguage.editionTranslit, b.yearOfEdition, b.pages, b.price, b.originalLanguageName, b.coverImage, b.category, b.isShow)" +
                "FROM Book b WHERE b.category.id =:categoryId");
        // AND b.isShow = true
        return entityManager.createQuery(hql, BookNewDTO.class).setParameter("categoryId", categoryId).getResultList();
    }

    @Override
    public List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang) {
        String hql = "SELECT new com.project.model.BookDTOForCategories(b.id, b.name.en, " +
                "b.author.en, b.edition.en, b.yearOfEdition, b.price, b.pages, " +
                "b.coverImage, b.category, b.isShow) FROM Book b WHERE b.category.id =:categoryId".replaceAll("LOC", lang);
    // AND b.isShow = true
        return entityManager.createQuery(hql, BookDTOForCategories.class).setParameter("categoryId", categoryId).getResultList();
    }


    @Override
    public Long getCountBooksByCategoryId(Long categoryId, boolean isShow) {
        String sql = "select count(b) from Book b WHERE b.category.id =:categoryId AND (b.isShow = true or b.isShow = :isShow)";
        // AND b.isShow = true
        return (Long) entityManager.createQuery(sql)
                .setParameter("categoryId", categoryId)
                .setParameter("isShow", isShow)
                .getSingleResult();
    }

    @Override
    public BookPageDto getBookPageByPageable(Pageable pageable) {
        int limitBookDTOOnPage = pageable.getPageSize();
        int minNumberId = limitBookDTOOnPage * pageable.getPageNumber();
        String amountOfBooks = getQuantityOfBooksByIsShow(true).toString();
        String sortTypeTmp = String.valueOf(pageable.getSort());
        String sortingObject = sortTypeTmp.split(":")[0];
        String typeOfSorting = sortTypeTmp.split(" ")[1];
        String hql = ("SELECT new com.project.model.BookDTO(b.id, b.originalLanguage.name, " +
                "b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit, " +
                "b.price, b.coverImage, b.isShow) FROM Book b ORDER BY sortingObject typeOfSorting")
                .replaceAll("sortingObject", sortingObject)
                .replaceAll("typeOfSorting", typeOfSorting);
        //WHERE b.isShow = :show

        List<BookDTO> bookDTOList = entityManager.createQuery(hql, BookDTO.class)
                //.setParameter("show", true)
                .setFirstResult(minNumberId)
                .setMaxResults(limitBookDTOOnPage)
                .getResultList();

        BookPageDto pageableBookDTO = new BookPageDto();
        pageableBookDTO.setBooks(bookDTOList);
        pageableBookDTO.setNumberPages(pageable.getPageNumber());
        pageableBookDTO.setSize(pageable.getPageSize());
        pageableBookDTO.setAmountOfBooksInDb(Long.parseLong(amountOfBooks));
        pageableBookDTO.setAmountOfPages((int) Math.ceil(Float.parseFloat(amountOfBooks) / limitBookDTOOnPage));

        return pageableBookDTO;
    }

    private Long getQuantityOfBooksByIsShow(Boolean isShow) {
        //todo счетчик
        return entityManager
                .createQuery("SELECT COUNT (1) FROM Book WHERE isShow = :isShow", Long.class)
                .setParameter("isShow", isShow)
                .getSingleResult();

    }

    @Transactional
    public void deleteImgfromDB(String idImage) {
        entityManager.createNativeQuery("delete from book_list_image where list_image_id like :idImage").setParameter("idImage", idImage)
                .executeUpdate();

    }

    @Override
    public List<Long> getAllLastOrderedBooks() {
        String hql = "SELECT b.id FROM Book b WHERE b.lastBookOrdered = true";
        return entityManager.createQuery(hql).getResultList();
    }


    @Override
    public void setLastOrderedBooks(List<Long> list) {
        for (Long id : list) {
            entityManager.createQuery("update Book set lastBookOrdered = true" +
                    " where id = :bookId")
                    .setParameter("bookId", id)
                    .executeUpdate();
        }
    }
}
