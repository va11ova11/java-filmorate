package ru.yandex.practicum.filmorate.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.user.UserAlreadyExistException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleNotValidException(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    return new ErrorMessage(e.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleNotFoundException(NotFoundException e) {
    log.error(e.getMessage(), e);
    return new ErrorMessage(e.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleUserAlreadyExistException(UserAlreadyExistException e) {
    log.error(e.getMessage(), e);
    return new ErrorMessage(e.getMessage());
  }
}
