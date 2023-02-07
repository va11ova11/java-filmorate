package ru.yandex.practicum.filmorate.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.advice.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.advice.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

@Slf4j
@RequiredArgsConstructor
@Component
public class FilmService {

  private final FilmStorage filmStorage;
  private final UserService userService;

  public Film addFilm(Film film) {
    Film newFilm = filmStorage.add(film);
    log.debug("New Film - {}, id - {}, has been added", newFilm.getName(), newFilm.getId());
    return newFilm;
  }

  public Film updateFilm(Film film) {
    Film updateFilm = filmStorage.update(film);
    log.debug("Film {}, id - {}, has been updated", updateFilm.getName(), film.getId());
    return updateFilm;
  }

  public Collection<Film> getAll() {
    Collection<Film> users = filmStorage.getAll();
    log.debug("Films has been received");
    return users;
  }

  public Film getFilmById(Long id) {
    Film film = filmStorage.get(id);
    log.debug("Film {}, id - {}, has been received by id", film.getName(), id);
    return film;
  }

  public Film likeFilmByUserId(Long id, Long userId) {
    Film film = filmStorage.get(id);
    if (userService.containsUser(userId)) {
      if (film.addUserLikes(userId)) {
        film.setRate(film.getRate() + 1);
        log.debug("User like - id {} has been added, all likes - {}", id, film.getUserLikes());
        return filmStorage.update(film);
      } else {
        throw new AlreadyExistException(String.format("User id - %s like already exist", userId));
      }
    } else {
      throw new NotFoundException(String.format("User id - %s not found", userId));
    }
  }


  public Film deleteLikeFilmByUserId(Long id, Long userId) {
    Film film = filmStorage.get(id);
    if (userService.containsUser(userId)) {
      if (film.deleteUserLike(userId)) {
        film.setRate(film.getRate() - 1);
        log.debug("User like - id {} has been deleted, all likes - {}", userId, film.getUserLikes());
        return filmStorage.update(film);
      } else {
        throw new NotFoundException(String.format("User id - %s likes not found", userId));
      }
    } else {
      throw new NotFoundException(String.format("User id - %s not found", userId));
    }
  }

  public List<Film> getPopularFilms(Integer count) {
    List<Film> films =  filmStorage.getAll().stream()
        .sorted((o1, o2) -> Integer.compare(o2.getRate(), o1.getRate()))
        .limit(count)
        .collect(Collectors.toList());
    log.debug("Popular films has been received");
    return films;
  }
}
