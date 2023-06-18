package com.india.testingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.india.testingapp.api.LoginAPI
import com.india.testingapp.models.request.RequestData
import com.india.testingapp.models.response.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepo @Inject constructor(private val loginAPI: LoginAPI) {



    private val _verifyAuthUserResponseLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
    val verifyAuthUserResponseLiveData: LiveData<NetworkResult<LoginResponse>>
        get() = _verifyAuthUserResponseLiveData

    suspend fun loginUser(requestData: RequestData) {
        _verifyAuthUserResponseLiveData.postValue(NetworkResult.Loading())
        val response = loginAPI.login(requestData)
        handleLoginResponse(response)

    }
    private fun handleLoginResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _verifyAuthUserResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody()!=null){
            if(response.code()==400){
                _verifyAuthUserResponseLiveData.postValue(NetworkResult.Error("Invalid Email and Password"))
            }else
            _verifyAuthUserResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong ${response.code()}"))
        }
        else{
            _verifyAuthUserResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}