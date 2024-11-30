package edu.dev.auth.validation.validator;

import edu.dev.auth.validation.annotation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			// Use reflection to get password and confirmPassword fields
			String password = (String) value.getClass().getMethod("getPassword").invoke(value);
			String confirmPassword = (String) value.getClass().getMethod("getConfirmPassword").invoke(value);

			if (password != null && password.equals(confirmPassword)) {
				return true;
			}

			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("password") // Specify the node where the error occurs
					.addConstraintViolation();

			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("confirmPassword") // Specify the node where the error occurs
					.addConstraintViolation();

			return false;

		} catch (Exception e) {
			return false;
		}

	}
}
