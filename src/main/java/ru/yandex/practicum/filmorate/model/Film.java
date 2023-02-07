package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.annotation.ReleaseDate;


@Data
public class Film {
  private Long id;
  @NotBlank(message = "Film name is required")
  private String name;
  @NotBlank(message = "Film description is required")
  @Size(max = 200, message = "Description length should be no more than 200 characters.")
  private String description;
  @NotNull(message = "Release date is required.")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @ReleaseDate
  private LocalDate releaseDate;
  @NotNull(message = "Duration is required")
  @Positive(message = "The duration of the film cannot be negative.")
  private Integer duration;
  private Set<Long> userLikes = new HashSet<>();
  private Integer rate = 0;
  public boolean addUserLikes(Long userId) {
    return userLikes.add(userId);
  }

  public boolean deleteUserLike(Long userId) {
    return userLikes.remove(userId);
  }
}
