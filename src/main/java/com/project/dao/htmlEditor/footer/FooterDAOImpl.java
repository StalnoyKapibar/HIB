package com.project.dao.htmlEditor.footer;

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
}
