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
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.controllerTest.utilForTest.LocalDateAdapter;

@SpringBootTest
public class FilmValidationTest {

  private static URI filmUri;
  private static HttpClient client;
  private static Gson gson;

  @BeforeAll
  public static void beforeAll() {
    filmUri = URI.create("http://localhost:8080/films");
    client = HttpClient.newHttpClient();
    gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .serializeNulls()
        .create();
  }


  @Test
  public void shouldReturnStatusCode400WhenFilmIsEmpty()
      throws IOException, InterruptedException {
    String json = "{}";
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(), "An empty movie is added.");
  }


  @Test
  public void shouldReturnStatusCode200WhenFilmIsCorrect()
      throws IOException, InterruptedException {
    Film correctFilm = Film.builder()
        .name("correct Film")
        .description("desc1")
        .duration(100)
        .releaseDate(LocalDate.now())
        .build();
    String json = gson.toJson(correctFilm);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(200, response.statusCode(), "The correct movie is not added.");
  }


  @Test
  public void shouldReturnStatusCode400WhenFilmNameIsEmpty()
      throws IOException, InterruptedException {
    Film filmEmptyName = Film.builder()
        .name("")
        .description("desc1")
        .duration(100)
        .releaseDate(LocalDate.now())
        .build();

    String json = gson.toJson(filmEmptyName);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(), "A movie with an empty name is added.");
  }

  @Test
  public void shouldReturnStatusCode400WhenFilmDescIsEmpty()
      throws IOException, InterruptedException {
    Film filmEmptyDesc = Film.builder()
        .name("name")
        .description("")
        .duration(100)
        .releaseDate(LocalDate.now())
        .build();

    String json = gson.toJson(filmEmptyDesc);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(), "A movie with an empty description is added.");
  }

  @Test
  public void shouldReturnStatusCode400WhenDescriptionLengthMore200()
      throws IOException, InterruptedException {
    Film filmDescLengthMore200 = Film.builder()
        .name("name")
        .description(
            "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.")
        .duration(100)
        .releaseDate(LocalDate.now())
        .build();

    String json = gson.toJson(filmDescLengthMore200);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(), "A movie with a description of more than 200 characters is added.");
  }

  @Test
  public void shouldReturnStatusCode400WhenReleaseDateIsFuture()
      throws IOException, InterruptedException {
    Film filmReleaseDateIsFuture = Film.builder()
        .name("name")
        .description("desc")
        .duration(100)
        .releaseDate(LocalDate.of(2025, 1, 2))
        .build();

    String json = gson.toJson(filmReleaseDateIsFuture);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(), "A movie is added with a release date in the future.");
  }

  @Test
  public void shouldReturnStatusCode400WhenReleaseDateBefore1895()
      throws IOException, InterruptedException {
    Film filmReleaseDateIsBefore1895 = Film.builder()
        .name("name")
        .description("desc")
        .duration(100)
        .releaseDate(LocalDate.of(1894, 1, 2))
        .build();

    String json = gson.toJson(filmReleaseDateIsBefore1895);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(), "A film with a release date earlier than 1895 is added.");
  }

  @Test
  public void shouldReturnStatusCode400WhenDurationIsEmpty()
      throws IOException, InterruptedException {
    Film filmDurationIsEmpty = Film.builder()
        .name("name")
        .description("desc")
        .releaseDate(LocalDate.of(2023, 1, 2))
        .build();

    String json = gson.toJson(filmDurationIsEmpty);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(), "A movie with no duration is added.");
  }

  @Test
  public void shouldReturnStatusCode400WhenDurationIsNegative()
      throws IOException, InterruptedException {
    Film filmDurationIsNegative = Film.builder()
        .name("name")
        .description("desc")
        .releaseDate(LocalDate.of(2023, 1, 2))
        .duration(-100)
        .build();

    String json = gson.toJson(filmDurationIsNegative);
    final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
    HttpRequest request = HttpRequest.newBuilder().uri(filmUri).POST(body)
        .header("Content-Type", "application/json").build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(400, response.statusCode(),
        "A movie with a negative duration is added.");
  }
}
