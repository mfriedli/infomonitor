/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.friedli.infosystem.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mfrie_000
 */
@Entity
@Table(name = "GAME")
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
    @NamedQuery(name = "Game.findByIhsGameNbr", query = "SELECT g FROM Game g WHERE g.ihsGameNbr = :ihsGameNbr"),
    @NamedQuery(name = "Game.findByHome", query = "SELECT g FROM Game g WHERE g.home = :home"),
    @NamedQuery(name = "Game.findByGuest", query = "SELECT g FROM Game g WHERE g.guest = :guest"),
    @NamedQuery(name = "Game.findByScoreHome", query = "SELECT g FROM Game g WHERE g.scoreHome = :scoreHome"),
    @NamedQuery(name = "Game.findByScoreGuest", query = "SELECT g FROM Game g WHERE g.scoreGuest = :scoreGuest"),
    @NamedQuery(name = "Game.findByDate", query = "SELECT g FROM Game g WHERE g.date = :date"),
    @NamedQuery(name = "Game.findByTime", query = "SELECT g FROM Game g WHERE g.time = :time"),
    @NamedQuery(name = "Game.findByLocation", query = "SELECT g FROM Game g WHERE g.location = :location"),
    @NamedQuery(name = "Game.findByIsOvertime", query = "SELECT g FROM Game g WHERE g.isOvertime = :isOvertime"),
    @NamedQuery(name = "Game.findByIsPenaltyShootOut", query = "SELECT g FROM Game g WHERE g.isPenaltyShootOut = :isPenaltyShootOut"),
    @NamedQuery(name = "Game.findByIsCompleted", query = "SELECT g FROM Game g WHERE g.isCompleted = :isCompleted")})
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IHS_GAME_NBR")
    private int ihsGameNbr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "HOME")
    private String home;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "GUEST")
    private String guest;
    @Column(name = "SCORE_HOME")
    private Short scoreHome;
    @Column(name = "SCORE_GUEST")
    private Short scoreGuest;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME")
    @Temporal(TemporalType.TIME)
    private Date time;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "LOCATION")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_OVERTIME")
    private Serializable isOvertime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_PENALTY_SHOOT_OUT")
    private Serializable isPenaltyShootOut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_COMPLETED")
    private Serializable isCompleted;

    public Game() {
    }

    public Game(Integer id) {
        this.id = id;
    }

    public Game(Integer id, int ihsGameNbr, String home, String guest, Date date, Date time, String location, Serializable isOvertime, Serializable isPenaltyShootOut, Serializable isCompleted) {
        this.id = id;
        this.ihsGameNbr = ihsGameNbr;
        this.home = home;
        this.guest = guest;
        this.date = date;
        this.time = time;
        this.location = location;
        this.isOvertime = isOvertime;
        this.isPenaltyShootOut = isPenaltyShootOut;
        this.isCompleted = isCompleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIhsGameNbr() {
        return ihsGameNbr;
    }

    public void setIhsGameNbr(int ihsGameNbr) {
        this.ihsGameNbr = ihsGameNbr;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public Short getScoreHome() {
        return scoreHome;
    }

    public void setScoreHome(Short scoreHome) {
        this.scoreHome = scoreHome;
    }

    public Short getScoreGuest() {
        return scoreGuest;
    }

    public void setScoreGuest(Short scoreGuest) {
        this.scoreGuest = scoreGuest;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Serializable getIsOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(Serializable isOvertime) {
        this.isOvertime = isOvertime;
    }

    public Serializable getIsPenaltyShootOut() {
        return isPenaltyShootOut;
    }

    public void setIsPenaltyShootOut(Serializable isPenaltyShootOut) {
        this.isPenaltyShootOut = isPenaltyShootOut;
    }

    public Serializable getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Serializable isCompleted) {
        this.isCompleted = isCompleted;
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
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.friedli.infosystem.entity.Game[ id=" + id + " ]";
    }
    
}
