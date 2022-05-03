package com.example.demozeroset.service

import com.example.demozeroset.dto.ConfigurationRequestDto
import com.example.demozeroset.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ConfigurationService @Autowired constructor(val userRepository: UserRepository) {


    fun changeConfiguration(userId: ObjectId, configurationRequestDto: ConfigurationRequestDto) :Boolean {
        val findUser = userRepository.findByIdOrNull(userId);

        findUser?.let {
            it.configuration.changeConfiguration(configurationRequestDto)
            userRepository.save(it)
            return true
        }

        return false
    }
}