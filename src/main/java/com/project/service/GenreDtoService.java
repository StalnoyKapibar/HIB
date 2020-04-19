package com.project.service;

import com.project.model.GenreDto;

import java.util.List;

public interface GenreDtoService {
    GenreDto getGenreDtoById(long id, String locale);

    List<GenreDto> getAllGenresDto(String locale);

    Long getVacantNumber();
}
