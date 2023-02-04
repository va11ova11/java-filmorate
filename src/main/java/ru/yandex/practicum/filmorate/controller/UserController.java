package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
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

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public User addUser(@RequestBody @NotNull @Valid User user) {
    User newUser =  userService.addUser(user);
    log.debug("Validation passed, a new user {}, was successfully added.", newUser.getName());
    return newUser;
  }

  @PutMapping
  public User updateUser(@RequestBody @NotNull @Valid User user) {
    User updateUser =  userService.updateUser(user);
    log.debug("User {} has been updated", updateUser.getName());
    return updateUser;
  }

  @GetMapping
  public Collection<User> getAllUsers() {
    Collection<User> users = userService.getAll();
    log.debug("Users has been received");
    return users;
  }

  @GetMapping("{id}")
  public User getUser(@NotNull @Positive @PathVariable Long id) {
    User user = userService.getUserById(id);
    log.debug("User {} has been received by Id - {}", user.getName(), id);
    return user;
  }

  @PutMapping(value = "/{id}/friends/{friendId}")
  public User addFriend(@NotNull @Positive @PathVariable Long id,
                        @NotNull @Positive @PathVariable Long friendId) {
    User user =  userService.addFriendById(id, friendId);
    log.debug("New friend has been added");
    return user;
  }

  @DeleteMapping(value = "/{id}/friends/{friendId}")
  public User deleteFriend(@NotNull @Positive @PathVariable Long id,
                           @NotNull @Positive @PathVariable Long friendId) {
    User user =  userService.deleteFriendById(id, friendId);
    log.debug("Friend has been deleted");
    return user;
  }

  @GetMapping("/{id}/friends")
  public List<User> getAllFriend(@NotNull @Positive @PathVariable Long id) {
      List<User> users =  userService.getUserFriendsById(id);
      log.debug("Users has been received");
      return users;
  }

  @GetMapping("/{id}/friends/common/{otherId}")
  public List<User> getCommonFriend(@NotNull @Positive @PathVariable Long id,
                                    @NotNull @Positive @PathVariable Long otherId) {
    List<User> commonFriend = userService.getCommonFriend(id, otherId);
    log.debug("Common friend has been received");
    return commonFriend;
  }
}
