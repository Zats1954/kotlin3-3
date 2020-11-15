package ru.netology

class Chat(
    val id: Int = 0,
    val ownerId: Int =0,
    var guestId: Int = 0,
    val title: String = "",
    val messages: MutableList<Message> = mutableListOf<Message>()
) { }
