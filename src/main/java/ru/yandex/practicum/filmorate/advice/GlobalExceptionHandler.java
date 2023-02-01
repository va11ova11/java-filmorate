package ru.yandex.practicum.filmorate.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ErrorMessage> catchResourceNotFoundException(ValidationException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorMessage> catchResourceNotFoundException(
      MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorMessage> catchResourceNotFoundException(NotFoundException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<>(new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND.value()),
        HttpStatus.NOT_FOUND);
  }
}
