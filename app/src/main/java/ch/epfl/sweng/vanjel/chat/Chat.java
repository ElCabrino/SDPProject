package ch.epfl.sweng.vanjel.chat;

/**
 * Class that represents a Chat
 *
 * @author Etienne Caquot
 */
public class Chat {

    private String time;
    private String lastMessage;
    private String contactName;
    private String contactUid;

    /**
     *
     * @param time a String to represent time of last message
     * @param lastMessage a String that is the last message in the chat
     * @param contactName a String that represent the contact Name
     * @param contactUid a String representing the contact UID
     */
    public Chat(String time, String lastMessage, String contactName, String contactUid) {
        this.time = time ;
        this.lastMessage = lastMessage;
        this.contactName = contactName;
        this.contactUid = contactUid;
    }

    /**
     *
     * @return time of last message
     */
    public String getTime() {
        return new String(time);
    }

    /**
     *
     * @return last message
     */
    public String getLastMessage() {
        return new String(lastMessage);
    }

    /**
     *
     * @return contact Name
     */
    public String getContactName() {
        return new String(contactName);
    }

    /**
     *
     * @return contact UID
     */
    public String getContactUid() {
        return new String(contactUid);
    }
}