package com.project.controller.restcontroller;

import com.project.model.Genre;
import com.project.model.GenreDto;
import com.project.service.GenreDtoService;
import com.project.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@AllArgsConstructor
@RestController
public class GenreController {

    private GenreService genreService;
    private GenreDtoService genreDtoService;

    @GetMapping("/genresdto")
    public List<GenreDto> getAllGenresDto(HttpServletResponse response, HttpServletRequest request) {
        List<GenreDto> genresDto = genreDtoService.getAllGenresDto((String) request.getSession().getAttribute("LANG"));
        response.setStatus(HttpServletResponse.SC_OK);
        return genresDto;
    }

    @GetMapping("/genredto/{id}")
    public GenreDto getGenreDtoById(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request) {
        GenreDto genreDto = genreDtoService.getGenreDtoById(id, (String) request.getSession().getAttribute("LANG"));
        response.setStatus(HttpServletResponse.SC_OK);
        return genreDto;
    }

    @GetMapping("/vacantnum")
    public Long getVacantNumber(HttpServletResponse response, HttpServletRequest request) {
        Long vacantNum = genreDtoService.getVacantNumber();
        response.setStatus(HttpServletResponse.SC_OK);
        return vacantNum;
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres(HttpServletResponse response) {
        List<Genre> genres = genreService.getAllGenres();
        response.setStatus(HttpServletResponse.SC_OK);
        return genres;
    }

    @GetMapping("/genre/{id}")
    public Genre getGenreById(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request) {
        Genre genre = genreService.getGenreById(id);
        response.setStatus(HttpServletResponse.SC_OK);
        return genre;
    }

    @PostMapping("/genre")
    public void addGenre(@RequestBody Genre genre, HttpServletResponse response) {
        genreService.addGenre(genre);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @DeleteMapping("/genre/{id}")
    public void deleteGenreById(@PathVariable Long id, HttpServletResponse response) {
        genreService.deleteGenreById(id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @PutMapping("/genre/{id}")
    public void editGenreById(@RequestBody Genre genre, @PathVariable Long id, HttpServletResponse response) {
        genreService.addGenre(new Genre(id, genre.getNumber(), genre.getGenreLocale()));
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @PutMapping("/genre/{oldId}/{newId}")
    public void changingGenreOrderById(@PathVariable long oldId, @PathVariable long newId, HttpServletResponse response) {
        genreService.changingGenreOrderById(oldId, newId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
