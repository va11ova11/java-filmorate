package ru.yandex.practicum.filmorate.storage.film.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.advice.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.advice.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Repository
public class FilmStorageImpl implements FilmStorage {

  private final Map<Long, Film> films;
  private Long filmId = 0L;


  public FilmStorageImpl() {
    films = new HashMap<>();
  }

  @Override
  public Film get(Long id) {
    if(films.containsKey(id)) {
      return films.get(id);
    } else {
      throw new NotFoundException(String.format("Film-%s not found", id));
    }
  }

  @Override
  public Film add(Film film) {
    boolean filmExist = films.values().stream().anyMatch(f -> f.equals(film));

    if(filmExist) {
      throw new AlreadyExistException(String.format(
          "Being film - %s %s alreadyExist", film.getId(), film.getName()));
    }
    film.setId(++filmId);
    films.put(film.getId(),film);
    return film;
  }

  @Override
  public Film delete(Long id) {
    if(films.containsKey(id)) {
      Film deleteFilm = films.get(id);
      films.remove(deleteFilm.getId());
      return deleteFilm;
    } else {
      throw new NotFoundException(String.format("Being deleted Film-%s not found", id));
    }
  }

  @Override
  public Film update(Film film) {
    if(films.containsKey(film.getId())) {
      films.put(film.getId(), film);
      return film;
    } else {
      throw new NotFoundException(String.format("Updated Film-%s %s not found",
          film.getId(), film.getName()));
    }
  }

  @Override
  public Collection<Film> getAll() {
    return films.values();
  }
}
