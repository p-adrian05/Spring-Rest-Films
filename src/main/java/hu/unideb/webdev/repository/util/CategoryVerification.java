package hu.unideb.webdev.repository.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CategoryVerificationValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface CategoryVerification {
    String message() default "Categories must start capital letter and contains only letters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
