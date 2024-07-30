package com.racso.test_login.data.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import com.racso.test_login.data.models.User
import com.racso.test_login.data.models.toUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDatasource @Inject constructor() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(username: String, password: String): User? {
        try {
            val result = auth.signInWithEmailAndPassword(username, password).await()
            return result.user?.toUser()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw Exception("Invalid email or password")
        }
    }

    suspend fun register(email: String, phoneNumber: String, password: String): User? {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            return result.user?.toUser()

        } catch (e: FirebaseAuthUserCollisionException) {
            throw Exception("User already exists with this email")
        }
    }

    fun logout(){
        auth.signOut()
    }

}