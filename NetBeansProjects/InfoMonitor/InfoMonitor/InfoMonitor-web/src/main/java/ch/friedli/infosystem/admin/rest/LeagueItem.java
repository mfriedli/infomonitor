package ch.friedli.infosystem.admin.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mfrie_000
 */
@XmlRootElement(name="LeagueItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class LeagueItem {
    @XmlElement
    private Long id;
    @XmlElement
    private Integer leagueId;
    @XmlElement
    private String leagueName;
    @XmlElement
    private String leagueShortName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getLeagueShortName() {
        return leagueShortName;
    }

    public void setLeagueShortName(String leagueShortName) {
        this.leagueShortName = leagueShortName;
    }

    @Override
    public String toString() {
        return "LeagueItem{" + "id=" + id + ", leagueId=" + leagueId + ", leagueName=" + leagueName + ", leagueShortName=" + leagueShortName + '}';
    }
    
    
    
}