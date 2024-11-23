package de.grademanager.core.domain.use_case.validate_register_form

import android.util.Patterns
import de.grademanager.common.util.StringWrapper
import de.grademanager.core.domain.models.ValidationResult

class ValidateRegisterFormUseCaseImpl : ValidateRegisterFormUseCase {
    override fun invoke(
        firstName: String,
        lastName: String,
        emailAddress: String,
        password: String,
        passwordConfirmation: String,

        firstNameEmptyErrorMessage: StringWrapper,
        lastNameEmptyErrorMessage: StringWrapper,
        emailAddressEmptyErrorMessage: StringWrapper,
        emailAddressInvalidErrorMessage: StringWrapper,
        passwordEmptyErrorMessage: StringWrapper,
        passwordConfirmationEmptyErrorMessage: StringWrapper,
        passwordConfirmationDoesNotMatchErrorMessage: StringWrapper
    ): ValidationResult {
        if (firstName.isBlank()) {
            return ValidationResult.Failure(firstNameEmptyErrorMessage)
        }

        if (lastName.isBlank()) {
            return ValidationResult.Failure(lastNameEmptyErrorMessage)
        }

        if (emailAddress.isBlank()) {
            return ValidationResult.Failure(emailAddressEmptyErrorMessage)
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return ValidationResult.Failure(emailAddressInvalidErrorMessage)
        }

        if (password.isBlank()) {
            return ValidationResult.Failure(passwordEmptyErrorMessage)
        }

        if (passwordConfirmation.isBlank()) {
            return ValidationResult.Failure(passwordConfirmationEmptyErrorMessage)
        }

        if (passwordConfirmation != password) {
            return ValidationResult.Failure(passwordConfirmationDoesNotMatchErrorMessage)
        }

        return ValidationResult.Success
    }
}