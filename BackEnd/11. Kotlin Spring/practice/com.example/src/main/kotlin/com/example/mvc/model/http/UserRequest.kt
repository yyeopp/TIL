package com.example.mvc.model.http

import com.example.mvc.annotation.StringFormatDateTime
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

// 클래스 단위 지정
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserRequest(
    @field:NotEmpty
    @field:Size(min = 2, max = 8)
    var name: String? = null,

    @field:PositiveOrZero
    var age: Int? = null,

    @field:Email
    var email: String? = null,

    @field:NotBlank
    var address: String? = null,

    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
//    @JsonProperty("phone_number") // 프로퍼티 단위 지정
    var phoneNumber: String? = null,

    @StringFormatDateTime(pattern = "yyyy-MM-dd HH:mm:ss", message = "패턴이 올바르지 않습니다")
    var createdAt: String? = null, // yyyy-MM-dd HH:mm:ss  ex) 2020-10-02 13:00:00
)

/*
{
    "name": "litsynp",
    "age": 10,
    "email": "litsynp@example.com",
    "address": "address",
    "phone_number": "010-1111-2222"
}
 */
