package com.racso.test_login.domain.usecases

import com.racso.test_login.data.models.User
import com.racso.test_login.data.repositories.AuthRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String, phone: String, ): User? {
        return repository.signup(email, phone ,password)
    }
}