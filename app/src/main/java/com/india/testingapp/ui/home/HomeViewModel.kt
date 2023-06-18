package com.india.testingapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.india.testingapp.LoginRepo
import com.india.testingapp.NetworkResult
import com.india.testingapp.models.response.UserListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepo: HomeRepo) : ViewModel() {
    val userListResponseLiveData: LiveData<NetworkResult<UserListResponse>>
        get() = homeRepo.userListResponseLiveData
    fun getUserData(){
        viewModelScope.launch {
            homeRepo.getUserData()
        }
    }

}