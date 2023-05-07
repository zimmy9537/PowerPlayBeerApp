package com.powerplay.zimmy.powerplaybeerapp.model


import com.google.gson.annotations.SerializedName

data class Method(
    @SerializedName("fermentation")
    val fermentation: Fermentation,
    @SerializedName("mash_temp")
    val mashTemp: List<MashTemp>,
    @SerializedName("twist")
    val twist: String
)