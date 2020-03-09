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
    //private LocaleHolder localeHolder;

    public GenreDtoServiceImpl(GenreDtoDao genreDtoDao) {
        this.genreDtoDao = genreDtoDao;
        //this.localeHolder = localeHolder;
    }

    @Override
    public GenreDto getGenreDtoById(long id) {
        return genreDtoDao.getGenreDtoById(id, "ru");
    }

    @Override
    public List<GenreDto> getAllGenreDto() {
        return genreDtoDao.getAllGenreDto("ru");
    }
}
