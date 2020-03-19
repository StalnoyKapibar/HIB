package com.project.service;

import com.project.model.Genre;
import java.util.List;

public interface GenreService {

    Genre getGenreById(long id);

    List<Genre> getAllGenres();

    Long addGenre(Genre genre);

    void deleteGenreById(long id);

    boolean changingGenreOrderById(long oldId, long newId);
}
