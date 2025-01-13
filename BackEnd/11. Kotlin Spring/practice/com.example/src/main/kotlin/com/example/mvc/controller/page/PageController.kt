package com.example.mvc.controller.page

import com.example.mvc.model.http.UserRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PageController {

    @GetMapping("/main")
    fun main(): String {
        println("init main")
        return "main.html"
    }

    @ResponseBody  // @Controller 붙은 클래스에서 json 반환하기
    @GetMapping("/test")
    fun response(): UserRequest {
        return UserRequest().apply {
            this.name = "litsynp"
        }
    }
}
