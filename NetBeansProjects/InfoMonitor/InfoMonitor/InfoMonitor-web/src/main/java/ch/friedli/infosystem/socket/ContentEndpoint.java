package ch.friedli.infosystem.socket;

import ch.friedli.secureremoteinterfaceinfomonitor.ContentDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.ContentLoaderRemote;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * This is the web socket endpoint for the Raiffeisen Arena info system.
 *
 * @author Michael Friedli
 */
@Singleton
@ServerEndpoint(value = "/contentendpoint", encoders={ContentDetailsEncoder.class})
public class ContentEndpoint {

    private static final Logger LOGGER = Logger.getLogger(ContentEndpoint.class.getName());
    private final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private int currentItemShownIndex = 0;

    @EJB
    ContentLoaderRemote contentLoader;
    
    @Resource
    TimerService timerService;

    /**
     * Returns the same message as received from the client. Currently this
     * method is not used since the front end is not sending any messages to the
     * server (no user interaction).
     *
     * @param message The message received from the front end
     * @return the same message as received
     */
    @OnMessage
    public String sendEchoReply(String message) {
        LOGGER.log(Level.FINE, "new message from client: {0}", new Object[]{message});
        return message;
    }

    @OnOpen
    public void onOpen(Session peer) {
        LOGGER.log(Level.FINE, "added new session to peers {0}" + new Object[]{peer});
        this.peers.add(peer);
        setTimer(10000);
    }

    public void setTimer(long intervalDuration) {
        LOGGER.log(Level.FINE, "Setting a programmatic timeout for " +
                intervalDuration + " milliseconds from now.");
        Timer timer = this.timerService.createTimer(intervalDuration, 
                "Created new programmatic timer");
    }
    @Timeout
    public void switchContent(Timer timer) {
        LOGGER.log(Level.FINE, "switch content timer received");
        for (Session peer : this.peers) {
            try {
                List<ContentDetail> details = this.contentLoader.loadActiveContentDetails();
                ContentDetail detail = details.get(this.currentItemShownIndex);
                peer.getBasicRemote().sendObject(detail);
                this.currentItemShownIndex++;
                if (this.currentItemShownIndex == details.size()) {
                    this.currentItemShownIndex = 0; // back to first item again
                }
                setTimer(detail.getShowInterval());
            } catch (IOException | EncodeException ex) {
                Logger.getLogger(ContentEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnClose
    public void onClose(Session peer) {
        this.peers.remove(peer);
    }

    @OnError
    public void onError(Throwable t) {
        LOGGER.log(Level.SEVERE, "Websocket exception :{}", t.toString());
    }
}
