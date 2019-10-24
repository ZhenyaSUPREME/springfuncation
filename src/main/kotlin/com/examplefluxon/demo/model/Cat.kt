package com.examplefluxon.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Cat(@Id var id: String, val name:String, val age:Int) {
}