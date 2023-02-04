package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.FriendOperationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Slf4j
@Service
public class UserService {

  private final UserStorage userStorage;

  public UserService(UserStorage userStorage) {
    this.userStorage = userStorage;
  }


  public User addUser(User user) {
    if(user.getName().isBlank()) {
      User updateNameUser = user.toBuilder().name(user.getLogin()).build();
      return userStorage.add(updateNameUser);
    }
    return userStorage.add(user);
  }

  public User updateUser(User user) {
    return userStorage.update(user);
  }

  public Collection<User> getAll() {
    return userStorage.getAll();
  }

  public User getUserById(Long id) {
    return userStorage.get(id);
  }

  public User addFriendById(Long id, Long friendId) {
    User user = userStorage.get(id);
    User friend = userStorage.get(friendId);

    if(user.getFriends().contains(friendId)) {
      throw new FriendOperationException("Friend has already been added");
    } else {
      user.addFriend(friendId);
      friend.addFriend(id);
      userStorage.update(user);
      userStorage.update(friend);
      return user;
    }
  }

  public User deleteFriendById(Long id, Long friendId) {
    User user = userStorage.get(id);
    User friend = userStorage.get(friendId);

    if(user.getFriends().contains(friendId)) {
      user.deleteFriend(friendId);
      friend.deleteFriend(id);
      userStorage.update(user);
      userStorage.update(friend);
      return user;
    }
    else {
      throw new FriendOperationException("Delete friend not found");
    }
  }

  public List<User> getUserFriendsById(Long id) {
    User user = userStorage.get(id);
    List<User> friends = new ArrayList<>();
    for(Long friendId : user.getFriends()) {
      friends.add(userStorage.get(friendId));
    }
    return friends;
  }

  public List<User> getCommonFriend(Long id, Long otherId) {
    Set<Long> friendsId1 = userStorage.get(id).getFriends();
    Set<Long> friendsId2 = userStorage.get(otherId).getFriends();

    if(friendsId1.isEmpty() || friendsId2.isEmpty()) {
      return new ArrayList<>();
    }

    List<Long> commonFriendId = new ArrayList<>();
    for(Long f1 : friendsId1){
      for(Long f2 : friendsId2) {
        if(f1.equals(f2)) {
          commonFriendId.add(f1);
        }
      }
    }

    List<User> commonFriends = new ArrayList<>();
    for(Long friendId : commonFriendId) {
      commonFriends.add(userStorage.get(friendId));
    }
    return commonFriends;
  }
}
