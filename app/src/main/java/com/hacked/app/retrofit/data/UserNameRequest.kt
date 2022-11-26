package com.hacked.app.retrofit.data

import com.google.gson.annotations.SerializedName

data class UserNameRequest (
    @SerializedName("userName")
    var userName: String
)