package ru.yandex.practicum.filmorate.validator;

import java.time.LocalDate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmValidator {
  public void validateFilm(Film film) throws ValidationException {
    if (film.getReleaseDate().isBefore(LocalDate.of(1895, 1,1))) {
      throw new ValidationException("The film can't be earlier than 1895.");
    }
  }
}
