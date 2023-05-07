package com.powerplay.zimmy.powerplaybeerapp.model


import com.google.gson.annotations.SerializedName

data class Hop(
    @SerializedName("add")
    val add: String,
    @SerializedName("amount")
    val amount: Amount,
    @SerializedName("attribute")
    val attribute: String,
    @SerializedName("name")
    val name: String
)