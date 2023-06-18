package com.india.testingapp.models.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") var appToken: String? = null,
)
