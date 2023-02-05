package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.advice.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.advice.exception.NotFoundException;
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
      throw new NotFoundException("User not found");
    }
  }

  @Override
  public User add(User user) {
    boolean userExist = users.values().stream().anyMatch(u -> u.equals(user));

    if (userExist) {
      throw new AlreadyExistException(String.format(
          "Being added user - %s already exist", user.getName()));
    }

    user.setId(++userId);
    users.put(user.getId(), user);
    log.debug("New user {}, was successfully added.", user.getName());
    return user;
  }

  @Override
  public User delete(Long id) {
    if(users.containsKey(id)) {
      User deleteUser = users.get(id);
      users.remove(id);
      return deleteUser;
    } else {
      throw new NotFoundException("Being deleted user not found");
    }
  }

  @Override
  public User update(User user) {
    if(users.containsKey(user.getId())) {
      users.put(user.getId(), user);
      return user;
    } else {
      throw new NotFoundException("Updated user not found");
    }
  }

  @Override
  public Collection<User> getAll() {
    return users.values();
  }

  @Override
  public boolean containsUser(Long id) {
     if(users.containsKey(id)) return true;
     else throw new NotFoundException("User not found");
  }
}
