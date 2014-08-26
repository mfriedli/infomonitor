package ch.friedli.infosystem.message;

import ch.friedli.infosystem.message.event.BreakingNewsEvent;
import ch.friedli.infosystem.message.event.LatestResultsEvent;
import ch.friedli.infosystem.message.event.LockerRoomEvent;
import ch.friedli.infosystem.message.event.TotomatEvent;
import ch.friedli.infosystem.message.event.annotation.WBBreakingNewsEvent;
import ch.friedli.infosystem.message.event.annotation.WBLatestResultsEvent;
import ch.friedli.infosystem.message.event.annotation.WBLockerRoomEvent;
import ch.friedli.infosystem.message.event.annotation.WBTotomatEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * This bean produces all the different events the front end needs to update its
 * views for.
 *
 * @author Michael Friedli
 */
@Stateless(name = "eventProducer")
public class EventProducer {

    private static final Logger LOGGER = Logger.getLogger(EventProducer.class.getName());

    @Inject
    @WBLockerRoomEvent
    Event<LockerRoomEvent> lockerRoomEvent;

    @Inject
    @WBLatestResultsEvent
    Event<LatestResultsEvent> latestResultEvent;

    @Inject
    @WBTotomatEvent
    Event<TotomatEvent> totomatEvent;

    @Inject
    @WBBreakingNewsEvent
    Event<BreakingNewsEvent> breakingNewsEvent;

    @Schedule(persistent = false, second = "*", minute = "*/30", hour = "*", info = "Locker Room Event publisher")
    public void produceLockerRoomEvent(Timer t) {
        LOGGER.log(Level.FINE, "start to produce a new locker room event");
        LockerRoomEvent event = new LockerRoomEvent();
        this.lockerRoomEvent.fire(event);
    }

    @Schedule(persistent = false, second = "*", minute = "*/15", hour = "*", info = "Latest Result Event publisher")
    public void produceLatestResultEvent(Timer t) {
        LOGGER.log(Level.FINE, "start to produce a new latest result event");
        LatestResultsEvent event = new LatestResultsEvent();
        this.latestResultEvent.fire(event);
    }

    //@Schedule(dayOfWeek = "6,7", second = "*", minute = "*/2", hour = "9-22", info = "Totomat Event publisher")
    @Schedule(persistent = false, second = "*", minute = "*/2", hour = "*", info = "Totomat Event publisher")
    public void produceTotomatEvent(Timer t) {
        LOGGER.log(Level.FINE, "start to produce a new totomat event");
        TotomatEvent event = new TotomatEvent();
        this.totomatEvent.fire(event);
    }

    //@Schedule(second = "*", minute = "*/30", hour = "*", info = "BreakingNews Event publisher")
    @Schedule(persistent = false, second = "*", minute = "*/5", hour = "*", info = "BreakingNews Event publisher")
    public void produceBreakingNewsEvent(Timer t) {
        LOGGER.log(Level.FINE, "start to produce a new breaking news event");
        BreakingNewsEvent event = new BreakingNewsEvent();
        this.breakingNewsEvent.fire(event);
    }
}
