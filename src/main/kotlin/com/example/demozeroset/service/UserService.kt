package com.example.demozeroset.service

import com.example.demozeroset.domain.User
import com.example.demozeroset.dto.UserRequestDto
import com.example.demozeroset.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(val userRepository : UserRepository) {
    fun join (userRequestDto : UserRequestDto) : User?{
        return userRepository?.save(User(userRequestDto))
    }

    fun findUserById(userId : ObjectId) : User? {
        return userRepository?.findByIdOrNull(userId)
    }
}