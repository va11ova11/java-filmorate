package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.advice.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.advice.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Slf4j
@Component
public class FilmService {

  private final FilmStorage filmStorage;
  private final UserService userService;

  @Autowired
  public FilmService(FilmStorage filmStorage, UserService userService) {
    this.filmStorage = filmStorage;
    this.userService = userService;
  }

  public Film addFilm(Film film) {
    Film newFilm = filmStorage.add(film);
    log.debug("New Film - {}, has been added", newFilm.getName());
    return newFilm;
  }

  public Film updateFilm(Film film) {
    Film updateFilm = filmStorage.update(film);
    log.debug("Film {} has been updated", updateFilm.getName());
    return updateFilm;
  }

  public Collection<Film> getAll() {
    Collection<Film> users = filmStorage.getAll();
    log.debug("Film has been received");
    return users;
  }

  public Film getFilmById(Long id) {
    Film film = filmStorage.get(id);
    log.debug("Film {} has been received by Id - {}", film.getName(), id);
    return film;
  }

  public Film likeFilmByUserId(Long id, Long userId) {
    Film film = filmStorage.get(id);

    if (!userService.containsUser(userId)) {
      throw new NotFoundException("User not found");
    }
    if (film.addUserLikes(userId)) {
      film.setRate(film.getRate() + 1);
      return filmStorage.update(film);
    } else {
      throw new AlreadyExistException("User like already exist");
    }
  }


  public Film deleteLikeFilmByUserId(Long id, Long userId) {
    Film film = filmStorage.get(id);

    if (!userService.containsUser(userId)) {
      throw new NotFoundException("User not found");
    }
    if (film.deleteUserLike(userId)) {
      film.setRate(film.getRate() - 1);
      return filmStorage.update(film);
    } else {
      throw new NotFoundException("User likes not found");
    }
  }

  public List<Film> getPopularFilms(Integer count) {
    return filmStorage.getAll().stream()
        .sorted((o1, o2) -> Integer.compare(o2.getRate(), o1.getRate()))
        .limit(count)
        .collect(Collectors.toList());
  }
}
