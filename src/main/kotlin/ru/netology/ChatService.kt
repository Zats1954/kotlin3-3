package ru.netology

class ChatService {
    var chats = mutableListOf<Chat>()

    fun createChat(
        ownerId: Int,
        guestId: Int,
        name: String = "newChat"
    ): Chat {
        chats.filterNot {
            (it.ownerId == ownerId && it.guestId == guestId) ||
                    (it.guestId == ownerId && it.ownerId == guestId)
        }      // нет ли уже созданного такого чата
            .let {
                chats.add(Chat(id = chats.maxByOrNull { it.id }.let { if (it != null) it.id + 1 else 0 },
                    ownerId = ownerId,
                    guestId = guestId,
                    title = name
                )
                )
            }
        return chats.last()
    }


    fun deleteChat(chatId: Int) {
        chats.removeAll { it.id == chatId }
    }

    fun allChats(): List<Chat> {
        return chats
    }


    fun writeMessage(ownerId: Int, guestId: Int, title: String, message: String) {

        chats.find {
            (it.ownerId == ownerId && it.guestId == guestId) ||
                    (it.guestId == ownerId && it.ownerId == guestId)
        }
            .let {
                val chat = if (it == null) {
                    if (ownerId == guestId) return
                    createChat(ownerId, guestId, "$ownerId to $guestId")
                } else {
                    it
                }
                chat.messages.add(
                    Message(maxId(chat) + 1, ownerId, title, message)
                )
            }

    }

    private fun maxId(chat: Chat): Int {
        var maxId = 0
        chat.messages.forEach { if (it.id > maxId) maxId = it.id }
        return maxId
    }


    fun deleteMessage(chatId: Int, messageId: Int) {
        chats[chatId].messages.filter { message -> message.id == messageId }
            .let {
                chats[chatId].messages.remove(chats[chatId].messages.first{it.id == messageId})
            }
        if (chats[chatId].messages.isEmpty()) chats.removeAt(chatId)
    }

    fun editMessage(
        chatId: Int,
        messageIds: Int,
        title: String,
        text: String
    ): Int {
        chats.find { it.id == chatId }.let {
            it?.messages?.find { message -> message.id == messageIds }.let { message: Message? ->
                if (message != null) {
                    message.title = title
                    message.text = text
                    return 0
                }

            }
        }
        return 1
    }
}

