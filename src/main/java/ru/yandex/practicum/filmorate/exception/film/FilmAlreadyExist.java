package ru.yandex.practicum.filmorate.exception.film;

public class FilmAlreadyExist extends RuntimeException {
  public FilmAlreadyExist(String mes) {
    super(mes);
  }
}
