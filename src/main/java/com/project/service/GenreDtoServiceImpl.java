package com.project.service;

import com.project.dao.GenreDtoDao;
import com.project.model.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Service("genreDtoService")
@AllArgsConstructor
@Transactional
public class GenreDtoServiceImpl implements GenreDtoService {

    private GenreDtoDao genreDtoDao;

    @Override
    public GenreDto getGenreDtoById(long id, String locale) {
        return genreDtoDao.getGenreDtoById(id, locale);
    }

    @Override
    public List<GenreDto> getAllGenresDto(String locale) {
        List<GenreDto> genresDto = genreDtoDao.getAllGenresDto(locale);
        genresDto.sort((o1, o2) -> (int) (o1.getNumber() - o2.getNumber()));
        return genresDto;
    }

    @Override
    public Long getVacantNumber() {
        try {
            return genreDtoDao.getMaxNumber() + 1;
        } catch (NoResultException e) {
            return 1L;
        }
    }
}
