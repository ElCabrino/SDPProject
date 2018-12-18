package ch.epfl.sweng.vanjel.chat;

/**
 * Class that represents a Message
 *
 * @author Etienne Caquot
 * @reviewer Vincent Cabrini
 */
public class Message {

    private final String time;
    private final String message;
    private final String senderUid;

    /**
     *
     * @param time a String to represent time of message
     * @param message a String to represent message text
     * @param senderUid a String to represent sender UID
     */
    public Message(String time, String message, String senderUid) {
        this.time = time ;
        this.message = message;
        this.senderUid = senderUid;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderUid() {
        return senderUid;
    }
}
