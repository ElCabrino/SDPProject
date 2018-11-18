package ch.epfl.sweng.vanjel.chat;

/**
 * Class that represents a Message
 *
 * @author Etienne Caquot
 */
public class Message {

    private String time;
    private String message;
    private String senderUid;

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

    /**
     *
     * @return time of message
     */
    public String getTime() {
        return new String(time);
    }

    /**
     *
     * @return message text
     */
    public String getMessage() {
        return new String(message);
    }

    /**
     *
     * @return senderUid
     */
    public String getSenderUid() {
        return new String(senderUid);
    }
}
