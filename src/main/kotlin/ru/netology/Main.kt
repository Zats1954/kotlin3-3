package ru.netology

    fun main() {
        val chats = ChatService()
        val users = Service(chats)
        users.users.add(User(0, "Миша"))
        users.users.add(User(1, "Вася"))
        users.users.add(User(2, "Игорь"))
        users.users.add(User(3, "Сергей"))

        chats.createChat(users.users[0].id, users.users[1].id,"cinema")
        chats.createChat(users.users[1].id, users.users[3].id,"sport")

        chats.chats.forEach {println(it.id.toString() + " " + it.title + " создал " +
                it.let{owner ->  users.users[owner.ownerId].name})}
        println()

        chats.writeMessage(users.users[0].id, users.users[1].id, "заголовок ","newMessageA")
        chats.writeMessage(users.users[1].id, users.users[0].id, "ответ ","сообщениеA1")
        chats.writeMessage(users.users[0].id, users.users[1].id, "ответ ","сообщениеA2")

        chats.writeMessage(users.users[1].id, users.users[3].id, "заголовок ","newMessageB")
        chats.writeMessage(users.users[3].id, users.users[1].id, "ответ ","сообщениеB1")
        chats.writeMessage(users.users[1].id, users.users[3].id, "ответ ","сообщениеB2")
        chats.writeMessage(users.users[3].id, users.users[3].id, "ответ ","сообщениеB3")



        chats.writeMessage(users.users[2].id, users.users[3].id, "заголовок ","newMessageC")
        chats.writeMessage(users.users[3].id, users.users[2].id, "ответ ","сообщениеC1")
        chats.writeMessage(users.users[2].id, users.users[3].id, "ответ ","сообщениеC2")


        println("Чаты с чужими сообщениями где участвует " + users.users[1].name )
        users.getChats(users.users[1]).forEach { println(it)}

        println("Записи в чатах" )
        chats.allChats().forEach { chat: Chat ->
            println("\n---------" + chat.title + "-----------")
            chat.messages.forEach {
                println("\nот " + users.users[it.ownerId].name)
                println(it.title)
                println(it.text)
            }
        }

        // Миша прочел чат "cinema" запись от Васи
        users.readMessage(users.users[1].id, chats.chats[0].id, chats.chats[0].messages[1].id)
        println("у " + users.users[1].name + " непрочитано чатов " + users.getUnreadChatsCount(users.users[1]))
        println("у " + users.users[3].name + " непрочитано чатов " + users.getUnreadChatsCount(users.users[3]))

        chats.deleteChat(1)
        println("=============удаляем sport=========================")
        chats.allChats().forEach { chat: Chat ->
            println("---------" + chat.title + "-----------")
        }

        println("сообщения из чата ${chats.chats[1].title}")

        chats.chats[0].messages.forEach{println(it.id.toString() + "\n" +  it.title + "\n" + it.text + "\n  ---------")}
        println(users.chatMessages(users.users[1], 0))
        println("===========")
        println(users.chatMessages(users.users[1], 0))

        chats.deleteMessage(0,1)
        chats.editMessage(0,3, "посылка", "это тебе от ${users.users[3].name}")
        chats.chats[0].messages.forEach{println(it.id.toString() + "\n" +  it.title + "\n" + it.text + "\n  ---------")}
    }

