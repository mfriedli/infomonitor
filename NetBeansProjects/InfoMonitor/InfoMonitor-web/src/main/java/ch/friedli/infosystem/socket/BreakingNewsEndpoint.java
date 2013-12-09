package ch.friedli.infosystem.socket;

import ch.friedli.infosystem.message.event.BreakingNewsEvent;
import ch.friedli.infosystem.message.event.annotation.WBBreakingNewsEvent;
import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsLoaderRemote;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author mfrie_000
 */
@Singleton
@ServerEndpoint(value="/breakingnewsendpoint", encoders={BreakingNewsDetailsEncoder.class})
public class BreakingNewsEndpoint {

    private static final Logger LOGGER = Logger.getLogger(BreakingNewsEndpoint.class.getName());
    private final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

   @EJB
   BreakingNewsLoaderRemote  breakingNewsLoader;
   
    /**
     * Returns the same message as received from the client.
     * Currently this method is not used since the front end is not sending 
     * any messages to the server (no user interaction).
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
    }

    
    /**
     * Observes any WBTotomatEvent and sends a corresponding response to 
     * all connected clients.
     * 
     * @param event The WBTotomatEvent this method is observing
     */
    public void onLockerRoomEvent(@Observes @WBBreakingNewsEvent BreakingNewsEvent event) {
        LOGGER.log(Level.FINE, "BreakingNews Event observed {0}" + new Object[]{event.getTimestamp()});
        for (Session peer : this.peers) {
            try {
                BreakingNewsDetail detail = this.breakingNewsLoader.loadBreakingNews();
                if (detail != null) {
                     peer.getBasicRemote().sendObject(detail);
                }
            } catch (IOException | EncodeException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
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
