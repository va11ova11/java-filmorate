package ru.yandex.practicum.filmorate.exception.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;

public class FilmNotFoundException extends NotFoundException {
  public FilmNotFoundException(String mes) {
    super(mes);
  }
}
