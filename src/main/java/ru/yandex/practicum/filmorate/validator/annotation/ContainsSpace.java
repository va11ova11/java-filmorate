package ru.yandex.practicum.filmorate.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.yandex.practicum.filmorate.validator.SpaceValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpaceValidator.class)
public @interface ContainsSpace {
  String message();
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
