package com.project.controller.controller;

import com.project.model.UserAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Represented controller for binding current user with google API key.
 * You must include an API key with every Maps JavaScript API request.
 * @see <a href = "https://developers.google.com/maps/documentation/javascript/get-api-key">
 *     https://developers.google.com/maps/documentation/javascript/get-api-key</a>
 */
@Controller
public class CabinetController {

    @Value("${googleKey}")
    private String key;

    @Value("${showAddressAutocomplete}")
    private boolean showAddressAutocomplete;

    /**
     * @return page with address form
     */
    @GetMapping("/cabinet")
    public String getCabinetPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String firstName = ((UserAccount) authentication.getPrincipal()).getFirstName();
        String lastName = ((UserAccount) authentication.getPrincipal()).getLastName();
        if (showAddressAutocomplete) {
            String url = "https://maps.googleapis.com/maps/api/js?key="
                    + key + "&libraries=places&callback=initAutocomplete";
            model.addAttribute("key", url);
        }
        model.addAttribute("disabledAutocomplete", !showAddressAutocomplete);
        model.addAttribute("lastName", lastName);
        model.addAttribute("firstName", firstName);
        return "cabinet";
    }
}
