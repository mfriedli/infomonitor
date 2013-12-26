package ch.friedli.infosystem.message.event;

import java.util.Date;

/**
 * This is just a simple POJO to be transfered to the client upon a Breaking news
 * event.
 * 
 * @author Michael Friedli
 */
public class BreakingNewsEvent {
    
    private Date timestamp;

    public BreakingNewsEvent() {
        timestamp = new Date();
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }
       
}
