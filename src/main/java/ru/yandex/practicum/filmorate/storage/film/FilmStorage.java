package ru.yandex.practicum.filmorate.storage.film;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
  Film get(Long id);

  Film add(Film film);

  Film delete(Long id);

  Film update(Film film);

  Collection<Film> getAll();
}
