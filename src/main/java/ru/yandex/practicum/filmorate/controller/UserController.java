package ru.yandex.practicum.filmorate.controller;

import static ru.yandex.practicum.filmorate.validator.UserValidator.validateUser;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;

@Slf4j
@RestController
public class UserController {

  private final Map<Integer, User> users = new HashMap<>();
  private int userId = 0;


  @PostMapping("/users")
  public User addUser(@RequestBody @NotNull @Valid User user) throws ValidationException {
      User validateUser = validateUser(user);
      User newUser = validateUser.toBuilder().id(++userId).build();
      users.put(newUser.getId(), newUser);
      log.trace("Validation passed, a new user was successfully added.");
      return newUser;
  }

  @PutMapping("/users")
  public User updateUser(@RequestBody @NotNull @Valid User user) throws ValidationException, NotFoundException {
    if (users.containsKey(user.getId())) {
        User updateUser = validateUser(user);
        users.put(updateUser.getId(), updateUser);
        log.trace("The user has been successfully updated.");
        return updateUser;
      }
    throw new NotFoundException("User being updated does not exist");
  }

  @GetMapping("/users")
  public Collection<User> getAllUsers() {
    log.trace("The list of users has been received.");
    return users.values();
  }
}
