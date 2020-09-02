package com.project.service.abstraction;

import com.project.model.Book;
import com.project.model.HibFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HibFileService {
    List<Book> getAllBooks();

    Book getBookFromHibFileByName(String name);

    void deleteByName(String name);

    void bulkLoading(MultipartFile[] files);

    void deleteAllHIBs();
}
