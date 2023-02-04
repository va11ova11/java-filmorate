package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validator.annotation.SpaceValidation;

public class SpaceValidator implements ConstraintValidator<SpaceValidation, String> {

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return !s.contains(" ");
  }
}
