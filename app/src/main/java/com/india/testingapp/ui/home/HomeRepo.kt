package com.india.testingapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.india.testingapp.NetworkResult
import com.india.testingapp.api.LoginAPI
import com.india.testingapp.models.response.LoginResponse
import com.india.testingapp.models.response.UserListResponse
import retrofit2.Response
import javax.inject.Inject

class HomeRepo @Inject constructor(private val loginAPI: LoginAPI){
    private val _userListResponseLiveData = MutableLiveData<NetworkResult<UserListResponse>>()
    val userListResponseLiveData: LiveData<NetworkResult<UserListResponse>>
        get() = _userListResponseLiveData
    suspend fun getUserData(){
        _userListResponseLiveData.postValue(NetworkResult.Loading())
        val response = loginAPI.getUserList()
        handleRespo(response)
    }

    private fun handleRespo(response: Response<UserListResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userListResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody()!=null){
                _userListResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong ${response.code()}"))
        }
        else{
            _userListResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}