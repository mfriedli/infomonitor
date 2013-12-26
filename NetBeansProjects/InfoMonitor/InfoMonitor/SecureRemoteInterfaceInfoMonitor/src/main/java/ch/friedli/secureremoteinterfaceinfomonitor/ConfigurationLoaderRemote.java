package ch.friedli.secureremoteinterfaceinfomonitor;

import java.util.List;
import javax.ejb.Remote;

/**
 * The remote EJB interfaces to be used by the front-end.
 *
 * @author Michael Friedli
 */
@Remote
public interface ConfigurationLoaderRemote {

    public List<SeasonDetail> loadAllSeasons();
    public int deleteSeasonEntityItem(Integer itemId);
    public void updateSeasonEntityItem(SeasonDetail detail);
    public SeasonDetail loadSeasonDetailById(Integer itemId);
    public void createSeasonEntityItem(SeasonDetail detail);
    
    public List<LeagueDetail> loadAllLeagues();
    public int deleteLeagueEntityItem(Integer itemId);
    public void updateLeagueEntityItem(LeagueDetail detail);
    public LeagueDetail loadLeagueDetailById(Integer itemId);
    public void createLeagueEntityItem(LeagueDetail detail);
}
