package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public User addUser(@RequestBody @NotNull @Valid User user) {
   return userService.addUser(user);
  }

  @PutMapping
  public User updateUser(@RequestBody @NotNull @Valid User user) {
    return userService.updateUser(user);
  }

  @GetMapping
  public Collection<User> getAllUsers() {
    return userService.getAll();
  }

  @GetMapping("{id}")
  public User getUser(@NotNull @Positive @PathVariable Long id) {
    return userService.getUserById(id);
  }

  @PutMapping(value = "/{id}/friends/{friendId}")
  public User addFriend(@NotNull @Positive @PathVariable Long id,
                        @NotNull @Positive @PathVariable Long friendId) {

    return userService.addFriendById(id, friendId);
  }

  @DeleteMapping(value = "/{id}/friends/{friendId}")
  public User deleteFriend(@NotNull @Positive @PathVariable Long id,
                           @NotNull @Positive @PathVariable Long friendId) {

    return userService.deleteFriendById(id, friendId);
  }

  @GetMapping("/{id}/friends")
  public List<User> getAllFriend(@NotNull @Positive @PathVariable Long id) {
      return userService.getUserFriendsById(id);
  }

  @GetMapping("/{id}/friends/common/{otherId}")
  public List<User> getCommonFriends(@NotNull @Positive @PathVariable Long id,
                                    @NotNull @Positive @PathVariable Long otherId) {
   return userService.getCommonFriends(id, otherId);
  }
}
