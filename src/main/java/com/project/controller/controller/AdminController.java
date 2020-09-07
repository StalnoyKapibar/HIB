package com.project.controller.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PropertySource("${googleConfigFile}")
//@PropertySource("classpath:gmail-dev.properties")
public class AdminController {
    @Value("${authUrl}")
    private String authUrl;

    @Value("${clientId}")
    private String clientId;

    @Value("${accessType}")
    private String accessType;

    @Value("${prompt}")
    private String prompt;

    @Value("${redirectUri}")
    private String redirectUri;

    @Value("${responseType}")
    private String responseType;

    @Value("${scope}")
    private String scope;

    @GetMapping("/admin/panel/**")
    public String getAdminPage(Model model) {
        String fullUrl = String.format
                ("%s?client_id=%s&access_type=%s&prompt=%s&redirect_uri=%s&response_type=%s&scope=%s",
              authUrl, clientId, accessType, prompt, redirectUri, responseType, scope);

        model.addAttribute("gmailRedirectUrl", fullUrl);

        return "admin/admin-page";
    }

    @GetMapping("/admin/book/{hibName}")
    public String editHIBAndSave() {
        return "editPageBook";
    }

    @GetMapping("/admin/edit/{id}")
    public String getEditPage() {

        return "editPageBook";
    }

    @GetMapping("/admin/addHibFile")
    public String getHibFileAddPage(){

        return "admin/addHibFile";
    }

    @GetMapping("/admin/addBookPage")
    public String getAddBookPage(){

        return "admin/addBook";
    }


}
