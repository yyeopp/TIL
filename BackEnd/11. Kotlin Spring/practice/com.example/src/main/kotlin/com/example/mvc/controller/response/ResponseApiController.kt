package com.example.mvc.controller.response

import com.example.mvc.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/response")
class ResponseApiController {

    // 1. get 4xx
    @GetMapping
    fun getMapping(@RequestParam age: Int?): ResponseEntity<String> {
        return age?.let {
            // age not null
            if (it < 20) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값은 20보다 커야 합니다.")
            }

            ResponseEntity.ok("OK")
        } ?: kotlin.run {
            // age is null
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값이 누락되었습니다.")
        }

        /*
        // 1. age == null -> 400 error
        if (age == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값이 누락되었습니다.")
        }

        // 2. age < 20 -> 400 error
        if (age < 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("age 값은 20보다 커야 합니다.")
        }
        */
    }

    // 2. post 200
    @PostMapping
    fun postMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(userRequest) // object mapper -> object -> json
    }

    // 3. put 201
    @PutMapping
    fun putMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<UserRequest> {
        // 1. 기존 데이터가 없어 새로 생성했다.
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest)
    }

    // 4. delete 500
    @DeleteMapping("/{id}")
    fun deleteMapping(@PathVariable id: Int): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
    }
}
