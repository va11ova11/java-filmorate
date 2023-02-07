package ru.yandex.practicum.filmorate.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.advice.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.advice.exception.NotFoundException;

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
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleUserAlreadyExistException(AlreadyExistException e) {
    log.error(e.getMessage(), e);
    return new ErrorMessage(e.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage handleInternalError(Throwable e) {
    log.error(e.getMessage(), e);
    return new ErrorMessage(e.getMessage());
  }
}
