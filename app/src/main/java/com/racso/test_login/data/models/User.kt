package com.racso.test_login.data.models

import com.google.firebase.auth.FirebaseUser

data class User(
    val id: String = "",
    val email: String = "",
    val phoneNumber: String= ""
)

fun FirebaseUser.toUser(): User {
    return User(uid, email ?: "", phoneNumber ?: "")
}
