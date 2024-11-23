package de.grademanager.core.domain.use_case.validate_register_form

import de.grademanager.common.util.StringWrapper
import de.grademanager.core.domain.models.ValidationResult

interface ValidateRegisterFormUseCase {

    operator fun invoke(
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
    ): ValidationResult

}