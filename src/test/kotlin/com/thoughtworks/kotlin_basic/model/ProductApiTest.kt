package com.thoughtworks.kotlin_basic.model

import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: ProductApi

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ProductApi::class.java)
    }

    @Test
    fun `get Products, 200 response returns list of products`() {
        val mockProductList = listOf(Product("mockSKU","mockName", 100.00, "mockType", "mockURL"))
        val mockProductJson = GsonBuilder().create().toJson(mockProductList)
        val mockSuccessResponse = MockResponse().setBody(mockProductJson)
        server.enqueue(mockSuccessResponse)

        val response = api.getProducts().execute()

        assertEquals(200, response.code())
        assertEquals(mockProductList, response.body())
    }

    @Test
    fun `get Products, 400 response returns client error`() {
        val mockErrorResponse = MockResponse()
            .setResponseCode(400)

        server.enqueue(mockErrorResponse)

        val response = api.getProducts().execute()

        assertEquals(response.code(), 400)
        assertEquals(response.message(), "Client Error")
    }

    @Test
    fun `get Inventories, 200 response returns list of inventory`() {
        val mockInventoryList = listOf(Inventory("mockSKU","mockZone", 10))
        val mockInventoryJson = GsonBuilder().create().toJson(mockInventoryList)
        val mockSuccessResponse = MockResponse().setBody(mockInventoryJson)
        server.enqueue(mockSuccessResponse)

        val response = api.getInventories().execute()

        assertEquals(200, response.code())
        assertEquals(mockInventoryList, response.body())
    }


    @Test
    fun `get Inventories, 400 response returns client error`() {
        val mockErrorResponse = MockResponse()
            .setResponseCode(400)

        server.enqueue(mockErrorResponse)

        val response = api.getInventories().execute()

        assertEquals(response.code(), 400)
        assertEquals(response.message(), "Client Error")
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }
}