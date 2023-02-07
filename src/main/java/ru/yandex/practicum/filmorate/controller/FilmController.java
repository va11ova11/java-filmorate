package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

  private final FilmService filmService;

  @PostMapping
  public Film addFilm(@RequestBody @NotNull @Valid Film film) {
    return filmService.addFilm(film);
  }

  @PutMapping
  public Film updateFilm(@RequestBody @NotNull @Valid Film film) {
    return filmService.updateFilm(film);
  }

  @GetMapping
  public Collection<Film> findAll() {
    return filmService.getAll();
  }

  @GetMapping("/{id}")
  public Film getFilmById(@PathVariable Long id) {
    return filmService.getFilmById(id);
  }

  @PutMapping("/{id}/like/{userId}")
  public Film likeFilmByUserId(@NotNull @Positive @PathVariable Long id,
                           @NotNull @Positive @PathVariable Long userId) {
    return filmService.likeFilmByUserId(id, userId);
  }

  @DeleteMapping("/{id}/like/{userId}")
  public Film deleteLikeFilmByUserId(@NotNull @Positive @PathVariable Long id,
                                    @NotNull @Positive @PathVariable Long userId) {
    return filmService.deleteLikeFilmByUserId(id, userId);
  }
  @GetMapping("/popular")
  public List<Film> getPopularFilms(@NotNull @Positive
  @RequestParam(name = "count", required = false, defaultValue = "10") Integer count) {
    return filmService.getPopularFilms(count);
  }
}
