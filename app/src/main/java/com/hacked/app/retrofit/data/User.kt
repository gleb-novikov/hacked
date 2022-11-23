package com.hacked.app.retrofit.data

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("userName")
    var userName: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("level")
    var level: Int,

    @SerializedName("exp")
    var exp: Int,

    @SerializedName("money")
    var money: Int
)
