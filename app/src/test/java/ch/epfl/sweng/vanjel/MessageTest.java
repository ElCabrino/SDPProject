package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.chat.Message;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    @Test
    public void getterTestConstructor1() {
        String senderUid = "oubsdf87gr3wuibf";
        String message1 = "This is a message";
        String time = "11.30";
        Message message = new Message(time, message1, senderUid);
        assertEquals(message.getTime(), time);
        assertEquals(message.getMessage(), message1);
        assertEquals(message.getSenderUid(), senderUid);
    }
}
