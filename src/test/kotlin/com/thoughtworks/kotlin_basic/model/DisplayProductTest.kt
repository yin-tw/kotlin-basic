package com.thoughtworks.kotlin_basic.model

import com.thoughtworks.kotlin_basic.util.PrintUtil
import com.thoughtworks.kotlin_basic.view.DisplayProduct
import io.mockk.*
import kotlin.test.Test


class DisplayProductTest {

    var productService = mockk<ProductService>()
    var printUtil = mockk<PrintUtil>()


    @Test
    fun `fetch and prints products from ProductService`() {
        val mockProducts = listOf(Product("mockSKU","mockName", 100.00, "mockType", "mockURL"))
        val productHeaders = Product::class.java.declaredFields.map{ it.name }
        val mockProductStringList = listOf(listOf("mockSKU","mockName", 100.00.toString(), "mockType", "mockURL"))

        every { productService.fetchProducts() } returns mockProducts
        every { printUtil.printTable(any(), any()) } just runs

        DisplayProduct(productService, printUtil).displayAllProducts()

        verify { printUtil.printTable(productHeaders, mockProductStringList) }
    }
}