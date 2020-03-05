package com.project.dao;

import com.project.model.GenreDto;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.util.List;

@Repository
public class GenreDtoDaoImpl implements GenreDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GenreDto getGenreDtoById(long id, String locale) {
        String temp = "Select new com.project.model.GenreDto(g.id, g.body.LOC) FROM Genre g WHERE g.id = :genreId".replaceAll("LOC", locale);
        return entityManager.createQuery(temp, GenreDto.class).setParameter("genreId", id).getSingleResult();
    }

    @Override
    public List<GenreDto> getAllGenreDto(String locale) {
        String temp = "Select new com.project.model.GenreDto(g.id, g.body.LOC) FROM Genre g".replaceAll("LOC", locale);
        return entityManager.createQuery(temp, GenreDto.class).getResultList();
    }
}
