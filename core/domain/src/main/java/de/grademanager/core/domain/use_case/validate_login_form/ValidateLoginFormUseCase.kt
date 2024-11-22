package de.grademanager.core.domain.use_case.validate_login_form

import de.grademanager.common.util.StringWrapper
import de.grademanager.core.domain.models.ValidationResult

interface ValidateLoginFormUseCase {

    operator fun invoke(
        emailAddress: String,
        password: String,

        emptyEmailAddressErrorMessage: StringWrapper,
        invalidEmailAddressErrorMessage: StringWrapper,
        emptyPasswordErrorMessage: StringWrapper,
    ): ValidationResult

}