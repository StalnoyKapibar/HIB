package com.project.controller.restcontroller.htmlEditor;

import com.project.model.htmlEditor.footer.Footer;
import com.project.service.htmlEditor.footer.FooterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/html/footer")
public class FooterRestController {
    private final FooterService footerService;

    @GetMapping
    public Footer getFooter() {
        return footerService.getFooter();
    }

    @PostMapping
    public Footer createFooter(@RequestBody Footer footer) {
        footer.setId(null);
        return footerService.createFooter(footer);
    }
}
