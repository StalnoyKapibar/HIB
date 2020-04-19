package com.project.service;

import com.project.dao.GenreDao;
import com.project.model.Genre;
import com.project.model.LocaleString;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Service("genreService")
@AllArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {

    private GenreDao genreDao;

    @Override
    public Genre getGenreById(long id) {
        return genreDao.findById(id).orElseThrow(() -> new NoResultException("Genre with id=" + id + " not found"));
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }

    @Override
    public void addGenre(Genre genre) {
        genreDao.saveAndFlush(genre);
    }

    @Override
    public void deleteGenreById(long id) {
        genreDao.deleteGenreById(id);
    }

    @Override
    public void changingGenreOrderById(long oldId, long newId) {
        Genre gTemp1, gTemp2;
        LocaleString lTemp1, lTemp2;

        gTemp1 = genreDao.findById(newId).orElseThrow(() -> new NoResultException("Genre with id=" + newId + " not found"));
        gTemp2 = genreDao.findById(oldId).orElseThrow(() -> new NoResultException("Genre with id=" + oldId + " not found"));

        lTemp1 = gTemp1.getGenreLocale();
        lTemp2 = gTemp2.getGenreLocale();

        gTemp1.setGenreLocale(lTemp2);
        gTemp2.setGenreLocale(lTemp1);
    }
}
