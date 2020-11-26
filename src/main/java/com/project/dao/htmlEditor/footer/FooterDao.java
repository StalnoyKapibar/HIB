package com.project.dao.htmlEditor.footer;

import com.project.dao.abstraction.GenericDao;
import com.project.model.htmlEditor.footer.Footer;

public interface FooterDao extends GenericDao<Long, Footer> {
    Footer getFooter();

    Footer updateFooter(Footer footer);

    Footer createFooter(Footer footer);

    void deleteFooter();

    Long getUpdateDate();

    void deleteOldLinks(Footer footer);
}
