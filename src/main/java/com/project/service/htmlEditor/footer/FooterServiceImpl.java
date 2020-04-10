package com.project.service.htmlEditor.footer;

import com.project.dao.htmlEditor.footer.FooterDAO;
import com.project.model.htmlEditor.footer.Footer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FooterServiceImpl implements FooterService {
    private final FooterDAO footerDAO;

    @Override
    public Footer getFooter() {
        return footerDAO.getFooter();
    }

    @Override
    public Footer updateFooter(Footer footer) {
        return footerDAO.updateFooter(footer);
    }

    @Transactional
    @Override
    public Footer createFooter(Footer footer) {
        return footerDAO.createFooter(footer);
    }

    @Override
    public void deleteFooter() {
        footerDAO.deleteFooter();
    }

    @Override
    public Long getUpdateDate() {
        return footerDAO.getUpdateDate();
    }
}
