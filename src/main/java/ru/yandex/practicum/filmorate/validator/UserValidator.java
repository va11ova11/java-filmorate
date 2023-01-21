package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

public class UserValidator {
  public User validateUser(User user) throws ValidationException{
    if(user.getLogin().contains(" ")) {
      throw new ValidationException("Validation failed! The login contains a space.");
    }
    if(user.getName() == null || user.getName().isEmpty()) {
      return user.toBuilder().name(user.getLogin()).build();
    }
    return user;
  }
}
