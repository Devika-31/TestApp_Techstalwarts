package com.india.testingapp.models.request

import com.google.gson.annotations.SerializedName

data class RequestData(
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
)
