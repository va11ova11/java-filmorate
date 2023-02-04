package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.film.FilmAlreadyExist;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public class FilmStorageImpl implements FilmStorage {

  private final Map<Integer, Film> films;

  public FilmStorageImpl() {
    films = new HashMap<>();
  }

  @Override
  public Film get(Integer id) {
    if(films.containsKey(id)) {
      return films.get(id);
    } else {
      throw new FilmNotFoundException("film not found");
    }
  }

  @Override
  public Film add(Film film) {
    films.values().stream()
        .filter(f -> f.equals(film))
        .findAny()
        .orElseThrow(() -> new FilmAlreadyExist(String.format(
            "Being added film - %s already exist", film.getName()
        )));
    films.put(film.getId(), film);
    return film;
  }

  @Override
  public Film delete(Integer id) {
    if(films.containsKey(id)) {
      Film deleteFilm = films.get(id);
      films.remove(deleteFilm.getId());
      return deleteFilm;
    } else {
      throw new FilmNotFoundException("Being deleted film not found");
    }
  }

  @Override
  public Film update(Film film) {
    if(films.containsKey(film.getId())) {
      films.put(film.getId(), film);
      return film;
    } else {
      throw new FilmNotFoundException("Updated film not found");
    }
  }

  @Override
  public Collection<Film> getAll() {
    return films.values();
  }
}
