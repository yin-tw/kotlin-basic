package com.thoughtworks.kotlin_basic.model

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import retrofit2.Response
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductServiceTest {

    val productApi = mockk<ProductApi>()
    val productService = ProductService(productApi)

    @Test
    fun `get products when product Api call is successful`() {
        val mockProducts = listOf(Product("mockSKU","mockName", 100.00, "mockType", "mockURL"))
        every { productApi.getProducts().execute() } returns Response.success(mockProducts)

        val results = productService.fetchProducts()

        assertEquals( mockProducts, results)
    }

    @Test
    fun `throws Exception with message when product Api call returns empty response`() {
        every { productApi.getProducts().execute() } returns Response.success(null)

        val exception = assertThrows<Exception> { productService.fetchProducts() }

        assertEquals("Empty response from product API", exception.message)
    }

    @Test
    fun `throws IO exception when product Api call throws IO exception`() {
        every { productApi.getProducts().execute() } throws IOException("Unable to connect to host")

        val exception = assertThrows<IOException> { productService.fetchProducts() }

        assertEquals("IOException caught for product API: Unable to connect to host", exception.message)
    }

}