package com.example.demozeroset.controller

import com.example.demozeroset.dto.ConfigurationRequestDto
import com.example.demozeroset.service.ConfigurationService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigurationController @Autowired constructor(val configurationService: ConfigurationService) {

    @GetMapping("/api/user/configuration/{userId}")
    fun getUserConfigurationInfo(@PathVariable userId : ObjectId, configurationRequestDto: ConfigurationRequestDto) : Boolean{
        return configurationService.changeConfiguration(userId, configurationRequestDto)
    }
}
