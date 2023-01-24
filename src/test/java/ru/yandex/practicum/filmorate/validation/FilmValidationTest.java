package ru.yandex.practicum.filmorate.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.validator.FilmValidator.validateFilm;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmValidationTest {

  private Film film;

  @BeforeEach
  public void beforeEach() {
    film = Film.builder()
        .id(1)
        .name("film1")
        .description("desc")
        .duration(100)
        .releaseDate(LocalDate.of(2000, 1, 1))
        .build();
  }

  @Test
  void successfulValidation() {
    assertEquals(film, validateFilm(film));
  }

  @Test
  void validateNull() {
    assertThrows(ValidationException.class, () -> validateFilm(null));
  }

  @Test
  void validateName() {
    Film filmEmptyName = film.toBuilder().name("").build();
    assertThrows(ValidationException.class, () -> validateFilm(filmEmptyName));
  }

  @Test
  void validateDescription() {
    Film filmFailDesc = film.toBuilder().description("d".repeat(203)).build();
    Film nullDesc = film.toBuilder().description(null).build();
    Film blankDesc = film.toBuilder().description("").build();
    assertThrows(ValidationException.class, () -> validateFilm(filmFailDesc));
    assertThrows(ValidationException.class, () -> validateFilm(nullDesc));
    assertThrows(ValidationException.class, () -> validateFilm(blankDesc));

  }

  @Test
  void validateReleaseDate() {
    Film filmFailReleaseDate = Film.builder().releaseDate(LocalDate.of(1894, 4, 2)).build();
    assertThrows(ValidationException.class, () -> validateFilm(filmFailReleaseDate));
  }

  @Test
  void validateDuration() {
    Film filmFailDuration = film.toBuilder().duration(-1).build();
    assertThrows(ValidationException.class, () -> validateFilm(filmFailDuration));
  }
}
