package com.project.controller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooterController {
    @GetMapping("/guide")
    public String getGuidePage(){
        return "guide";
    }
    @GetMapping("/contacts")
    public String getContactsPage(){
        return "contacts";
    }
    @GetMapping("/authors")
    public String getAuthorsPage(){
        return "authors";
    }
    @GetMapping("/manual")
    public String getManualPage(){
        return "manual";
    }
    @GetMapping("/links")
    public String getLinksPage(){
        return "links";
    }
}
