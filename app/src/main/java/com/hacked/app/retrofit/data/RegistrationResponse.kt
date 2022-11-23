package com.hacked.app.retrofit.data

import com.google.gson.annotations.SerializedName

data class RegistrationResponse (
    @SerializedName("user")
    var user: User
)
