package ru.netology

import java.time.Clock

class Message(
    val id: Int = 0,
    val ownerId: Int = 0,
    var title:String,
    var text: String,
    var date: Long = Clock.systemUTC().millis()
){
}