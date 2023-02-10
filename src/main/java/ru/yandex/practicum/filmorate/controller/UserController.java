package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public User addUser(@RequestBody @NotNull @Valid User user) {
   return userService.addUser(user);
  }

  @PutMapping
  public User updateUser(@RequestBody @NotNull @Valid User user) {
    return userService.updateUser(user);
  }
  @DeleteMapping("/{id}")
  public User deleteUserById(@PathVariable @NotNull @Positive Long id) {
    return userService.deleteUserById(id);
  }

  @GetMapping
  public Collection<User> getAll() {
    return userService.getAll();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable @NotNull @Positive Long id) {
    return userService.getUserById(id);
  }

  @PutMapping(value = "/{id}/friends/{friendId}")
  public User addFriend(@PathVariable @NotNull @Positive Long id,
                        @PathVariable @NotNull @Positive Long friendId) {
    return userService.addFriendById(id, friendId);
  }

  @DeleteMapping(value = "/{id}/friends/{friendId}")
  public User deleteFriend(@PathVariable @NotNull @Positive Long id,
                           @PathVariable @NotNull @Positive  Long friendId) {
    return userService.deleteFriendById(id, friendId);
  }

  @GetMapping("/{id}/friends")
  public List<User> getAllFriend(@PathVariable @NotNull @Positive Long id) {
      return userService.getUserFriendsById(id);
  }

  @GetMapping("/{id}/friends/common/{otherId}")
  public List<User> getCommonFriends(@PathVariable(name = "id") @NotNull @Positive Long user1Id,
                                     @PathVariable(name = "otherId") @NotNull @Positive Long user2Id) {
   return userService.getCommonFriendsByIds(user1Id, user2Id);
  }
}
