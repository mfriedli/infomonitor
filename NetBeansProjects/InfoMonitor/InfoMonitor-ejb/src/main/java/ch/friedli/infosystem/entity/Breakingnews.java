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
@Table(name = "BREAKINGNEWS")
@NamedQueries({
    @NamedQuery(name = "Breakingnews.findAll", query = "SELECT b FROM Breakingnews b"),
    @NamedQuery(name = "Breakingnews.findById", query = "SELECT b FROM Breakingnews b WHERE b.id = :id"),
    @NamedQuery(name = "Breakingnews.findByText", query = "SELECT b FROM Breakingnews b WHERE b.text = :text"),
    @NamedQuery(name = "Breakingnews.findByDate", query = "SELECT b FROM Breakingnews b WHERE b.date = :date"),
    @NamedQuery(name = "Breakingnews.findByAuthor", query = "SELECT b FROM Breakingnews b WHERE b.author = :author"),
    @NamedQuery(name = "Breakingnews.findByIsActive", query = "SELECT b FROM Breakingnews b WHERE b.isActive = :isActive")})
public class Breakingnews implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 150)
    @Column(name = "TEXT")
    private String text;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Size(max = 35)
    @Column(name = "AUTHOR")
    private String author;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ACTIVE")
    private short isActive;

    public Breakingnews() {
    }

    public Breakingnews(Integer id) {
        this.id = id;
    }

    public Breakingnews(Integer id, Date date, short isActive) {
        this.id = id;
        this.date = date;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        if (!(object instanceof Breakingnews)) {
            return false;
        }
        Breakingnews other = (Breakingnews) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.friedli.infosystem.entity.Breakingnews[ id=" + id + " ]";
    }
    
}
