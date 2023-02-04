package ru.yandex.practicum.filmorate.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.validator.UserValidator.validateUser;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

public class ReleaseDateValidationTest {

  private User user;

  @BeforeEach
  public void beforeEach() {
    user = User.builder()
        .id(1)
        .name("name")
        .login("login")
        .email("user1@mail.ru")
        .birthday(LocalDate.of(2000, 2, 3))
        .build();
  }

  @Test
  void successfulValidation() {
    assertEquals(user, validateUser(user));
  }

  @Test
  void validateNull() {

    assertThrows(ValidationException.class, () -> validateUser(null));
  }

  @Test
  void validateEmail() {
    User emptyEmail = user.toBuilder().email("").build();
    User nullEmail = user.toBuilder().email(null).build();
    User emailWithSpace = user.toBuilder().email(" ").build();

    assertThrows(ValidationException.class, () -> validateUser(emptyEmail));
    assertThrows(ValidationException.class, () -> validateUser(nullEmail));
    assertThrows(ValidationException.class, () -> validateUser(emailWithSpace));
  }

  @Test
  void validateLogin() {
    User emptyLogin = user.toBuilder().login("").build();
    User nullLogin = user.toBuilder().login(null).build();
    User loginWithSpace = user.toBuilder().login("log in").build();
    User onlySpace = user.toBuilder().login(" ").build();
    assertThrows(ValidationException.class, () -> validateUser(nullLogin));
    assertThrows(ValidationException.class, () -> validateUser(emptyLogin));
    assertThrows(ValidationException.class, () -> validateUser(loginWithSpace));
    assertThrows(ValidationException.class, () -> validateUser(onlySpace));
  }

  @Test
  void validateBirthday() {
    User nullBirthday = user.toBuilder().birthday(null).build();
    User futureBirthday = user.toBuilder().birthday(LocalDate.of(2050, 1, 1)).build();
    assertThrows(ValidationException.class, () -> validateUser(nullBirthday));
    assertThrows(ValidationException.class, () -> validateUser(futureBirthday));
  }

  @Test
  void validateName() {
    User nullName = user.toBuilder().name(null).build();
    User emptyName = user.toBuilder().name("").build();
    User oneSpaceName = user.toBuilder().name(" ").build();

    User newNameUser1 = validateUser(nullName);
    User newNameUser2 = validateUser(emptyName);
    User newNameUser3 = validateUser(oneSpaceName);

    assertEquals(newNameUser1.getLogin(), newNameUser1.getName());
    assertEquals(newNameUser2.getLogin(), newNameUser2.getName());
    assertEquals(newNameUser3.getLogin(), newNameUser3.getName());
  }
}
