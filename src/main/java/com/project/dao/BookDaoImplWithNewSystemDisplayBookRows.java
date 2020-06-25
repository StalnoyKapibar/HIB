package com.project.dao;

import com.project.model.BookDTO;
import com.project.model.BookNewDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("withNewSystemDisplayBook")
public class BookDaoImplWithNewSystemDisplayBookRows extends BookDaoImpl {

    @Override
    @SuppressWarnings("all")
    public BookNewDTO getNewBookDTObyIdAndLang(Long id, String lang) {
        String hql = ("SELECT new com.project.model.BookNewDTO(b.id, b.originalLanguage.name, " +
                "b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit, " +
                "CASE WHEN b.description.LOC <> '' " +
                "THEN b.description.LOC " +
                "ELSE " +
                "CASE WHEN b.description.en <> '' " +
                "THEN b.description.en " +
                "ELSE b.description.fr " +
                "END " +
                "END, " +
                "b.originalLanguage.edition, b.originalLanguage.editionTranslit, b.yearOfEdition, " +
                "b.pages, b.price, b.originalLanguageName, b.coverImage, b.isShow) FROM Book b WHERE id = :id").replaceAll("LOC", lang);
        BookNewDTO bookNewDTO = entityManager.createQuery(hql, BookNewDTO.class).setParameter("id", id).getSingleResult();
        bookNewDTO.setImageList(getBookImageListById(id));
        return bookNewDTO;
    }

    @Override
    public List<BookDTO> get20BookDTO(String locale) {
        String hql = "SELECT new com.project.model.BookDTO(b.id, b.originalLanguage.name, b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit, b.price, b.coverImage)" +
                "FROM Book b WHERE b.isShow = true or b.isShow = null ORDER BY RAND()";
        return entityManager.createQuery(hql, BookDTO.class).setMaxResults(20).getResultList();
    }
}
