package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.user.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
@Component
public class UserStorageImpl implements UserStorage{

  private final Map<Long, User> users;
  private Long userId = 0L;

  public UserStorageImpl() {
    users = new HashMap<>();
  }

  @Override
  public User get(Long id) {
    if (users.containsKey(id)) {
      return users.get(id);
    } else {
      throw new UserNotFoundException("User not found");
    }
  }

  @Override
  public User add(User user) {
    boolean userExist = users.values().stream().anyMatch(u -> u.equals(user));

    if (userExist) {
      throw new UserAlreadyExistException(String.format(
          "Being added user - %s already exist", user.getName()));
    }
    User newUser = user.toBuilder().id(++userId).build();
    users.put(newUser.getId(), newUser);
    return newUser;
  }

  @Override
  public User delete(Long id) {
    if(users.containsKey(id)) {
      User deleteUser = users.get(id);
      users.remove(id);
      return deleteUser;
    } else {
      throw new UserNotFoundException("Being deleted user not found");
    }
  }

  @Override
  public User update(User user) {
    if(users.containsKey(user.getId())) {
      users.put(user.getId(), user);
      return user;
    } else {
      throw new UserNotFoundException("Updated user not found");
    }
  }

  @Override
  public Collection<User> getAll() {
    return users.values();
  }
}
