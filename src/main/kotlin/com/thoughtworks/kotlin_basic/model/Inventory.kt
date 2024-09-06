package com.thoughtworks.kotlin_basic.model

import com.google.gson.annotations.SerializedName

data class Inventory(

    @SerializedName("SKU")
    val sku: String,

    @SerializedName("zone")
    val zone: String,

    @SerializedName("quantity")
    val quantity: Int
)
