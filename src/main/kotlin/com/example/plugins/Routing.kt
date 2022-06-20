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
            call.respond(newUser)
        }

        put("/addGroup"){
            // usera are already exsisting we are just forming a group out of them
            // so we are kind of updating therefore using put instead of post
        }

        post("/addTransaction"){

        }

    }
}
