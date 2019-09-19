package br.com.mymoney.user.application.web.request.validation

object ValidationRules {

    //name
    const val MIN_USER_NAME_LENGTH = 2
    const val MAX_USER_NAME_LENGTH = 100

    //last_name
    const val MIN_LAST_NAME_LENGTH = 3
    const val MAX_LAST_NAME_LENGTH = 100

    //email
    const val MIN_USER_EMAIL_LENGTH = 6
    const val MAX_USER_EMAIL_LENGTH = 100

}