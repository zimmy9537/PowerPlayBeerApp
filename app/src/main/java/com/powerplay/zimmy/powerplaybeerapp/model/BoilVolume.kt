package com.powerplay.zimmy.powerplaybeerapp.model


import com.google.gson.annotations.SerializedName

data class BoilVolume(
    @SerializedName("unit")
    val unit: String,
    @SerializedName("value")
    val value: Int
)