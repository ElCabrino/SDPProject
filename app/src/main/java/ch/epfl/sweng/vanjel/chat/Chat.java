
package ch.epfl.sweng.vanjel.chat;

/*
 * Due to unreasonable code climate duplicate issue with Appointment class
 * This class is ignored by code climate.
 */

/**
 * @author Etienne Caquot
 * @reviewer Luca Joss
 */
public class Chat {

    private final String time;
    private final String lastMessage;
    private final String contactName;
    private final String contactUid;

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

    public String getTime() {
        return time;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactUid() {
        return contactUid;
    }
}