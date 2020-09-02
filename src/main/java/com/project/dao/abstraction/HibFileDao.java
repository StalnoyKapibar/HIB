package com.project.dao.abstraction;

import com.project.model.Book;

import java.io.File;
import java.util.List;

public interface HibFileDao {
    List<Book> getAllBooks();

    void deleteByName(String name);

    void saveHibFile(File file, byte[] data);
}
