package com.thoughtworks.kotlin_basic.model

import java.io.IOException

class ProductService( private val productApi: ProductApi = RetrofitClient.getClient().create(ProductApi::class.java)) {
    fun fetchProducts(): List<Product> {
        val products: List<Product>

        try {
            val productResponse = productApi.getProducts().execute()

            if (productResponse.isSuccessful) {
                products = productResponse.body() ?: throw Exception("Empty product response from product API")
            } else {
                throw Exception(productResponse.errorBody().toString())
            }
        } catch (e: IOException) {
            throw IOException("IOException caught for product API: ${e.message}")
        }

        return products
    }

    fun fetchInventories(): List<Inventory> {
        val inventories: List<Inventory>

        try {
            val inventoryResponse = productApi.getInventories().execute()

            if (inventoryResponse.isSuccessful) {
                inventories = inventoryResponse.body() ?: throw Exception("Empty inventory response from product API")
            } else {
                throw Exception(inventoryResponse.errorBody().toString())
            }
        } catch (e: IOException) {
            throw IOException("IOException caught for product API: ${e.message}")
        }

        return inventories
    }
}