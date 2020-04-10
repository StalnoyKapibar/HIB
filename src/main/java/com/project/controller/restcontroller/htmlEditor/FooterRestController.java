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
@RequestMapping("/api/admin/html/footer")
public class FooterRestController {
    private final FooterService footerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(FooterRestController.class.getName());

    @GetMapping
    public Footer getFooter() {
        return footerService.getFooter();
    }

    @GetMapping("/update-date")
    public Long getFooterUpdateDate() {
        return footerService.getUpdateDate();
    }

    @PostMapping
    public Footer createFooter(@RequestBody Footer footer) {
        LOGGER.debug("POST request '/api/admin/html/footer' with {}", footer);
        footer.setId(null);
        footer.setUpdateDate(new Date().getTime());
        return footerService.createFooter(footer);
    }
}
