package com.racso.test_login.utils

import android.util.Patterns

fun isValidPassword(password: String): Boolean {
    val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}\$".toRegex()
    return passwordPattern.matches(password)
}


fun isValidEmail(email: String): Boolean {
    val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
    return emailPattern.matches(email)
}

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val phoneNumberPattern = "^\\+?[0-9]{1,4}?[-.\\s]?\\(?[0-9]{1,3}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}\$".toRegex()
    return phoneNumberPattern.matches(phoneNumber)
}
