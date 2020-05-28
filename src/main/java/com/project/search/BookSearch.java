package com.project.search;

import com.project.dao.abstraction.BookDao;
import com.project.model.BookNewDTO;
import com.project.model.BookPageDto;
import com.project.model.BookSearchPageDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
        List<BookNewDTO> result = bookDao.getBookBySearchRequestAdvanced(req.trim());
        return result;
    }

    public BookSearchPageDTO searchByParameters(String req, Long priceFrom, Long priceTo, String yearOfEditionFrom, String yearOfEditionTo, Long pagesFrom,
                                                Long pagesTo, String searchBy, List<String> categories, Pageable pageable) {
        BookSearchPageDTO result = bookDao.getBookBySearchRequest(req.trim(), priceFrom, priceTo, yearOfEditionFrom, yearOfEditionTo, pagesFrom, pagesTo, searchBy, categories, pageable);
        return result;
    }

    public List<BookNewDTO> searchAdmin(String req, boolean isShow) {
        List<BookNewDTO> result = bookDao.getBookBySearchRequest(req.trim(), isShow);
        return result;
    }
}
