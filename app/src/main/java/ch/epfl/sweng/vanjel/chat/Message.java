package ch.epfl.sweng.vanjel.chat;

/**
 * Class that represents a Messag
 *
 * @author Etienne Caquot
 * @reviewer Vincent Cabrini
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
        return time;
    }

    /**
     *
     * @return message text
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return senderUid
     */
    public String getSenderUid() {
        return senderUid;
    }
}
