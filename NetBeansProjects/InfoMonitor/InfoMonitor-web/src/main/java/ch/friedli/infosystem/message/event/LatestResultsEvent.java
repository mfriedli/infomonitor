package ch.friedli.infosystem.message.event;

import java.util.Date;

/**
 * This is just a simple POJO to be transfered to the client upon a LockerRoom
 * event.
 * 
 * @author Michael Friedli
 */
public class LatestResultsEvent {
    
    private Date timestamp;

    public LatestResultsEvent() {
        timestamp = new Date();
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }
       
}
