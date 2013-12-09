package ch.friedli.infosystem.message.event;

import java.util.Date;

/**
 * This is just a simple POJO to be transfered to the client upon a Totomat
 * event.
 * 
 * @author Michael Friedli
 */
public class TotomatEvent {
    
    private Date timestamp;

    public TotomatEvent() {
        timestamp = new Date();
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }
       
}
