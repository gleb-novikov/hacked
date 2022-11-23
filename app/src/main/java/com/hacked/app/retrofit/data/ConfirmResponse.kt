package com.hacked.app.retrofit.data

import com.google.gson.annotations.SerializedName

data class ConfirmResponse (
    @SerializedName("user")
    var user: User,

    @SerializedName("message")
    var message: String,

    @SerializedName("token")
    var token: String
)
