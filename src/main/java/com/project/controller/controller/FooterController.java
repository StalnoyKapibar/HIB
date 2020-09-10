package com.project.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


@Controller
public class FooterController {
    @GetMapping("/guide")
    public String getGuidePage() {
        return "guide";
    }

    @GetMapping("/contacts")
    public String getContactsPage(Model model) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/footer.properties"));
        String email = properties.getProperty("email");
        String phoneNumber = properties.getProperty("phoneNumber");
        model.addAttribute("email", email);
        model.addAttribute("phoneNumber", phoneNumber);
        return "contacts";
    }

    @GetMapping("/authors")
    public String getAuthorsPage() {
        return "authors";
    }

    @GetMapping("/manual")
    public String getManualPage() {
        return "manual";
    }

    @GetMapping("/links")
    public String getLinksPage() {
        return "links";
    }
}
