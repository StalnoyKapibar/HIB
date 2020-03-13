package com.project.search;

import com.project.dao.BookDAO;
import com.project.model.BookDTO;
import com.project.model.LocaleString;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class BookSearch {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookDAO bookDAO;

    public List<BookDTO> search(String req) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(LocaleString.class).get();
        Query query = queryBuilder.keyword().onFields("ru", "en", "fr", "it", "de", "cs", "gr").matching(req).createQuery();

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, LocaleString.class);
        List<LocaleString> results = jpaQuery.getResultList();
        List<BookDTO> result = new ArrayList<>();

        for (LocaleString localeString : results) {
            result.add(bookDAO.getBookByIdLocale(localeString.getId()));
        }

        return result;
    }
}
