package hu.unideb.webdev.repository.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class CategoryVerificationValidation implements ConstraintValidator<CategoryVerification, Collection<String>> {
    @Override
    public void initialize(CategoryVerification constraintAnnotation) {

    }

    @Override
    public boolean isValid(Collection<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        return strings.stream().allMatch(s -> s.matches("^[A-Z][a-z]*"));
    }
}
