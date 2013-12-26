package ch.friedli.infosystem.business.impl;

import ch.friedli.infosystem.business.exception.BusinessTechnicalException;
import ch.friedli.infosystem.entity.Season;
import ch.friedli.infosystem.ihs.totomat.jaxb.*;
import ch.friedli.secureremoteinterfaceinfomonitor.TotomatDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.TotomatLoaderRemote;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author mfrie_000
 */
@Stateless
public class TotomatLoaderImpl implements TotomatLoaderRemote {

    private final static String SERVICE_URL_BASE = "http://service.ih-s.ch/xml/totomat.aspx?typ=saison&id=";

    @PersistenceContext
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<TotomatDetail> loadTotomatDetails() {
        List<TotomatDetail> result = new LinkedList<>();
        List<Ihs.Spiel> games = loadTotomat();
        for (Ihs.Spiel game : games) {
            if (game == null) {
                return result; // no games
            }
            TotomatDetail detail = new TotomatDetail();

            // date from xml service <![CDATA[ 05.10.2013 17:00:00 ]]>
            String[] dateParts = game.getDatum().split(" ");
            String[] timeParts = dateParts[1].split(":");
            StringBuilder time = new StringBuilder();
            time.append(timeParts[0]).append(":").append(timeParts[1]);
            detail.setDateString(dateParts[0]);
            detail.setTimeString(time.toString());
            detail.setGuestTeam(game.getGastkuerzel());
            detail.setHomeTeam(game.getHeimkuerzel());
            detail.setLeague(game.getLiganame());
            detail.setResult(game.getResultat());
            detail.setResultFirstHalf(game.getResultath1());
            detail.setResultSecondHalf(game.getResultath2());
            if (game.getIsrunning() == 1) {
                detail.setIsRunning(true);
            } else {
                detail.setIsRunning(false);
            }
            if (game.getNachPenalty() == 1) {
                detail.setScoreSuffix("n.P.");
            } else if (game.getNachVerlaengerung() == 1) {
                detail.setScoreSuffix("n.V.");
            }
            result.add(detail);
        }
        return result;
    }

    private List<Ihs.Spiel> loadTotomat() {
        List<Ihs.Spiel> totomatGames = new LinkedList<>();
        InputStream inStream = null;
        try {
            Season season = (Season)em.createNamedQuery("Season.findByIsActive").setParameter("isActive", 1).getSingleResult();

            inStream = new URL(SERVICE_URL_BASE + season.getSeasonId()).openStream();
            // JAXB unmarschalling
            JAXBContext jc = JAXBContext.newInstance("ch.friedli.infosystem.ihs.totomat.jaxb");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Ihs ihsTotomat = (Ihs) unmarshaller.unmarshal(inStream);
            totomatGames.addAll(ihsTotomat.getSpiel());

            return totomatGames;

        } catch (MalformedURLException ex) {
            Logger.getLogger(TotomatLoaderImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessTechnicalException(ex, "Unable to load game schedule");
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(TotomatLoaderImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessTechnicalException(ex, "Unable to load game schedule");
        } finally {
            if (inStream != null) {
                try {
                    inStream.close(); // call this in a finally block
                } catch (IOException ex) {
                    Logger.getLogger(TotomatLoaderImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
