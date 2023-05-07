package com.powerplay.zimmy.powerplaybeerapp.model


import com.google.gson.annotations.SerializedName

data class MashTemp(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("temp")
    val temp: Temp
)