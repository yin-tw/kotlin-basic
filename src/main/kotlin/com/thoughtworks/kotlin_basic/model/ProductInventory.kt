package com.thoughtworks.kotlin_basic.model


data class ProductInventory(

    val sku: String,

    val name: String,

    val actualPrice: Double,

    val stockInventory: Int,

    val imageUrl: String

)
