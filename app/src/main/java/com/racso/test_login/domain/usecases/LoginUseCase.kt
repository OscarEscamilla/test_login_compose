package com.racso.test_login.domain.usecases

import com.racso.test_login.data.models.User
import com.racso.test_login.data.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): User? {
        return repository.login(email, password)
    }
}