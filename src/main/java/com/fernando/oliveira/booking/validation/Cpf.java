package com.fernando.oliveira.booking.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CpfValidator.class)
public @interface Cpf {

    String message() default "";
    Class<?>[] groups() default {};
    Class <? extends Payload>[] payload() default {};
    String value() default "";
    boolean required() default false;
}
