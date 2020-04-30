package com.project.service;

import com.project.dao.abstraction.BookDao;
import com.project.model.*;
import com.project.service.abstraction.BookService;
import com.project.util.TransliterateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDAO;
    private final TransliterateUtil transliterateUtil;

    @Override
    public List<Book> getAllBookDTO() {
        return bookDAO.findAll();
    }

    @Override
    public Book getById(Long id) {
        return bookDAO.findById(id);
    }

    @Override
    public void addBook(Book book) {
        bookDAO.add(book);
    }

    @Override
    public PageableBookDTO getPageBookDTOByPageable(Pageable pageable, boolean disabled) {
        return bookDAO.getPageBookDTOByPageable(pageable, disabled);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDAO.deleteById(id);
    }

    @Override
    public BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang) {
        Book book = bookDAO.findById(id);
        BookNewDTO bookNewDTO = new BookNewDTO();
        String originalLanguage = book.getOriginalLanguage();
        if (originalLanguage == null) originalLanguage = lang;
        Method getStringInOriginalLanguage = null;
        try {
            getStringInOriginalLanguage = LocaleString.class
                    .getMethod("get" +
                            originalLanguage.charAt(0) +
                            Character.toLowerCase(originalLanguage.charAt(1)));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (originalLanguage.equalsIgnoreCase("OTHER")) {
            bookNewDTO.setName(book.getOtherLanguageOfBook().getOtherLangNameBook());
            bookNewDTO.setTransitName(book.getOtherLanguageOfBook().getTranslitNameBook());
            bookNewDTO.setAuthor(book.getOtherLanguageOfBook().getOtherLangAuthor());
            bookNewDTO.setTransitAuthor(book.getOtherLanguageOfBook().getTranslitAuthor());
            bookNewDTO.setEdition(book.getEdition().getEn());
            bookNewDTO.setEditionTranslite(transliterateUtil.transliterate(book.getEdition().getEn()));
        } else {
            try {
                LocaleString name = book.getName();
                LocaleString author = book.getAuthor();
                LocaleString edition = book.getEdition();
                bookNewDTO.setName((String) getStringInOriginalLanguage.invoke(name));
                bookNewDTO.setTransitName(transliterateUtil.transliterate((String) getStringInOriginalLanguage.invoke(name)));
                bookNewDTO.setAuthor((String) getStringInOriginalLanguage.invoke(author));
                bookNewDTO.setTransitAuthor(transliterateUtil.transliterate((String) getStringInOriginalLanguage.invoke(author)));
                bookNewDTO.setEdition((String) getStringInOriginalLanguage.invoke(edition));
                bookNewDTO.setEditionTranslite(transliterateUtil.transliterate((String) getStringInOriginalLanguage.invoke(edition)));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        bookNewDTO.setId(book.getId());
        bookNewDTO.setCoverImage(book.getCoverImage());
        bookNewDTO.setImageList(book.getListImage());
        try {
            if (getStringInOriginalLanguage != null) {
                bookNewDTO.setDesc((String) getStringInOriginalLanguage.invoke(book.getDescription()));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        bookNewDTO.setPages(book.getPages());
        bookNewDTO.setYearOfEdition(book.getYearOfEdition());
        bookNewDTO.setOriginalLanguage(book.getOriginalLanguage());
        bookNewDTO.setPrice(book.getPrice());
        return bookNewDTO;
    }

    @Override
    public void updateBook(Book book) {
        bookDAO.update(book);
    }

    @Override
    public List<BookDTO20> get20BookDTO(String locale) {
        List<BookDTO20> bookDTOList = bookDAO
                .findAll()
                .stream()
                .map(book -> {
                    BookDTO20 bookDTO20 = new BookDTO20();
                    String originalLanguage = book.getOriginalLanguage();
                    if (originalLanguage == null) originalLanguage = locale.toUpperCase();
                    if (originalLanguage.equalsIgnoreCase("OTHER")) {
                        bookDTO20.setNameBookDTOLocale(book.getOtherLanguageOfBook().getOtherLangNameBook());
                        bookDTO20.setNameBookTransliteDTOLocale(book.getOtherLanguageOfBook().getTranslitNameBook());
                        bookDTO20.setNameAuthorDTOLocale(book.getOtherLanguageOfBook().getOtherLangAuthor());
                        bookDTO20.setNameAuthorTransliteDTOLocale(book.getOtherLanguageOfBook().getTranslitAuthor());
                    } else {
                        try {
                            LocaleString name = book.getName();
                            LocaleString author = book.getAuthor();
                            Method getStringInOriginalLanguage = LocaleString.class
                                    .getMethod("get" +
                                            originalLanguage.charAt(0) +
                                            Character.toLowerCase(originalLanguage.charAt(1)));
                            bookDTO20.setNameBookDTOLocale((String) getStringInOriginalLanguage.invoke(name));
                            bookDTO20.setNameBookTransliteDTOLocale(transliterateUtil.transliterate((String) getStringInOriginalLanguage.invoke(name)));
                            bookDTO20.setNameAuthorDTOLocale((String) getStringInOriginalLanguage.invoke(author));
                            bookDTO20.setNameAuthorTransliteDTOLocale(transliterateUtil.transliterate((String) getStringInOriginalLanguage.invoke(author)));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    bookDTO20.setCoverImage(book.getCoverImage());
                    bookDTO20.setId(book.getId());
                    bookDTO20.setPrice(book.getPrice());
                    return bookDTO20;
                }).collect(Collectors.toList());
        return bookDTOList;
    }

    @Override
    public BookDTO20 getBookBySearchRequest(LocaleString localeString, String locale) {
        return bookDAO.getBookBySearchRequest(localeString, locale);
    }

    @Override
    public Book getBookById(Long id) {
        return bookDAO.findById(id);
    }

    @Override
    public List<BookDTOForCategories> getBooksByCategoryId(Long categoryId, String lang) {
        return bookDAO.getBooksByCategoryId(categoryId, lang);
    }

    @Override
    public String getLastIdOfBook() {
        return bookDAO.getLastIdOfBook();
    }
}
