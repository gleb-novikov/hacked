package com.hacked.app.retrofit.data

import com.google.gson.annotations.SerializedName

data class UserRequest (
    @SerializedName("userName")
    var userName: String,

    @SerializedName("level")
    var level: Int,

    @SerializedName("exp")
    var exp: Int,

    @SerializedName("money")
    var money: Int
)