package com.example.plugins

import MySQLhandler
import User
import UserDraft
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
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
            val newUser = repository.addUser(newUserDraft)

            if(newUser == null) {
                call.respond(HttpStatusCode.BadRequest , "No such group exists ...enter a valid group name")
                return@post
            }

            call.respond(newUser)
        }

        post("/addGroup"){

            val name = call.parameters["groupName"]
            if(name == null) {
                call.respond(HttpStatusCode.BadRequest,"Group Name cannot be null")
                return@post
            }

            val id =repository.addGroup(name)
            if(id == -1){
                call.respond(HttpStatusCode.BadRequest , "Group already exists")
                return@post
            }

            call.respond(Pair(id,name))
        }

        post("/addTransaction"){

        }

    }
}
