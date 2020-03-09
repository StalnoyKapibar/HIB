package com.project.service;

import com.project.dao.GenreDao;
import com.project.exception.ResourceNotFoundException;
import com.project.model.Genre;
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
        genreDao.deleteById(id);
        //genreDao.deleteGenreById(id);
    }

    @Override
    public boolean changingGenreOrderById(long oldId, long newId) {
        Genre temp;
        try {
            temp = genreDao.findById(newId).orElseThrow(() -> new ResourceNotFoundException(oldId));
            genreDao.saveAndFlush(
                    new Genre(
                            newId,
                            genreDao.findById(oldId).orElse(null).getLocale()
                    )
            );
            genreDao.saveAndFlush(
                    new Genre(
                            oldId,
                            temp.getLocale()
                    )
            );
        } catch (ResourceNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }
}
