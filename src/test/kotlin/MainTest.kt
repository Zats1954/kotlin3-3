import org.junit.Assert.assertEquals
import org.junit.Test
import ru.netology.*

class MainTest {
    val chats = ChatService()
    val users = Service(chats)

    fun init(){
        users.users.add(User(0, "Миша"))
        users.users.add(User(1, "Вася"))
        users.users.add(User(2, "Игорь"))
        users.users.add(User(3, "Сергей"))

        chats.createChat(users.users[0].id, users.users[1].id,"cinema")
        chats.createChat(users.users[1].id, users.users[3].id,"sport")
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

    }


    @Test
    fun chats_Create(){
        init()
        assertEquals( 3, chats.chats.size)
    }

    @Test
    fun write_Messages(){
        init()
        assertEquals(3, chats.chats[1].messages.size)
    }

    @Test
    fun delete_Chat(){
        init()
        chats.deleteChat(1)
        assertEquals( 2, chats.chats.size)
        assertEquals("2 to 3",chats.chats[1].title )
    }

    @Test
    fun user_unread_Chats(){
        init()
        users.readMessage(users.users[1].id, chats.chats[0].id, chats.chats[0].messages[1].id)
        assertEquals( 2, users.getUnreadChatsCount(users.users[1]))
        assertEquals(2,
                   chats.chats[0].messages.filter{!users.users[1].readedMessages.contains(Pair(0, it.id))}.size)
        users.chatMessages(users.users[1], 0)
        assertEquals(0,
            chats.chats[0].messages.filter{!users.users[1].readedMessages.contains(Pair(0, it.id))}.size)
    }

    @Test
    fun delete_Message(){
        init()
        assertEquals(3,
            chats.chats.size)
        chats.deleteMessage(0,1)
        assertEquals(2,
            chats.chats[0].messages.size)
        chats.deleteMessage(0,2)
        chats.deleteMessage(0,3)
        assertEquals(2,
            chats.chats.size)
//        assertEquals( 2, chats.chats.size)
//        assertEquals("2 to 3",chats.chats[1].title )
    }


}