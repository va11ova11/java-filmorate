package ru.yandex.practicum.filmorate.validator;

import java.time.LocalDate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmValidator {
  public static Film validateFilm(Film film) throws ValidationException {
    if(film == null) throw new ValidationException("film is null");
    checkName(film);
    checkDescriptionLength(film);
    checkRelease(film);
    checkDuration(film);
    return film;
  }

  private static void checkRelease(Film film) throws ValidationException {
    if (film.getReleaseDate().isBefore(LocalDate.of(1895, 1,1))) {
      throw new ValidationException("The film can't be earlier than 1895.");
    }
  }

  private static void checkDuration(Film film) throws ValidationException {
    if (film.getDuration() <= 0) {
      throw new ValidationException("film.duration is negative or zero");
    }
  }

  private static void checkDescriptionLength(Film film) {
    if(film.getDescription() == null || film.getDescription().isBlank()){
      throw new ValidationException("Film description null or blank.");
    }
    if(film.getDescription().length() >= 200) {
      throw new ValidationException("Description length should be no more than 200 characters.");
    }
  }

  private static void checkName(Film film) {
    if(film.getName() == null || film.getName().isBlank()) {
      throw new ValidationException("film name is null or blank");
    }
  }

}
