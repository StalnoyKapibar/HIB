package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.FooterContactsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Api(tags = "This is the REST-API documentation for Footer contacts.")
@RestController
@RequestMapping("/contacts")
public class FooterContactsRestController {

    @ApiOperation(value = "Get contacts for Footer:"
            , notes = "This endpoint returns the contacts list for footer"
            , response = List.class, tags = "getFooterContacts")
    @GetMapping()
    public ResponseEntity<List<FooterContactsDTO>> getFooterContacts(Model model) throws IOException {
        List<FooterContactsDTO> contacts = new ArrayList<>();
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/footer.properties"));
        String email = properties.getProperty("email");
        String phoneNumber = properties.getProperty("phoneNumber");
        contacts.add(new FooterContactsDTO(email, phoneNumber));
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}
