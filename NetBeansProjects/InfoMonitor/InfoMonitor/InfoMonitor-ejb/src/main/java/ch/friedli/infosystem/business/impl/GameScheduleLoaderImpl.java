package ch.friedli.infosystem.business.impl;

import ch.friedli.infosystem.business.exception.BusinessTechnicalException;
import ch.friedli.infosystem.entity.League;
import ch.friedli.infosystem.entity.Season;
import ch.friedli.infosystem.ihs.jaxb.Ihs;
import ch.friedli.infosystem.ihs.jaxb.Ihs.Spiel;
import ch.friedli.secureremoteinterfaceinfomonitor.GameScheduleLoaderRemote;
import ch.friedli.secureremoteinterfaceinfomonitor.LatestResultDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.LockerRoomDetail;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * The loader service to fetch different aspects of the games.
 *
 * @author Michael Friedli
 */
@Stateless
public class GameScheduleLoaderImpl implements GameScheduleLoaderRemote {

    private final static String SERVICE_URL_BASE = "http://service.ih-s.ch/xml/spielplan.aspx?typ=liga&list=all&id=";

    @PersistenceContext
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<LatestResultDetail> loadLatestResults() {
        List<LatestResultDetail> latestResultList = new LinkedList<>();
        Map<String, List<Ihs.Spiel>> allLeagueGamesMap = this.loadEntireGameSchedule();
        for (Entry<String, List<Ihs.Spiel>> entry : allLeagueGamesMap.entrySet()) {
            for (Ihs.Spiel game : entry.getValue()) {
                // only consider games that have been finished
                if (game.getGespielt() == 1) {
                    LatestResultDetail rd = new LatestResultDetail();
                    // date from xml service <![CDATA[ 05.10.2013 17:00:00 ]]>
                    String[] dateParts = game.getDatum().split(" ");
                    String[] timeParts = dateParts[1].split(":");
                    StringBuilder time = new StringBuilder();
                    time.append(timeParts[0]).append(":").append(timeParts[1]);
                    rd.setDateString(dateParts[0]);
                    rd.setTimeString(time.toString());
                    rd.setHomeTeam(game.getHeim());
                    rd.setGuestTeam(game.getGast());
                    rd.setResult(game.getResultat());
                    rd.setResultFirstHalf(game.getResultat1());
                    rd.setResultSecondHalf(game.getResultat2());
                    if (game.getNachPenalty() == 1) {
                        rd.setScoreSuffix("n.P.");
                    } else if (game.getNachVerlaengerung() == 1) {
                        rd.setScoreSuffix("n.V.");
                    }
                    rd.setLeague(entry.getKey());

                    latestResultList.add(rd);
                }
            }
        }
        Collections.sort(latestResultList, new LatestResultDateComparator());
        if (latestResultList.size() > 16) {
            return latestResultList.subList(0, 16);
        } else {
            return latestResultList; // no truncation necessary
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<LockerRoomDetail> loadGameSchedule() {
        List<LockerRoomDetail> details = new LinkedList<>();
        Map<String, List<Spiel>> allLeagueGamesMap = this.loadEntireGameSchedule();
        allLeagueGamesMap = filterNextRelevantGamesInKaltbrunn(allLeagueGamesMap);
        for (Entry<String, List<Ihs.Spiel>> entry : allLeagueGamesMap.entrySet()) {
            // first convert into domain dto
            for (Ihs.Spiel game : entry.getValue()) {

                // date from xml service <![CDATA[ 05.10.2013 17:00:00 ]]>
                String[] dateParts = game.getDatum().split(" ");
                String[] timeParts = dateParts[1].split(":");
                StringBuilder time = new StringBuilder();
                time.append(timeParts[0]).append(":").append(timeParts[1]);
                // home team locker room
                LockerRoomDetail detailHome = new LockerRoomDetail();
                detailHome.setDateString(dateParts[0]);
                detailHome.setTimeString(time.toString());
                detailHome.setTeamName(game.getHeim());
                detailHome.setLeague(entry.getKey());
                details.add(detailHome);

                // guest team locker room
                LockerRoomDetail detailGuest = new LockerRoomDetail();
                detailGuest.setDateString(dateParts[0]);
                detailGuest.setTimeString(time.toString());
                detailGuest.setTeamName(game.getGast());
                detailGuest.setLeague(entry.getKey());
                details.add(detailGuest);
            }
        }
        // Now assign the locker rooms to the first six LockerRoomDetail entries
        // sort the locker room details by game time and only consider the first 
        // 6 locker room details to assign a locker room number
        Collections.sort(details, new LockerRoomDateComparator());
        List<LockerRoomDetail> lockerRoomsList = new ArrayList<>();

        int index = 0;
        int[] lockerRoomNbrs = new int[4];
        lockerRoomNbrs[0] = 3;
        lockerRoomNbrs[1] = 1;
        lockerRoomNbrs[2] = 4;
        lockerRoomNbrs[3] = 2;
        // distribute locker rooms 1 / 2 / 3 / 4
        // note: a game is played by two teams one will be assigned 
        // locker room 1 the other team locker room 2
        // for the next game the home team gets locker room 2 assigned and
        // the guest team locker room 4
        // then we start over with 1 and 3
        for (LockerRoomDetail detail : details) {
            if (lockerRoomsList.size() == 6) {
                break; // need only 6 locker room details in result
            }
            if (index > (lockerRoomNbrs.length - 1)) {
                index = 0; //start over with locker room 3 + 1
            }
            detail.setRoomNumber(lockerRoomNbrs[index]);
            lockerRoomsList.add(detail);
            index++;
        }
        return lockerRoomsList;
    }

    private Map<String, List<Ihs.Spiel>> loadEntireGameSchedule() {
        Map<String, List<Ihs.Spiel>> leagueGameMap = new HashMap<>();
        InputStream inStream = null;
        try {
            List<League> leagues = em.createNamedQuery("League.findAll").getResultList();
            for (League league : leagues) {
                inStream = new URL(SERVICE_URL_BASE + league.getLeagueId()).openStream();
                // JAXB unmarschalling
                JAXBContext jc = JAXBContext.newInstance("ch.friedli.infosystem.ihs.jaxb");
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                Ihs ihsSchedule = (Ihs) unmarshaller.unmarshal(inStream);
                List<Ihs.Spiel> gamesPerLeague = leagueGameMap.get(league.getLeagueShortName());
                if (gamesPerLeague == null) {
                    gamesPerLeague = new LinkedList<>();
                    leagueGameMap.put(league.getLeagueShortName(), gamesPerLeague);
                }
                gamesPerLeague.addAll(ihsSchedule.getSpiel());
            }
            return leagueGameMap;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GameScheduleLoaderImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessTechnicalException(ex, "Unable to load game schedule");
        } catch (IOException | JAXBException ex) {
            Logger.getLogger(GameScheduleLoaderImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessTechnicalException(ex, "Unable to load game schedule");
        } finally {
            if (inStream != null) {
                try {
                    inStream.close(); // call this in a finally block
                } catch (IOException ex) {
                    Logger.getLogger(GameScheduleLoaderImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private Map<String, List<Ihs.Spiel>> filterNextRelevantGamesInKaltbrunn(Map<String, List<Ihs.Spiel>> allLeagueGamesMap) {
        Map<String, List<Ihs.Spiel>> nextGames = new HashMap<>();
        for (Entry<String, List<Ihs.Spiel>> entry : allLeagueGamesMap.entrySet()) {
            List<Ihs.Spiel> gamesList = new LinkedList<>();
            nextGames.put(entry.getKey(), gamesList);
            // get current time/ date minus 2.5 h
            DateTime timeRange = new DateTime();
            timeRange = timeRange.minusHours(2);
            timeRange = timeRange.minusMinutes(30);
            for (Ihs.Spiel game : entry.getValue()) {
                // game not yet played and is held in Kaltbrunn
                if (game.getAbgesagt() == 0 && game.getGespielt() == 0
                        && game.getSpielort().contains("Kaltbrunn")) {

                    // get the date of the current game
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
                    DateTime gameDateTime = formatter.parseDateTime(game.getDatum());
                    // only consider games that are in the future or within 2.5 hours
                    // of now 
                    if (gameDateTime.isAfter(timeRange)) {
                        gamesList.add(game);
                    }
                }
            }
        }
        return nextGames;
    }

    class LockerRoomDateComparator implements Comparator<LockerRoomDetail> {

        @Override
        public int compare(LockerRoomDetail o1, LockerRoomDetail o2) {
            // get the date of the current game
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
            StringBuilder dateTimeBuilder = new StringBuilder();
            dateTimeBuilder.append(o1.getDateString());
            dateTimeBuilder.append(" ");
            dateTimeBuilder.append(o1.getTimeString());
            DateTime date1 = formatter.parseDateTime(dateTimeBuilder.toString());

            dateTimeBuilder.setLength(0);
            dateTimeBuilder.append(o2.getDateString());
            dateTimeBuilder.append(" ");
            dateTimeBuilder.append(o2.getTimeString());
            DateTime date2 = formatter.parseDateTime(dateTimeBuilder.toString());

            return date1.compareTo(date2);
        }
    }

    class LatestResultDateComparator implements Comparator<LatestResultDetail> {

        @Override
        public int compare(LatestResultDetail o1, LatestResultDetail o2) {
            // get the date of the current game
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
            StringBuilder dateTimeBuilder = new StringBuilder();
            dateTimeBuilder.append(o1.getDateString());
            dateTimeBuilder.append(" ");
            dateTimeBuilder.append(o1.getTimeString());
            DateTime date1 = formatter.parseDateTime(dateTimeBuilder.toString());

            dateTimeBuilder.setLength(0);
            dateTimeBuilder.append(o2.getDateString());
            dateTimeBuilder.append(" ");
            dateTimeBuilder.append(o2.getTimeString());
            DateTime date2 = formatter.parseDateTime(dateTimeBuilder.toString());

            return date2.compareTo(date1);
        }
    }

    private Season findActiveSeason() {
        List<Season> seasons = em.createNamedQuery("Season.findAll").getResultList();
        for (Season season : seasons) {
            if (season.getIsActive() == 1) {
                return season;
            }
        }
        throw new BusinessTechnicalException("no active season in db found");
    }
}
