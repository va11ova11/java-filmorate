package ru.yandex.practicum.filmorate.validator;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validator.annotation.ReleaseDateValidation;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateValidation, LocalDate> {
  @Override
  public boolean isValid(LocalDate localDate,
      ConstraintValidatorContext constraintValidatorContext) {
    LocalDate firstRelease = LocalDate.of(1895, 1,1);
    return localDate.isAfter(firstRelease);
  }
}
