package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello Welcome to the HomePage of Expense Sharing Application")
        }

        post("/createuser"){

        }

        put("/creategroup"){
            // usera are already exsisting we are just forming a group out of them
            // so we are kind of updating therefore using put instead of post
        }

        post("/transaction"){

        }




    }
}
