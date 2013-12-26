/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.friedli.infosystem.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mfrie_000
 */
@Entity
@Table(name = "SEASON")
@NamedQueries({
    @NamedQuery(name = "Season.deleteById", query ="DELETE FROM Season s WHERE s.id = :id"),
    @NamedQuery(name = "Season.findAll", query = "SELECT s FROM Season s"),
    @NamedQuery(name = "Season.findById", query = "SELECT s FROM Season s WHERE s.id = :id"),
    @NamedQuery(name = "Season.findBySeasonId", query = "SELECT s FROM Season s WHERE s.seasonId = :seasonId"),
    @NamedQuery(name = "Season.findBySeasonName", query = "SELECT s FROM Season s WHERE s.seasonName = :seasonName"),
    @NamedQuery(name = "Season.findByIsActive", query = "SELECT s FROM Season s WHERE s.isActive = :isActive")})
public class Season implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEASON_ID")
    private int seasonId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "SEASON_NAME")
    private String seasonName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ACTIVE")
    private short isActive;

    public Season() {
    }

    public Season(Long id) {
        this.id = id;
    }

    public Season(Long id, int seasonId, String seasonName, short isActive) {
        this.id = id;
        this.seasonId = seasonId;
        this.seasonName = seasonName;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public short getIsActive() {
        return isActive;
    }

    public void setIsActive(short isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Season)) {
            return false;
        }
        Season other = (Season) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.friedli.infosystem.entity.Season[ id=" + id + " ]";
    }
    
}
