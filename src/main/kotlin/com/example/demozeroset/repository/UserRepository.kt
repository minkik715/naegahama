package com.example.demozeroset.repository

import com.example.demozeroset.domain.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: MongoRepository<User, ObjectId> , QuerydslPredicateExecutor<User>{
}

