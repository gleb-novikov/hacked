package com.hacked.app.retrofit.data

import com.google.gson.annotations.SerializedName

data class ConfirmRequest (
    @SerializedName("activationCode")
    var activationCode: String
)