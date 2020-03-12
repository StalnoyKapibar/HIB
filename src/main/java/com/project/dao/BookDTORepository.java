package com.project.dao;

import com.project.model.Book;
import com.project.model.BookDTO;
import com.project.model.BookNewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookDTORepository extends CrudRepository<Book, Long> {

    @Query(value = "Select new com.project.model.BookNewDTO(b.id, b.nameLocale, b.authorLocale, b.coverImage, b.listImage) FROM Book b " )
    Page<BookNewDTO> findAll(Pageable pageable);
}