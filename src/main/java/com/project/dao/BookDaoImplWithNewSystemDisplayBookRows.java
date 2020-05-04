package com.project.dao;

import com.project.model.BookDTO20;
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
                "b.description.LOC, b.originalLanguage.edition, b.originalLanguage.editionTranslit, b.yearOfEdition, " +
                "b.pages, b.price, b.originalLanguageName, b.coverImage) FROM Book b WHERE id = :id").replaceAll("LOC", lang);
        BookNewDTO bookNewDTO = entityManager.createQuery(hql, BookNewDTO.class).setParameter("id", id).getSingleResult();
        bookNewDTO.setImageList(getBookImageListById(id));
        return bookNewDTO;
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        String hql = "SELECT new com.project.model.BookDTO20(b.id, b.originalLanguage.name, b.originalLanguage.nameTranslit, b.originalLanguage.author, b.originalLanguage.authorTranslit, b.price, b.coverImage)" +
                "FROM Book b WHERE b.isShow = true or b.isShow = null ORDER BY RAND()";
        return entityManager.createQuery(hql, BookDTO20.class).setMaxResults(20).getResultList();
    }
}
