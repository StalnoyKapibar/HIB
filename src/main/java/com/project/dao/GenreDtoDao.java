package com.project.dao;

import com.project.model.GenreDto;
import java.util.List;

public interface GenreDtoDao {
    GenreDto getGenreDtoById(long id, String locale);
    List<GenreDto> getAllGenreDto(String locale);
    Long getMaxNumber();
}
