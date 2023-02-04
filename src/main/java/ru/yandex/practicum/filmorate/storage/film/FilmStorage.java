package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
  Film get(Integer id);

  Film add(Film film);

  Film delete(Integer id);

  Film update(Film film);

  Collection<Film> getAll();
}
