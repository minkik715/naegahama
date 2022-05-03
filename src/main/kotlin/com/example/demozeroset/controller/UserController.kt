package com.example.demozeroset.controller

import com.example.demozeroset.domain.User
import com.example.demozeroset.dto.UserRequestDto
import com.example.demozeroset.repository.UserRepository
import com.example.demozeroset.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    val userService : UserService ?= null

    @GetMapping("api/hello")
    fun hello() : String {
        return "Hello"
    }

    /*@PostMapping("/api/signup")
    fun signup(@RequestBody userRequestDto: UserRequestDto) : Boolean? {
        return userService?.join(userRequestDto)
    }*/
}