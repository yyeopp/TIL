package com.example.mvc.controller.exception

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest
@AutoConfigureMockMvc
class ExceptionApiControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(
            get("/api/exception/hello")
        ).andExpect(
            status().isOk
        ).andExpect(
            content().string("hello")
        ).andDo(print())
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "yyeopp")
        queryParams.add("age", "29")
        mockMvc.perform(get("/api/exception").queryParams(queryParams))
            .andExpect(status().isOk)
            .andExpect(content().string("yyeopp 29"))
            .andDo(print())

    }

    @Test
    fun getFailTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "yyeopp")
        queryParams.add("age", "9")
        mockMvc.perform(get("/api/exception").queryParams(queryParams))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType("application/json"))
            .andDo(print())

    }
}