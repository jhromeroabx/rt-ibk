package org.example.infrastructure.validation;

import org.example.infrastructure.config.SecurityProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private final SecurityProperties securityProperties;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        String regex = securityProperties.getPasswordRegex();
        return Pattern.matches(regex, value);
    }
}