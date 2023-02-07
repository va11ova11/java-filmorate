package ru.yandex.practicum.filmorate.storage.user;

import java.util.Collection;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

  User get(Long id);

  User add(User user);

  User delete(Long id);

  User update(User user);

  Collection<User> getAll();

  boolean containsUser(Long id);
}
