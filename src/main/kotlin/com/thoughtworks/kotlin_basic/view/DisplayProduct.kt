package com.thoughtworks.kotlin_basic.view

import com.thoughtworks.kotlin_basic.util.PrintUtil
import com.thoughtworks.kotlin_basic.model.Product
import com.thoughtworks.kotlin_basic.model.ProductService
import kotlin.reflect.full.memberProperties

class DisplayProduct(
    var productService: ProductService = ProductService(),
    var printUtil: PrintUtil = PrintUtil()) {

    fun displayAllProducts() {
        val products = productService.fetchProducts()
        val productHeaders = Product::class.java.declaredFields.map { it.name }
        val productsList = mutableListOf<List<String>>()

        for (product in products) {
            val productProperties = mutableListOf<String>()
            for (field in productHeaders) {
                val prop = Product::class.memberProperties.single { it.name == field }
                productProperties.add(prop.get(product).toString())
            }
            productsList.add(productProperties)
        }

        printUtil.printTable(productHeaders, productsList.toList())
    }

}
