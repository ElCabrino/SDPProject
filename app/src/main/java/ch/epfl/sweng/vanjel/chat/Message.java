package ch.epfl.sweng.vanjel.chat;

/**
 * @author Etienne Caquot
 */
public class Message {

    private String time;
    private String message;
    private String sender;

    /**
     *
     * @param time
     * @param message
     * @param sender
     */
    public Message(String time, String message, String sender) {
        this.time = time ;
        this.message = message;
        this.sender = sender;
    }

    /**
     *
     * @return
     */
    public String getTime() {
        return new String(time);
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return new String(message);
    }

    /**
     *
     * @return
     */
    public String getSender() {
        return new String(sender);
    }
}
