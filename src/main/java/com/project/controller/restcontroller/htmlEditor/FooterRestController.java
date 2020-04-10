package com.project.controller.restcontroller.htmlEditor;

import com.project.model.htmlEditor.footer.Footer;
import com.project.service.htmlEditor.footer.FooterService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@AllArgsConstructor
public class FooterRestController {
    private final FooterService footerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(FooterRestController.class.getName());
    private static final String API_ADMIN_FOOTER = "/api/admin/html/footer";
    private static final String API_FOOTER = "/api/html/footer";

    @GetMapping(API_FOOTER)
    public Footer getFooter() {
        return footerService.getFooter();
    }

    @GetMapping(API_FOOTER + "/update-date")
    public Long getFooterUpdateDate() {
        return footerService.getUpdateDate();
    }

    @PostMapping(API_ADMIN_FOOTER)
    public Footer createFooter(@RequestBody Footer footer) {
        LOGGER.debug("POST request '" + API_ADMIN_FOOTER + "' with {}", footer);
        footer.setId(null);
        footer.setUpdateDate(new Date().getTime());
        return footerService.createFooter(footer);
    }

    @PutMapping(API_ADMIN_FOOTER)
    public Footer editFooter(@RequestBody Footer footer) {
        LOGGER.debug("PUT request '" + API_ADMIN_FOOTER + "' with {}", footer);
        LOGGER.info("PUT request '" + API_ADMIN_FOOTER + "' with {}", footer);
        footer.setId(1L);
        return footerService.updateFooter(footer);
    }

}
