package com.project.service;

import com.project.dao.GenreDao;
import com.project.exception.ResourceNotFoundException;
import com.project.model.Genre;
import com.project.model.LocaleString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("genreService")
@Transactional
public class GenreServiceImpl implements GenreService {

    private GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.findById(id).orElse(null);
    }

    @Override
    public Long addGenre(Genre genre) {
        return genreDao.saveAndFlush(genre).getId();
    }

    @Override
    public void deleteGenreById(long id) {
        genreDao.deleteGenreById(id);
    }

    @Override
    public boolean changingGenreOrderById(long oldId, long newId) {
        Genre gTemp1, gTemp2;
        LocaleString lTemp1, lTemp2;
        try {
            gTemp1 = genreDao.findById(newId).orElseThrow(() -> new ResourceNotFoundException(newId));
            gTemp2 = genreDao.findById(oldId).orElseThrow(() -> new ResourceNotFoundException(oldId));

            lTemp1 = gTemp1.getGenreLocale();
            lTemp2 = gTemp2.getGenreLocale();

            gTemp1.setGenreLocale(lTemp2);
            gTemp2.setGenreLocale(lTemp1);
        } catch (ResourceNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }
}
