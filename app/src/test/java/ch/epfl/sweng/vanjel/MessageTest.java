package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.chat.Message;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    private String time = "11.30";
    private String Message = "This is a message";
    private String senderUid = "oubsdf87gr3wuibf";

    @Test
    public void getterTestConstructor1() {
        Message message = new Message(time,Message,senderUid);
        assertEquals(message.getTime(),time);
        assertEquals(message.getMessage(),Message);
        assertEquals(message.getSenderUid(),senderUid);
    }
}
