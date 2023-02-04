package ru.yandex.practicum.filmorate.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.yandex.practicum.filmorate.validator.ReleaseDateValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
public @interface ReleaseDateValidation {
  String message() default "Release date must be not before 1895 year";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
