package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.advice.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.advice.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

  private final UserStorage userStorage;


  public User addUser(User user) {
    if (user.getName().isBlank()) {
      user.setName(user.getLogin());
      userStorage.add(user);
      log.debug("New User id - {}, {} update name to login and has been added", user.getId(),
          user.getName());
      return user;
    }
    User newUser = userStorage.add(user);
    log.debug("New User id - {}, {} has been added", newUser.getId(), user.getName());
    return newUser;
  }

  public User updateUser(User user) {
    User updateUser = userStorage.update(user);
    log.debug("User id - {}, {} has been updated", updateUser.getId(), user.getName());
    return updateUser;
  }

  public Collection<User> getAll() {
    Collection<User> users = userStorage.getAll();
    log.debug("Users has been received");
    return users;
  }

  public User getUserById(Long id) {
    User user = userStorage.get(id);
    log.debug("User id - {}, {}, has been received by Id", id, user.getName());
    return user;
  }

  public User addFriendById(Long id, Long friendId) {
    User user = userStorage.get(id);
    User friend = userStorage.get(friendId);

    if (user.addFriend(friendId) && friend.addFriend(id)) {
      userStorage.update(user);
      userStorage.update(friend);
      log.debug("New friend id - {} {}, {} has been added",
          friend.getId(), friend.getName(), friend.getFriends());
      return user;
    } else {
      throw new AlreadyExistException(String.format(
          "Friend id - %s %s, %s has already been added",
          friend.getId(), friend.getName(), friend.getName()));
    }
  }

  public User deleteFriendById(Long id, Long friendId) {
    User user = userStorage.get(id);
    User friend = userStorage.get(friendId);

    if (user.deleteFriend(friendId) && friend.deleteFriend(id)) {
      userStorage.update(user);
      userStorage.update(friend);
      log.debug("Friend id - {} {}, {} has been deleted", friendId, friend.getName(),
          friend.getFriends());
      return user;
    } else {
      throw new NotFoundException(String.format(
          "Deleted fried id - %s, %s, %s not found", friendId, friend.getName(), friend.getName()));
    }
  }

  public List<User> getUserFriendsById(Long id) {
    User user = userStorage.get(id);
    List<User> friends = new ArrayList<>();
    for (Long friendId : user.getFriends()) {
      friends.add(userStorage.get(friendId));
    }
    log.debug("Friends has been received");
    return friends;
  }

  public List<User> getCommonFriends(Long id, Long otherId) {
    Set<Long> friendsId1 = userStorage.get(id).getFriends();
    Set<Long> friendsId2 = userStorage.get(otherId).getFriends();

    if (friendsId1 == null || friendsId2.isEmpty()) {
      return new ArrayList<>();
    }

    List<Long> commonFriendId = new ArrayList<>();
    for (Long f1 : friendsId1) {
      for (Long f2 : friendsId2) {
        if (f1.equals(f2)) {
          commonFriendId.add(f1);
        }
      }
    }

    List<User> commonFriends = new ArrayList<>();
    for (Long friendId : commonFriendId) {
      commonFriends.add(userStorage.get(friendId));
    }
    log.debug("Common friends has been received");
    return commonFriends;
  }

  public boolean containsUser(Long id) {
    return userStorage.containsUser(id);
  }
}
