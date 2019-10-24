package com.examplefluxon.demo.repository

import com.examplefluxon.demo.model.Cat
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CatMonRepository: ReactiveMongoRepository<Cat,String> {

}