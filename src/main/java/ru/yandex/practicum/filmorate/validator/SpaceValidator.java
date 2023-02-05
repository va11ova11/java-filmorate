package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validator.annotation.ContainsSpace;

public class SpaceValidator implements ConstraintValidator<ContainsSpace, String> {

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return !s.contains(" ");
  }
}
