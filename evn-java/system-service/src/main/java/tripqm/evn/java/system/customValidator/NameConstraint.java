package tripqm.evn.java.system.customValidator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {NameValidator.class})
public @interface NameConstraint {
    String message() default "Invalid name";

    String invalidChar();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
