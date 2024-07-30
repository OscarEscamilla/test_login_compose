package com.racso.test_login.data.repositories

import com.racso.test_login.data.remote.AuthRemoteDatasource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authRemoteDatasource: AuthRemoteDatasource){

    suspend fun login(email: String, password: String) = authRemoteDatasource.login(email, password)

    fun logout() = authRemoteDatasource.logout()

    suspend fun signup(email: String, phone: String, password: String) = authRemoteDatasource.register(email, phone,password)
}