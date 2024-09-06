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

    fun fetchProductInventories(): List<ProductInventory> {
        val products: List<Product>
        val inventories: List<Inventory>
        val productInventories = mutableListOf<ProductInventory>()

        try {
            products = fetchProducts()
            inventories = fetchInventories()
        } catch (e: Exception ) {
            throw Exception(e.message)
        } catch (e: IOException) {
            throw IOException(e.message)
        }

        for (product in products) {
            var stockInventory = 0
            var productPrice = product.price

            for ( inventory in inventories) {
                if (inventory.sku == product.sku) {
                    stockInventory += inventory.quantity
                }
            }

            if (product.type == "HIGH_DEMAND") {
                when (stockInventory){
                    in 31..100 -> productPrice *= 1.2
                    in 0..30 -> productPrice *= 1.5
                }
            }


            val productInventory = ProductInventory(
                product.sku,
                product.name,
                productPrice,
                stockInventory,
                product.imageUrl
                )

            productInventories.add(productInventory)
        }

        return productInventories
    }
}