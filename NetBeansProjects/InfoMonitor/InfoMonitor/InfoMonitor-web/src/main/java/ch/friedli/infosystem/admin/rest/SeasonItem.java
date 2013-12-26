package ch.friedli.infosystem.admin.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mfrie_000
 */
@XmlRootElement(name="SeasonItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeasonItem {
    @XmlElement
    private Long id;
    @XmlElement
    private Integer seasonId;
    @XmlElement
    private String seasonName;
    @XmlElement
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "SeasonItem{" + "id=" + id + ", seasonId=" + seasonId + ", seasonName=" + seasonName + ", isActive=" + isActive + '}';
    }
    
    
}