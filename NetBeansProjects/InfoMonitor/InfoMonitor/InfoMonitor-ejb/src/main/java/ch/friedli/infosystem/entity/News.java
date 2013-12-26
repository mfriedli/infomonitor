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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "NEWS")
@NamedQueries({
    @NamedQuery(name = "News.findAll", query = "SELECT n FROM News n"),
    @NamedQuery(name = "News.findById", query = "SELECT n FROM News n WHERE n.id = :id"),
    @NamedQuery(name = "News.findByTitle", query = "SELECT n FROM News n WHERE n.title = :title"),
    @NamedQuery(name = "News.findByDate", query = "SELECT n FROM News n WHERE n.date = :date"),
    @NamedQuery(name = "News.findByShowInterval", query = "SELECT n FROM News n WHERE n.showInterval = :showInterval"),
    @NamedQuery(name = "News.findByIsHide", query = "SELECT n FROM News n WHERE n.isHide = :isHide")})
public class News implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 60)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 32700)
    @Column(name = "TEXT")
    private String text;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Lob
    @Column(name = "PICTURE")
    private Serializable picture;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHOW_INTERVAL")
    private int showInterval;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_HIDE")
    private short isHide;

    public News() {
    }

    public News(Integer id) {
        this.id = id;
    }

    public News(Integer id, String text, int showInterval, short isHide) {
        this.id = id;
        this.text = text;
        this.showInterval = showInterval;
        this.isHide = isHide;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Serializable getPicture() {
        return picture;
    }

    public void setPicture(Serializable picture) {
        this.picture = picture;
    }

    public int getShowInterval() {
        return showInterval;
    }

    public void setShowInterval(int showInterval) {
        this.showInterval = showInterval;
    }

    public short getIsHide() {
        return isHide;
    }

    public void setIsHide(short isHide) {
        this.isHide = isHide;
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
        if (!(object instanceof News)) {
            return false;
        }
        News other = (News) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.friedli.infosystem.entity.News[ id=" + id + " ]";
    }
    
}
