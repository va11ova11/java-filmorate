package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import ru.yandex.practicum.filmorate.validator.annotation.ReleaseDateValidation;


@Value
@Builder(toBuilder = true)
public class Film {
  Integer id;
  @NotBlank(message = "Film name is required")
  String name;
  @NotBlank(message = "Film description is required")
  @Size(max = 200, message = "Description length should be no more than 200 characters.")
  String description;
  @NotNull(message = "Release date is required.")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ReleaseDateValidation
  LocalDate releaseDate;
  @NotNull(message = "Duration is required")
  @Positive(message = "The duration of the film cannot be negative.")
  Integer duration;
}
