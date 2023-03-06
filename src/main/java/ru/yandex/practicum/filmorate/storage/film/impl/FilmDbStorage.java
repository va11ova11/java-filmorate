package ru.yandex.practicum.filmorate.storage.film.impl;

import java.util.Collection;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Component
public class FilmDbStorage implements FilmStorage {
  private final JdbcTemplate jdbcTemplate;

  public FilmDbStorage(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Film get(Long id) {
    return null;
  }

  @Override
  public Film add(Film film) {
    return null;
  }

  @Override
  public Film delete(Long id) {
    return null;
  }

  @Override
  public Film update(Film film) {
    return null;
  }

  @Override
  public Collection<Film> getAll() {
    return null;
  }
}
