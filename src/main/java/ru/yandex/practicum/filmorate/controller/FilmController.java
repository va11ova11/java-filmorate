package ru.yandex.practicum.filmorate.controller;

import static ru.yandex.practicum.filmorate.validator.FilmValidator.validateFilm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

  private final Map<Integer, Film> films = new HashMap<>();
  private int filmId = 0;

  @PostMapping
  public Film addFilm(@RequestBody @NotNull @Valid Film film) throws ValidationException {
    validateFilm(film);
    film = film.toBuilder().id(++filmId).build();
    films.put(film.getId(), film);
    log.trace("Validation has been successfully, the new movie has been successfully added.");
    return film;
  }

  @PutMapping
  public Film updateFilm(@RequestBody @NotNull @Valid Film film)
      throws ValidationException, NotFoundException {
    validateFilm(film);
    if (films.containsKey(film.getId())) {
      films.put(film.getId(), film);
      log.trace("The movie has been successfully updated.");
      return film;
    }
    throw new NotFoundException("Updated movie does not exist.");
  }

  @GetMapping
  public Collection<Film> findAll() {
    log.trace("The list of films has been received.");
    return films.values();
  }
}
