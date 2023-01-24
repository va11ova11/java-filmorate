package ru.yandex.practicum.filmorate.validator;

import java.time.LocalDate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

public class UserValidator {
  public static User validateUser(User user) throws ValidationException {
    if(user == null) throw new ValidationException("User is null");
    checkEmail(user);
    checkLogin(user);
    checkBirthday(user);
    if(user.getName() == null || user.getName().isBlank()) {
      return updateName(user);
    }
    return user;
  }


  private static User updateName(User user) {
    return user.toBuilder().name(user.getLogin()).build();
  }
  private static void checkEmail(User user) {
    if(user.getEmail() == null || user.getEmail().isBlank()) {
      throw new ValidationException("User email is null or blank.");
    }
  }

  private static void checkLogin(User user) {
    if(user.getLogin() == null || user.getLogin().isBlank()) {
      throw new ValidationException("User login is null or blank");
    }
    if(user.getLogin().contains(" ")) {
      throw new ValidationException("Validation failed! The login contains a space.");
    }
  }

  private static void checkBirthday(User user) {
    if(user.getBirthday() == null) {
      throw new ValidationException("User birthday is null");
    }
    if(user.getBirthday().isAfter(LocalDate.now())) {
      throw new ValidationException("User's birthday in the future");
    }
  }
}
