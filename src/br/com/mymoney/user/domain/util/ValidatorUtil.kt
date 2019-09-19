package br.com.mymoney.user.domain.util

import br.com.caelum.stella.validation.CPFValidator
import org.apache.commons.validator.routines.EmailValidator

object ValidatorUtil {

    fun validateTaxIdentifier(cpf: String) =
        try {
            CPFValidator().assertValid(cpf)
            true
        } catch (e: Exception) {
            false
        }

    fun validateEmail(email: String) = EmailValidator.getInstance()
        .isValid(email)
}
