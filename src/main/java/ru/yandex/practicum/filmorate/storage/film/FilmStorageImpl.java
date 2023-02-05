package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.advice.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.advice.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

@Component
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
      throw new NotFoundException("film not found");
    }
  }

  @Override
  public Film add(Film film) {
    boolean filmExist = films.values().stream().anyMatch(f -> f.equals(film));

    if(filmExist) {
      throw new AlreadyExistException(String.format(
          "Being film - %s alreadyExist", film.getName()));
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
      throw new NotFoundException("Being deleted film not found");
    }
  }

  @Override
  public Film update(Film film) {
    if(films.containsKey(film.getId())) {
      films.put(film.getId(), film);
      return film;
    } else {
      throw new NotFoundException("Updated film not found");
    }
  }

  @Override
  public Collection<Film> getAll() {
    return films.values();
  }
}
