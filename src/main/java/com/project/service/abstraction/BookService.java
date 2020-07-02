package com.project.service.abstraction;

import com.project.model.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface BookService {
    List<Book> getAllBookDTO();

    Book getById(Long id);

    void addBook(Book book);

    BookPageAdminDto getPageBookDTOByPageable(Pageable pageable, boolean disabled);

    void deleteBookById(Long id);

    void updateBook(Book book);

    List<BookDTO> get20BookDTO(String locale);

    Book getBookById(Long id);

    List<BookNewDTO> getBooksByCategoryId(Long categoryId);

    List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang);

    Long getCountBooksByCategoryId(Long categoryId, boolean isShow);

    String getLastIdOfBook();

    BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang);

    BookPageDto getBookPageByPageable(Pageable pageable);

    List<BookNewDTO> getAllBooksSearchPage();

    List<Long> getAllLastOrderedBooks();

    void setLastOrderedBooks(List<Long> list);

    List<BookNewDTO>getAllLightBookDtoForSearch();

    Long getSizeOfTotalBooks();

    public Set<String> getAuthorSet();
}
