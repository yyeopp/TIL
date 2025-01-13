package com.example.mvc.controller.get

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class GetApiController {

    // The (relatively) new way
    @GetMapping(path = ["/hello", "/abcd"]) // path = [] for multiple endpoints
    fun hello(): String {
        return "Hello Kotlin"
    }

    // Old way
    @RequestMapping(method = [RequestMethod.GET], value = ["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping"
    }

    @GetMapping("/get-mapping/path-variable/{name}/{age}")
    fun pathVariable(
        @PathVariable name: String,
        @PathVariable age: Int // Int인데 String이면 오류 발생
    ): String {
        println("${name}, ${age}")
        return "${name} ${age}"
    }

    @GetMapping("/get-mapping/path-variable2/{name}/{age}")
    fun pathVariable2(
        @PathVariable(value = "name") _name: String,
        @PathVariable(name = "age") age: Int
    ): String {
        println("${_name}, ${age}")
        return "${_name} ${age}"
    }

    @GetMapping("/get-mapping/query-param")
    fun queryParam(
        @RequestParam(value = "name") name: String,
        @RequestParam(name = "age") age: Int
    ): String {
        println("${name}, ${age}")
        return "${name} ${age}"
    }

    // name, age, address, email
    // -
    // phoneNumber -> phone-number 필요하면 @RequestParam으로 받아야 함
    @GetMapping("/get-mapping/query-param/object")
    fun queryParamObject(
        userRequest: UserRequest // 바로 Query Parameter 매칭
    ): UserRequest {
        println(userRequest)
        return userRequest
    }

    // phone-number도 받을 수 있음
    @GetMapping("/get-mapping/query-param/map")
    fun queryParamMap(
        @RequestParam map: Map<String, Any>
    ): Map<String, Any> {
        println(map)
        val phoneNumber = map.get("phone-number")
        println("phoneNumber: ${phoneNumber}")
        return map
    }
}
