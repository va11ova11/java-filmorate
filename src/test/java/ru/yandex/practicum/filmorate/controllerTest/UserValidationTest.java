package ru.yandex.practicum.filmorate.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllerTest.utilForTest.LocalDateAdapter;
import ru.yandex.practicum.filmorate.model.User;

public class UserValidationTest {

  private static URI userUri;
  private static HttpClient client;
  private static Gson gson;

  @BeforeAll
  public static void beforeAll() {
    userUri = URI.create("http://localhost:8080/users");
    client = HttpClient.newHttpClient();
    gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();
  }

  @Test
  public void shouldReturnStatusCode400WhenFilmIsEmpty()
      throws IOException, InterruptedException {
    String json = "{}";
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(userUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(), "An empty user is added.");
  }

  @Test
  public void shouldStatusCode200WhenUserIsCorrect() throws IOException, InterruptedException {
    User user = User.builder()
        .name("user1")
        .email("user1@mail.ru")
        .login("11111")
        .birthday(LocalDate.of(2004, 1, 2))
        .build();

    String json = gson.toJson(user);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(userUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(200, response.statusCode(), "The correct user is not added.");
  }

  @Test
  public void shouldStatusCode400WhenEmailIsEmpty() throws IOException, InterruptedException {
    User user = User.builder()
        .name("user1")
        .login("11111")
        .birthday(LocalDate.of(2004, 1, 2))
        .build();

    String json = gson.toJson(user);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(userUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(400, response.statusCode(), "A user without email is added.");
  }


  @Test
  public void shouldStatusCode400WhenEmailIsIncorrect() throws IOException, InterruptedException {
    User user = User.builder()
        .name("user1")
        .login("11111")
        .email("user.22yande.ru")
        .birthday(LocalDate.of(2004, 1, 2))
        .build();

    String json = gson.toJson(user);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(userUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(400, response.statusCode(), "A user with an incorrect email is added.");
  }

  @Test
  public void shouldStatusCode400WhenLoginIsEmpty() throws IOException, InterruptedException {
    User user = User.builder()
        .name("user1")
        .email("user@mail.ru")
        .birthday(LocalDate.of(2004, 1, 2))
        .build();

    String json = gson.toJson(user);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(userUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(400, response.statusCode(), "A user with an empty username is added.");
  }


  @Test
  public void shouldStatusCode200WhenNameIsEmpty() throws IOException, InterruptedException {
    User user = User.builder()
        .login("user1")
        .email("user@mail.ru")
        .birthday(LocalDate.of(2004, 1, 2))
        .build();

    String json = gson.toJson(user);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(userUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    String userName = gson.fromJson(response.body(), User.class).getName();

    assertEquals(200, response.statusCode(), "A user with an empty name is not added.");
    assertEquals("user1", userName, "If the name is empty, no login is assigned to the name");
  }


  @Test
  public void shouldStatusCode400WhenBirthdayIsFuture() throws IOException, InterruptedException {
    User user = User.builder()
        .login("1111")
        .name("user1")
        .email("user@mail.ru")
        .birthday(LocalDate.of(2024, 1, 2))
        .build();

    String json = gson.toJson(user);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(userUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(400, response.statusCode(), "The user is added happy birthday in the future.");

  }

  @Test
  public void shouldStatusCode400WhenLoginContainsSpace() throws IOException, InterruptedException {
    User user = User.builder()
        .login("user name")
        .name("user1")
        .email("user@mail.ru")
        .birthday(LocalDate.of(2004, 1, 2))
        .build();

    String json = gson.toJson(user);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(userUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    assertEquals(400, response.statusCode(), "A user with a space in the login is added.");
  }
}
