package com.project.service;

import com.project.model.GenreDto;
import java.util.List;

public interface GenreDtoService {
    GenreDto getGenreDtoById(long id, String locale);
    List<GenreDto> getAllGenreDto(String locale);
    Long getVacantNumber();
}
