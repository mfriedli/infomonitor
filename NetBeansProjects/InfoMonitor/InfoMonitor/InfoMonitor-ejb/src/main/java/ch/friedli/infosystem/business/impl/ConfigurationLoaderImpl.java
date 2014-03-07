package ch.friedli.infosystem.business.impl;

import ch.friedli.infosystem.entity.League;
import ch.friedli.infosystem.entity.Season;
import ch.friedli.secureremoteinterfaceinfomonitor.ConfigurationLoaderRemote;
import ch.friedli.secureremoteinterfaceinfomonitor.LeagueDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.SeasonDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mfrie_000
 */
@Stateless
public class ConfigurationLoaderImpl implements ConfigurationLoaderRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SeasonDetail> loadAllSeasons() {
        List<SeasonDetail> result = new ArrayList<SeasonDetail>();
        List<Season> seasons = em.createNamedQuery("Season.findAll").getResultList();
        for (Season season : seasons) {
            result.add(convertSeasonDetail(season));
        }
        return result;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteSeasonEntityItem(Integer itemId) {
        Query query = this.em.createNamedQuery("Season.deleteById").setParameter("id", itemId);
        return query.executeUpdate();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSeasonEntityItem(SeasonDetail detail) {
        Season itemToBeUpdated = (Season) this.em.createNamedQuery("Season.findById").setParameter("id", detail.getId()).getSingleResult();
        if (itemToBeUpdated != null) {
            itemToBeUpdated.setSeasonId(detail.getSeasonId());
            itemToBeUpdated.setIsActive(detail.isIsActive() ? (short) 1 : 0);
            itemToBeUpdated.setSeasonName(detail.getSeasonName());
            this.em.persist(itemToBeUpdated);
            this.em.flush();
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SeasonDetail loadSeasonDetailById(Integer itemId) {
        Season item = (Season) this.em.createNamedQuery("Season.findById").setParameter("id", itemId).getSingleResult();
        return convertSeasonDetail(item);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSeasonEntityItem(SeasonDetail detail) {
        Season entity = new Season();
        entity.setSeasonId(detail.getSeasonId());
        entity.setIsActive(detail.isIsActive() ? (short) 1 : 0);
        entity.setSeasonName(detail.getSeasonName());
        this.em.persist(entity);
        this.em.flush();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<LeagueDetail> loadAllLeagues() {
        List<LeagueDetail> result = new ArrayList<LeagueDetail>();
        List<League> leagues = em.createNamedQuery("League.findAll").getResultList();
        for (League league : leagues) {
            result.add(convertLeagueDetail(league));
        }
        return result;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteLeagueEntityItem(Integer itemId) {
        Query query = this.em.createNamedQuery("League.deleteById").setParameter("id", itemId);
        return query.executeUpdate();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateLeagueEntityItem(LeagueDetail detail) {
        League itemToBeUpdated = (League) this.em.createNamedQuery("League.findById").setParameter("id", detail.getId()).getSingleResult();
        if (itemToBeUpdated != null) {
            itemToBeUpdated.setLeagueId(detail.getLeagueId());
            itemToBeUpdated.setLeagueName(detail.getLeagueName());
            itemToBeUpdated.setLeagueShortName(detail.getLeagueShortName());
            this.em.persist(itemToBeUpdated);
            this.em.flush();
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public LeagueDetail loadLeagueDetailById(Integer itemId) {
        League item = (League) this.em.createNamedQuery("League.findById").setParameter("id", itemId).getSingleResult();
        return convertLeagueDetail(item);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createLeagueEntityItem(LeagueDetail detail) {
        League entity = new League();
        entity.setLeagueId(detail.getLeagueId());
        entity.setLeagueName(detail.getLeagueName());
        entity.setLeagueShortName(detail.getLeagueShortName());
        this.em.persist(entity);
        this.em.flush();
    }
    
    private SeasonDetail convertSeasonDetail(Season item) {
        SeasonDetail detail = new SeasonDetail();
        detail.setId(item.getId());
        detail.setSeasonId(item.getSeasonId());
        detail.setSeasonName(item.getSeasonName());
        detail.setIsActive((item.getIsActive() != 0));
        return detail;
    }
    
    private LeagueDetail convertLeagueDetail(League item) {
        LeagueDetail detail = new LeagueDetail();
        detail.setId(item.getId());
        detail.setLeagueId(item.getLeagueId());
        detail.setLeagueName(item.getLeagueName());
        detail.setLeagueShortName(item.getLeagueShortName());
        return detail;
    }
}
