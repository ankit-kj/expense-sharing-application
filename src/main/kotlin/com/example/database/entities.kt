package com.example.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserTable : Table<UserTableEntity>("Users"){

    val userId = int("userId").primaryKey().bindTo { it.userId }
    val name = varchar("name").bindTo { it.name }
    val email = varchar("email").bindTo{it.email}
    val mobile = varchar("mobile").bindTo{it.mobile}
}

interface UserTableEntity : Entity<UserTableEntity>{
    companion object : Entity.Factory<UserTableEntity>()

    val userId : Int
    val name : String
    val email : String
    val mobile : String
}