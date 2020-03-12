package com.project.service;

import com.project.dao.GenreDtoDao;
import com.project.model.GenreDto;
//import com.project.util.LocaleHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("genreDtoService")
@Transactional
public class GenreDtoServiceImpl implements GenreDtoService {

    private GenreDtoDao genreDtoDao;

    public GenreDtoServiceImpl(GenreDtoDao genreDtoDao) {
        this.genreDtoDao = genreDtoDao;
    }

    @Override
    public GenreDto getGenreDtoById(long id, String locale) {
        return genreDtoDao.getGenreDtoById(id, locale);
    }

    @Override
    public List<GenreDto> getAllGenreDto(String locale) {
        return genreDtoDao.getAllGenreDto(locale);
    }
}
