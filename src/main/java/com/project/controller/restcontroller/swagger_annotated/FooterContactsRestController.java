package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.FooterContactsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Api(tags = "REST-API документ для Footer contacts.")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/contacts")
public class FooterContactsRestController {

    @ApiOperation(value = "Получить контакты для футера"
            , notes = "Этот ендпойнт возвращает список контактов для футера из файла 'footer.properties'"
            , response = FooterContactsDTO.class
            , responseContainer = "List"
            , tags = "getFooterContacts")
    @GetMapping()
    public ResponseEntity<List<FooterContactsDTO>> getFooterContacts() throws IOException {
        List<FooterContactsDTO> contacts = new ArrayList<>();
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/footer.properties"));
        String email = properties.getProperty("email");
        String phoneNumber = properties.getProperty("phoneNumber");
        contacts.add(new FooterContactsDTO(email, phoneNumber));
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}
