package com.racso.test_login.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.racso.test_login.data.models.User
import com.racso.test_login.domain.usecases.LoginUseCase
import com.racso.test_login.domain.usecases.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val singUpUseCase: SignupUseCase,
): ViewModel() {

    private val _loginResult = MutableStateFlow<Result<User?>>(Result.success(null))
    val loginResult: StateFlow<Result<User?>> = _loginResult

    private val _singupResult = MutableStateFlow<Result<User?>>(Result.success(null))
    val signupResult: StateFlow<Result<User?>> = _singupResult

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = loginUseCase.invoke(email, password)
                _loginResult.value = Result.success(user)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _loginResult.value = Result.failure(Exception("Invalid email or password"))
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            }
        }
    }

    fun signUp(email: String, password: String, phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = singUpUseCase.invoke(email, password, phone)
                _singupResult.value = Result.success(user)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _singupResult.value = Result.failure(Exception("Invalid email or password"))
            } catch (e: Exception) {
                _singupResult.value = Result.failure(e)
            }
        }
    }
}