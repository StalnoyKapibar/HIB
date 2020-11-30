package com.project.service;

import com.project.dao.abstraction.BookDao;
import com.project.model.*;
import com.project.util.TransliterateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Primary
@Transactional
@Repository("withAutoFillOriginalLanguageNameRows")
public class BookServiceImplWithAutoFillOriginalLanguageRows extends BookServiceImpl {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookServiceImplWithAutoFillOriginalLanguageRows.class.getName());
    private final TransliterateUtil transliterateUtil;
    private final BookDao bookDao;

    @Autowired
    public BookServiceImplWithAutoFillOriginalLanguageRows(TransliterateUtil transliterateUtil, @Qualifier("withNewSystemDisplayBook") BookDao bookDao) {
        this.transliterateUtil = transliterateUtil;
        this.bookDao = bookDao;
    }

    @Override
    public void addBook(Book book) {
        Method getStringInOriginalLanguageName;
        if (book.getOriginalLanguageName() == null) book.setOriginalLanguageName("OTHER");
        if (!book.getOriginalLanguageName().equalsIgnoreCase("OTHER")) {
            if (book.getOriginalLanguage() == null) book.setOriginalLanguage(new OriginalLanguage());
            try {
                String bookName, bookAuthor, bookEdition;
                OriginalLanguage bookOriginalLanguage = book.getOriginalLanguage();
                getStringInOriginalLanguageName = LocaleString.class.getMethod("get" + book.getOriginalLanguageName().charAt(0)
                        + Character.toLowerCase(book.getOriginalLanguageName().charAt(1)));
                bookName = (String) getStringInOriginalLanguageName.invoke(book.getName());
                bookAuthor = (String) getStringInOriginalLanguageName.invoke(book.getAuthor());
                bookEdition = (String) getStringInOriginalLanguageName.invoke(book.getEdition());
                bookOriginalLanguage.setName(bookName);
                bookOriginalLanguage.setAuthor(bookAuthor);
                bookOriginalLanguage.setEdition(bookEdition);
                bookOriginalLanguage.setNameTranslit(transliterateUtil.transliterate(bookName));
                bookOriginalLanguage.setAuthorTranslit(transliterateUtil.transliterate(bookAuthor));
                bookOriginalLanguage.setEditionTranslit(transliterateUtil.transliterate(bookEdition));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.error("Reflection error occurred", e);
            }
        }
        super.addBook(book);
    }

    @Override
    public BookNewDTO getNewBookDTOByIdAndLang(Long id, String lang) {
        return bookDao.getNewBookDTObyIdAndLang(id, lang);
    }

    @Override
    public List<BookDTO> get20BookDTO(String locale) {
        return bookDao.get20BookDTO(locale);
    }
}
