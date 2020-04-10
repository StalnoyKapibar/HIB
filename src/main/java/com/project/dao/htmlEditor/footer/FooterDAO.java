package com.project.dao.htmlEditor.footer;

import com.project.model.htmlEditor.footer.Footer;

public interface FooterDAO {
    Footer getFooter();

    Footer updateFooter(Footer footer);

    Footer createFooter(Footer footer);

    void deleteFooter();

    Long getUpdateDate();
}
