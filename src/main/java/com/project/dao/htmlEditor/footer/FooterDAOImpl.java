package com.project.dao.htmlEditor.footer;

import com.project.model.htmlEditor.Link;
import com.project.model.htmlEditor.footer.Footer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FooterDAOImpl implements FooterDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Footer getFooter() {
        return entityManager.createQuery("FROM Footer", Footer.class).getSingleResult();
    }

    @Override
    public Footer updateFooter(Footer footer) {
        entityManager.merge(footer);
        return footer;
    }

    @Override
    public Footer createFooter(Footer footer) {
        entityManager.persist(footer);
        return footer;
    }

    @Override
    public void deleteFooter() {
        Footer footer = getFooter();
        entityManager
                .createQuery("DELETE Footer WHERE id = :id", Footer.class)
                .setParameter("id", footer.getId());
    }

    @Override
    public Long getUpdateDate() {
        return entityManager
                .createQuery("SELECT new java.lang.Long(f.updateDate) FROM Footer f", Long.class)
                .getSingleResult();
    }

    @Override
    public void deleteOldLinks(Footer footer) {
        for (Link link : footer.getLinks()) {
            entityManager.remove(entityManager.contains(link) ? link : entityManager.merge(link));
        }
    }
}
