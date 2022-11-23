package com.hacked.app.retrofit.data

import com.google.gson.annotations.SerializedName

data class RegistrationRequest (
    @SerializedName("userName")
    var userName: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String
)