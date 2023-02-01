package ru.yandex.practicum.filmorate.advice;

public class ErrorMessage {

  private String message;
  private int statusCode;

  public ErrorMessage(String message, int statusCode) {
    this.statusCode = statusCode;
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorMessage() {

  }
}
