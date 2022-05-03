package com.example.demozeroset.domain

import com.example.demozeroset.configEnum.Role
import com.example.demozeroset.dto.UserRequestDto
import com.querydsl.core.annotations.QueryEntity
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue

@Entity
@QueryEntity
@Document(collection = "user")
class User() {


    @Id @GeneratedValue
    var id: ObjectId ?= null

    @Field
    var nickname: String?= null

    @Field
    var age : Int?= null

    @Field
    var role : Role = Role.BASIC

    @Field
    var createdAt : LocalDateTime = LocalDateTime.now()

    @Field
    var configuration :Configuration = Configuration()

    constructor(userRequestDto: UserRequestDto) : this() {
        this.nickname = userRequestDto.name
        this.age = userRequestDto.age
    }


}

