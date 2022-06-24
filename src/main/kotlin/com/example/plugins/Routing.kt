package com.example.plugins

import GroupDraft
import MySQLhandler
import Transaction
import User
import UserDraft
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.request.*


fun Application.configureRouting() {


    routing {

        val repository = MySQLhandler()

        get("/") {
            call.respondText("Hello Welcome to the HomePage of Expense Sharing Application")
        }

        post("/addUser"){
            val newUserDraft = call.receive<UserDraft>()
            val newUserId = repository.addUser(newUserDraft)

            if(newUserId == -1) {
                call.respond(HttpStatusCode.BadRequest , "No such group exists ...enter a valid group name")
                return@post
            }

            if(newUserId == -2){
                call.respond(HttpStatusCode.BadRequest , "User with same name already exists...enter a unique name")
                return@post
            }

            call.respond(User(newUserId , newUserDraft.name, newUserDraft.email, newUserDraft.mobile))
        }

        post("/addGroup"){

            val name = call.receive<GroupDraft>()
//            if(name.groupName == null) {
//                call.respond(HttpStatusCode.BadRequest,"Group Name cannot be null")
//                return@post
//            }

            val id =repository.addGroup(name.groupName)

            if(id == -1){
                call.respond(HttpStatusCode.BadRequest , "Group already exists")
                return@post
            }

            call.respond(Pair(id,name.groupName))

        }

        post("/addTransaction"){
            val transaction = call.receive<Transaction>()
            val rnVal = repository.addTransaction(transaction)

            if(rnVal == -1){
                call.respond(HttpStatusCode.BadRequest , "No such group exists")
                return@post
            }

            call.respond(HttpStatusCode.OK)
        }

    }
}
