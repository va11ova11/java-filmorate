package ru.yandex.practicum.filmorate.advice.exception;

public class AlreadyExistException extends RuntimeException {
  public AlreadyExistException(String mes) {
    super(mes);
  }
}
