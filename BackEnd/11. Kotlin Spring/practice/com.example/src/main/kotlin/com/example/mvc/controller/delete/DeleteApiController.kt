package com.example.mvc.controller.delete

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Validated  // Bean이 아니므로 붙여줘야 한다
class DeleteApiController {

    // 1. path variable
    // 2. request param

    @DeleteMapping("/delete-mapping")
    fun deleteMapping(
        @RequestParam(value = "name")
        _name: String,

        @RequestParam(value = "age")
        @NotNull(message = "age 값이 누락되었습니다.")
        @Min(value = 20, message = "age는 20보다 커야 합니다.")
        _age: Int
    ): String {
        println(_name)
        println(_age)
        return "${_name} ${_age}"
    }

    @DeleteMapping("/delete-mapping/name/{name}/age/{age}")
    fun deleteMappingPath(
        @PathVariable("name")
        @NotNull
        @Size(min = 2, max = 10)
        _name: String,

        @PathVariable("age")
        @NotNull(message = "age 값이 누락되었습니다.")
        @Min(value = 20, message = "age는 20보다 커야 합니다.")
        _age: Int
    ): String {
        println(_name)
        println(_age)
        return "${_name} ${_age}"
    }
}
