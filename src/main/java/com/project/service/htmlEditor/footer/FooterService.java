package com.project.service.htmlEditor.footer;

import com.project.model.htmlEditor.footer.Footer;

public interface FooterService {
    Footer getFooter();

    Footer updateFooter(Footer footer);

    Footer createFooter(Footer footer);

    void deleteFooter();

    Long getUpdateDate();
}
