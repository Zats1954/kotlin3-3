package ru.netology

class MessageService {
    var messages = mutableListOf<Message>()

    fun addMessage(title: String, text: String): Int {
        val newId = if (messages.isEmpty()) {
            0
        } else {
            messages[messages.lastIndex].id + 1
        }
        val newMessage = Message(id = newId, title = title, text = text)
        messages.add(newMessage)
        return newId
    }

    fun get(
        messageIds: String = "",
        userId: Int = 0,
        offset: Int = 0,
        count: Int = 20,
        sort: Int = 0
    ): List<Message>? {
        val idMessages: List<Int> = messageIds.split(",").map { it.toInt() }
        return if (idMessages.isNotEmpty()) {
            messages.filter { idMessages.contains(it.id) }
        } else {
            messages
        }
    }

    fun getById(
        messageIds: Int,
        ownerId: Int = 0,
        needWiki: Int = 0
    ): Message? {
        messages.firstOrNull { it.id == messageIds }
            .let {
                if (it == null) {
                    println("getById: Ошибка 180 Сообщение $messageIds не найдено ")
                }
                return it
            }
    }
}



