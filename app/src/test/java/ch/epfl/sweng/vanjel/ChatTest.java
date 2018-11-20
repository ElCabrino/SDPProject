package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.chat.Chat;

import static org.junit.Assert.assertEquals;

public class ChatTest {

    private String time = "11.30";
    private String lastMessage = "This is a message";
    private String contactName = "Jon Bob";
    private String contactUid = "e6dbajud8ebd98azdd8";
    private int lastMessageLength = lastMessage.length();

    @Test
    public void getterTestConstructor1() {
        Chat chat = new Chat(time,lastMessage,contactName,contactUid,lastMessageLength);
        assertEquals(chat.getTime(),time);
        assertEquals(chat.getLastMessage(),lastMessage);
        assertEquals(chat.getContactName(),contactName);
        assertEquals(chat.getContactUid(),contactUid);
        assertEquals(chat.getMessageLength(),lastMessageLength);
    }
}
