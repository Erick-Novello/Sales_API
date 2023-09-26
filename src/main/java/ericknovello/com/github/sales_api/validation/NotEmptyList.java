package ericknovello.com.github.sales_api.validation;

import ericknovello.com.github.sales_api.validation.constraintValidator.NotEmptyListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message() default  "List cannot be empty";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default{};

}
