package com.india.testingapp

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.india.testingapp.models.request.RequestData
import com.india.testingapp.models.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepo: LoginRepo) : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    val verifyResponseLiveData: LiveData<NetworkResult<LoginResponse>>
        get() = loginRepo.verifyAuthUserResponseLiveData

    fun validateInput(): Pair<Boolean, String> {

        var result = Pair(true, "")
        if (!isValidEmail(email.value!!)) {
            result = Pair(false, "Invalid email id")
        } else if (TextUtils.isEmpty(password.value!!) || password.value!!.length < 5) {
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun loginUser() {
        viewModelScope.launch {
            loginRepo.loginUser(RequestData(email.value, password.value))
        }
    }

}