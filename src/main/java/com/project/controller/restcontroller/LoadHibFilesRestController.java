package com.project.controller.restcontroller;

import com.project.HIBParser.HibParser;
import com.project.model.BookDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoadHibFilesRestController {
    private final HibParser hibParser;

    @PostMapping("/api/loadFile")
    public BookDTO loadFile(@RequestBody String book) {
        return hibParser.getBookFromJSON(book);
    }
}
