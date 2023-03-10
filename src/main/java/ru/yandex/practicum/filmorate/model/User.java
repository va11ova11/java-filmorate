package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.annotation.ContainsSpace;

@Data
public class User {
  private Long id;
  @NotNull(message = "Email is required")
  @Email
  private String email;
  @NotBlank(message = "Login is required")
  @ContainsSpace(message = "Login contains space")
  private String login;
  private String name;
  @NotNull(message = "Birthday is required")
  @Past
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  private Set<Long> friends = new HashSet<>();
  public boolean addFriend(Long id) {
    return friends.add(id);
  }
  public boolean deleteFriend(Long id) {
    return friends.remove(id);
  }
}
