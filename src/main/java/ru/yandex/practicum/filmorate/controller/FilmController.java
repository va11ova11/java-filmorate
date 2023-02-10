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
  @DeleteMapping("/{id}")
  public Film deleteFilmById(@PathVariable @NotNull @Positive Long id) {
    return filmService.deleteFilmById(id);
  }

  @GetMapping
  public Collection<Film> getAll() {
    return filmService.getAll();
  }

  @GetMapping("/{id}")
  public Film getFilmById(@PathVariable @NotNull Long id) {
    return filmService.getFilmById(id);
  }

  @PutMapping("/{id}/like/{userId}")
  public Film likeFilm(@NotNull @Positive @PathVariable Long id,
                           @NotNull @Positive @PathVariable Long userId) {
    return filmService.likeByFilmIdAndUserId(id, userId);
  }

  @DeleteMapping("/{id}/like/{userId}")
  public Film deleteLike(@NotNull @Positive @PathVariable Long id,
                                  @NotNull @Positive @PathVariable Long userId) {
    return filmService.deleteLikeByFilmIdAndUserId(id, userId);
  }
  @GetMapping("/popular")
  public List<Film> getPopularFilms(
      @RequestParam(name = "count", required = false, defaultValue = "10")
      @NotNull @Positive Integer count) {
    return filmService.getPopularFilms(count);
  }
}
