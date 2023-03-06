package ru.yandex.practicum.filmorate.storage.user.impl;

import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Component
public class UserDbStorage implements UserStorage {

  private final JdbcTemplate jdbcTemplate;

  public UserDbStorage(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  @Override
  public User get(Long id) {
    return null;
  }

  @Override
  public User add(User user) {
    return null;
  }

  @Override
  public User delete(Long id) {
    return null;
  }

  @Override
  public User update(User user) {
    return null;
  }

  @Override
  public Collection<User> getAll() {
    return null;
  }

  @Override
  public boolean containsUser(Long id) {
    return false;
  }
}
