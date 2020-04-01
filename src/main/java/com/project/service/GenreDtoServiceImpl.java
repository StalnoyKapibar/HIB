package com.project.service;

import com.project.dao.GenreDtoDao;
import com.project.model.GenreDto;
//import com.project.util.LocaleHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
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
        List<GenreDto> genresDto = genreDtoDao.getAllGenreDto(locale);
        genresDto.sort((o1, o2) -> (int) (o1.getNumber() - o2.getNumber()));
        return genresDto;
    }

    @Override
    public Long getVacantNumber() {
        Long maxNum = genreDtoDao.getMaxNumber();
        return maxNum == null ? 1L : maxNum + 1;
    }
}
