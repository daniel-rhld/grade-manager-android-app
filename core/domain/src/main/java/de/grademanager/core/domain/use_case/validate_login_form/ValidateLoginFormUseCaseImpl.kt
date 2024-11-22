package de.grademanager.core.domain.use_case.validate_login_form

import android.util.Patterns
import de.grademanager.common.util.StringWrapper
import de.grademanager.core.domain.models.ValidationResult

class ValidateLoginFormUseCaseImpl : ValidateLoginFormUseCase {

    override fun invoke(
        emailAddress: String,
        password: String,

        emptyEmailAddressErrorMessage: StringWrapper,
        invalidEmailAddressErrorMessage: StringWrapper,
        emptyPasswordErrorMessage: StringWrapper,
    ): ValidationResult {
        if (emailAddress.isBlank()) {
            return ValidationResult.Failure(emptyEmailAddressErrorMessage)
        }

        if (password.isBlank()) {
            return ValidationResult.Failure(emptyPasswordErrorMessage)
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return ValidationResult.Failure(invalidEmailAddressErrorMessage)
        }

        return ValidationResult.Success
    }

}