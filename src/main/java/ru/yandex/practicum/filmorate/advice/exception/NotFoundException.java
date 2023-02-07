package ru.yandex.practicum.filmorate.advice.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
      super(message);
    }
}
