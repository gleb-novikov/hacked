package com.hacked.app.retrofit.data

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("user")
    var user: User
)
