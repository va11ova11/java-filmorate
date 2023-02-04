package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.Builder;
import lombok.Value;
import ru.yandex.practicum.filmorate.validator.annotation.SpaceValidation;

@Value
@Builder(toBuilder = true)
public class User {
  Long id;
  @NotNull(message = "Email is required")
  @Email
  String email;
  @NotBlank(message = "Login is required")
  @SpaceValidation(message = "Login contains space")
  String login;
  String name;
  @NotNull(message = "Birthday is required")
  @Past
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate birthday;
}
