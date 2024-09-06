package com.thoughtworks.kotlin_basic.model

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import retrofit2.Response
import java.io.IOException
import java.lang.Boolean.TRUE
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductServiceTest {

    val productApi = mockk<ProductApi>()
    val productService = ProductService(productApi)

    @Nested
    inner class FetchProducts {

        @Test
        fun `get products when fetch products api call is successful`() {
            val mockProducts = listOf(Product("mockSKU","mockName", 100.00, "mockType", "mockURL"))
            every { productApi.getProducts().execute() } returns Response.success(mockProducts)

            val results = productService.fetchProducts()

            assertEquals( mockProducts, results)
        }


        @Test
        fun `throws Exception with message when fetch products api call returns empty response`() {
            every { productApi.getProducts().execute() } returns Response.success(null)

            val exception = assertThrows<Exception> { productService.fetchProducts() }

            assertEquals("Empty product response from product API", exception.message)
        }

        @Test
        fun `throws IO exception when fetch products api call throws IO exception`() {
            every { productApi.getProducts().execute() } throws IOException("Unable to connect to host")

            val exception = assertThrows<IOException> { productService.fetchProducts() }

            assertEquals("IOException caught for product API: Unable to connect to host", exception.message)
        }

    }

    @Nested
    inner class FetchInventories {
        @Test
        fun `get inventories when fetch inventories api call is successful`() {
            val mockInventories = listOf(Inventory("mockSKU","mockZone", 10))
            every { productApi.getInventories().execute() } returns Response.success(mockInventories)

            val results = productService.fetchInventories()

            assertEquals( mockInventories, results)
        }

        @Test
        fun `throws Exception with message when fetch inventories api call returns empty response`() {
            every { productApi.getInventories().execute() } returns Response.success(null)

            val exception = assertThrows<Exception> { productService.fetchInventories() }

            assertEquals("Empty inventory response from product API", exception.message)
        }

        @Test
        fun `throws IO exception when fetch inventories api call throws IO exception`() {
            every { productApi.getInventories().execute() } throws IOException("Unable to connect to host")

            val exception = assertThrows<IOException> { productService.fetchInventories() }

            assertEquals("IOException caught for product API: Unable to connect to host", exception.message)
        }

    }

    @Nested
    inner class FetchProductInventories {
        val mockProductA = Product("mockSKU1","AAA", 123.00, "NORMAL", "aaaURL")
        val mockProductB = Product("mockSKU2","BBB", 456.00, "NORMAL", "bbbURL")
        val mockProductC = Product("mockSKU3","CCC", 234.00, "HIGH_DEMAND", "cccURL")
        val mockProductD = Product("mockSKU4","DDD", 345.00, "HIGH_DEMAND", "dddURL")
        val mockProductE = Product("mockSKU5","EEE", 213.00, "HIGH_DEMAND", "eeeURL")


        @BeforeEach
        fun beforeEach() {
            val mockProducts = listOf( mockProductA, mockProductB, mockProductC, mockProductD, mockProductE)
            val mockInventories = listOf(
                Inventory(mockProductA.sku,"mockZoneA", 10),
                Inventory(mockProductA.sku,"mockZoneB", 20),
                Inventory(mockProductA.sku,"mockZoneC", 30),
                Inventory(mockProductB.sku,"mockZoneD", 10),
                Inventory(mockProductC.sku,"mockZoneE", 60),
                Inventory(mockProductC.sku,"mockZoneF", 40),
                Inventory(mockProductC.sku,"mockZoneG", 30),
                Inventory(mockProductD.sku ,"mockZoneH", 10),
                Inventory(mockProductE.sku,"mockZoneI", 25),
                Inventory(mockProductE.sku,"mockZoneJ", 31),
            )

            every { productApi.getProducts().execute() } returns Response.success(mockProducts)
            every { productApi.getInventories().execute() } returns Response.success(mockInventories)
        }

        @Test
        fun `stock of a product equals the sum of stocks in various regions`() {
            val results:List<ProductInventory> = productService.fetchProductInventories()
            val productAAA: ProductInventory? = results.find{ it.name == "AAA"}

            assertEquals( 60, productAAA?.stockInventory)
        }

        @Test
        fun `price of a NORMAL product is equal original price`() {
            val results:List<ProductInventory> = productService.fetchProductInventories()
            val productA: ProductInventory? = results.find{ it.name == "AAA"}

            assertEquals( mockProductA.price, productA?.actualPrice)
        }

        @Test
        fun `price of a HIGH_DEMAND product with more than 100 quantity is equal original price`() {
            val results:List<ProductInventory> = productService.fetchProductInventories()
            val productC: ProductInventory? = results.find{ it.name == "CCC"}
            val productQuantity = productC?.stockInventory ?: 0

            assertEquals( TRUE, productQuantity > 100)
            assertEquals( mockProductC.price, productC?.actualPrice)
        }

        @Test
        fun `price of a HIGH_DEMAND product with quantity less than 30 is 150 percent original price`() {
            val results:List<ProductInventory> = productService.fetchProductInventories()
            val productD: ProductInventory? = results.find{ it.name == "DDD"}
            val productQuantity = productD?.stockInventory ?: 0

            assertEquals( TRUE, productQuantity < 30)
            assertEquals( mockProductD.price*1.5, productD?.actualPrice)
        }

        @Test
        fun `price of a HIGH_DEMAND product with quantity less than or equal 100 but above 30 is 120 percent original price`() {
            val results:List<ProductInventory> = productService.fetchProductInventories()
            val productE: ProductInventory? = results.find{ it.name == "EEE"}
            val productQuantity = productE?.stockInventory ?: 0


            assertEquals( TRUE, productQuantity in 31..100)
            assertEquals( mockProductE.price*1.2, productE?.actualPrice)
        }
    }
}