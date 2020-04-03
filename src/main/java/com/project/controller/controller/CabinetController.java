package com.project.controller.controller;

import com.project.model.UserAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CabinetController {

    @Value("${googleKey}")
    private String key;

    @Value("${showAddressAutocomplete}")
    private boolean showAddressAutocomplete;

    @GetMapping("/cabinet")
    public String getCabinetPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String firstName = ((UserAccount) authentication.getPrincipal()).getFirstName();
        String lastName = ((UserAccount) authentication.getPrincipal()).getLastName();
        String url = "https://maps.googleapis.com/maps/api/js?key="
                + key + "&libraries=places&callback=initAutocomplete";
        model.addAttribute("disabledAutocomplete", !showAddressAutocomplete);
        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);
        model.addAttribute("key", url);
        return "cabinet";
    }
}
