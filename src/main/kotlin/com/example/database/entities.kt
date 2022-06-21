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

object GroupTable : Table<GroupTableEntity>("groups"){
    val groupId = int("groupId").primaryKey().bindTo { it.groupId }
    val groupName = varchar("groupName").bindTo{it.groupName}
}

interface GroupTableEntity : Entity<GroupTableEntity>{
    companion object : Entity.Factory<GroupTableEntity>()

    val groupId : Int
    val groupName : String

}

object GroupUserPairTable : Table<GroupUserPairIdentity>("group_user_pair"){
    val userId = int("userId").primaryKey().bindTo { it.userId }
    val groupId = int("groupId").primaryKey().bindTo { it.groupId }
}

interface GroupUserPairIdentity : Entity<GroupUserPairIdentity>{
    companion object : Entity.Factory<GroupUserPairIdentity>()

    val userId : Int
    val groupId : Int
}