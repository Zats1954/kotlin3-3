package ru.netology

class Service(val chats: ChatService ) {
    var users: MutableList<User> = mutableListOf()

    fun readMessage(userId: Int, chatId: Int, messageId: Int) {
       if(users[userId].readedMessages.find { it.first == chatId && it.second == messageId } == null)
             users[userId].readedMessages.add(Pair(chatId, messageId))
    }


    fun getUnreadChatsCount(user: User): Int {
        var countUnreadChat = 0
        chats.chats.forEach { chat ->
            if (chat.ownerId == user.id || chat.guestId == user.id) { // чат отслеживает user
                    for(message in chat.messages){
                       if (!user.readedMessages.contains(Pair(chat.id,message.id)) && message.ownerId!=user.id ){
                           countUnreadChat++
                           break}
                    }
            }
        }
        return countUnreadChat
    }

    fun getChats(user:User):List<String>{
        val list: MutableList<String> = mutableListOf()
    // чаты пользователя
        val userChat = chats.chats.filter{user.id in (listOf(it.guestId, it.ownerId))}
    //чаты с непустыми чужими сообщениями
        userChat.filter{it.messages.filter{ message:Message -> message.ownerId!=user.id}.isNotEmpty()}

        .forEach {chat:Chat ->if(chat.messages.size == 0) {list.add(chat.title + " нет сообщений")}
                             else {list.add(chat.title)} }
        return list
    }

    fun chatMessages(user:User, chatId: Int): String {
        var info = "у ${user.name} в chat $chatId"                   // id чата
        chats.chats[chatId].messages.filter{!user.readedMessages.contains(Pair(chatId, it.id))}
            .sortedBy { it.id }
            .let {
                if (it.isNotEmpty()) {
                    // id последнего сообщения, начиная с которого нужно подгрузить новые
                    info += "\nпервое непрочитанное сообщение " + it.first().id
                    it.forEach { message: Message -> readMessage(user.id, chatId, message.id) } //автоматически считаются прочитанными
                } else {info += "\nнепрочитанных сообщений нет"}
             }
        info += "\nвсего сообщений " + chats.chats[chatId].messages.size           //  количество сообщений
        return info}
    }