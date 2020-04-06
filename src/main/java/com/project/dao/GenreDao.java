package com.project.dao;

import com.project.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreDao extends JpaRepository<Genre, Long> {
    void deleteGenreById(Long id);
}
