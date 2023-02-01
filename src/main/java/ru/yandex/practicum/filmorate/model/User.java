package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
