package com.project.service;

import com.project.model.Genre;

public interface GenreService {

    Genre getGenreById(long id);

    Long addGenre(Genre genre);

    void deleteGenreById(long id);

    boolean changingGenreOrderById(long oldId, long newId);
}
