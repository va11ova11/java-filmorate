package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class User {
  int id;
  @NotEmpty(message = "Email is required")
  @Email
  String email;
  @NotEmpty(message = "Login is required")
  String login;
  String name;
  @NotNull(message = "Birthday is required")
  @Past
  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDate birthday;
}
