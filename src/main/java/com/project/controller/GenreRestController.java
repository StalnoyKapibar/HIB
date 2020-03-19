package com.project.controller;

import com.project.model.Genre;
import com.project.model.GenreDto;
import com.project.service.GenreDtoService;
import com.project.service.GenreService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class GenreRestController {

    private GenreService genreService;
    private GenreDtoService genreDtoService;

    public GenreRestController(GenreService genreService, GenreDtoService genreDtoService) {
        this.genreService = genreService;
        this.genreDtoService = genreDtoService;
    }

    @GetMapping("/genresdto")
    public List<GenreDto> getAllGenresDto(HttpServletResponse response, HttpServletRequest request) {
        List<GenreDto> genres = genreDtoService.getAllGenreDto ((String) request.getSession().getAttribute("LANG"));
        if (genres != null) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return genres;
    }

    @GetMapping("/genredto/{id}")
    public GenreDto getGenreById(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request) {
        GenreDto genre = genreDtoService.getGenreDtoById(id, (String) request.getSession().getAttribute("LANG"));
        if (genre != null) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return genre;
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres(HttpServletResponse response) {
        List<Genre> genres = genreService.getAllGenres();
        if (genres != null) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return genres;
    }

    @PostMapping("/genre")
    public void addGenre(@RequestBody Genre genre, HttpServletResponse response) {
        Long new_id = genreService.addGenre(genre);
        if (new_id != null) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.addHeader("new_id", new_id.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/genre/{id}")
    public void deleteGenreById(@PathVariable Long id, HttpServletResponse response) {
        if (genreService.getGenreById(id) != null) {
            genreService.deleteGenreById(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/genre/{id}")
    public void editGenreById(@RequestBody Genre genre, @PathVariable Long id, HttpServletResponse response) {
        Long editedId = genreService.addGenre(new Genre(id, genre.getGenreLocale()));
        if (editedId != null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/genre/{oldId}/{newId}")
    public void changingGenreOrderById(@PathVariable long oldId, @PathVariable long newId, HttpServletResponse response) {
        if (genreService.changingGenreOrderById(oldId, newId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }
}
