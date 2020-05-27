package com.project.search;

import com.project.dao.abstraction.BookDao;
import com.project.model.BookNewDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
public class BookSearch {

    @Autowired
    private final BookDao bookDao;

    public List<BookNewDTO> searchByCategory(Long categoryId) {
        List<BookNewDTO> result = bookDao.getBooksByCategoryId(categoryId);

        return result;
    }

    public List<BookNewDTO> searchSidebar(String req) {
        List<BookNewDTO> result = bookDao.getBookBySearchRequestAdvanced(req);
        return result;
    }

    public List<BookNewDTO> searchByParameters(String req, Long priceFrom, Long priceTo, String yearOfEditionFrom, String yearOfEditionTo, Long pagesFrom,
                                   Long pagesTo, String searchBy, List<String> categories) {
        List<BookNewDTO> result = bookDao.getBookBySearchRequest(req, priceFrom, priceTo, yearOfEditionFrom, yearOfEditionTo, pagesFrom, pagesTo, searchBy, categories);
        return result;
    }

    public List<BookNewDTO> searchAdmin(String req, boolean isShow) {
        List<BookNewDTO> result = bookDao.getBookBySearchRequest(req, isShow);
        return result;
    }
}
