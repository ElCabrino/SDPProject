package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.chat.Chat;

import static org.junit.Assert.assertEquals;

public class ChatTest {

    @Test
    public void getterTestConstructor1() {
        String contactUid = "e6dbajud8ebd98azdd8";
        String contactName = "Jon Bob";
        String lastMessage = "This is a message";
        String time = "11.30";
        Chat chat = new Chat(time, lastMessage, contactName, contactUid);
        assertEquals(chat.getTime(), time);
        assertEquals(chat.getLastMessage(), lastMessage);
        assertEquals(chat.getContactName(), contactName);
        assertEquals(chat.getContactUid(), contactUid);
    }
}
