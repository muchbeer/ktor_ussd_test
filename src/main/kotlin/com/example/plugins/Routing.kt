package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/ussd") {
            println("this show the message entered")
            val ussdParameters = call.receiveParameters()
            val sessionID = ussdParameters["sessionId"].toString()
            val phoneNumber = ussdParameters["phoneNumber"].toString()
            val text = ussdParameters["text"].toString()

           val response = ussdMenu(text)

            call.respondText (response)

        }

        get("/receive") {
            print("life is good")
        }
    }
}

fun ussdMenu(text: String): String {

    val response = StringBuilder()

    if (text.isEmpty()) {
        // This is the first request. Note how we start the response with CON
        response.append("CON What would you like to check\n1. My account\n2. My phone number")

    } else if (text.contentEquals("1")) {
        // Business logic for first level response
        response.append("CON Choose account information you want to view\n1. Account number");

    } else if (text.contentEquals("2")) {

        // This is a terminal request. Note how we start the response with END
        val ussdPhoneNumber = "255757022731"
        response.append("END Your phone number is ");
        response.append(ussdPhoneNumber)

    } else if (text.contentEquals("1*1")) {
        // This is a second level response where the user selected 1 in the first instance

        val accountNumber = "ACC100101"
        response.append("END Your account number is "); // This is a terminal request. Note how we start the response with END
        response.append(accountNumber)
    }
    return response.toString()
}