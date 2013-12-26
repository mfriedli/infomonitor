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
@Table(name = "CONTENT")
@NamedQueries({
    @NamedQuery(name = "Content.deleteById", query ="DELETE FROM Content c WHERE c.id = :id"),
    @NamedQuery(name = "Content.findAll", query = "SELECT c FROM Content c"),
    @NamedQuery(name = "Content.findById", query = "SELECT c FROM Content c WHERE c.id = :id"),
    @NamedQuery(name = "Content.findByIsActive", query = "SELECT c FROM Content c WHERE c.isActive = :isActive"),
    @NamedQuery(name = "Content.findByContentType", query = "SELECT c FROM Content c WHERE c.contentType = :contentType"),
    @NamedQuery(name = "Content.findByCreationTime", query = "SELECT c FROM Content c WHERE c.creationTime = :creationTime"),
    @NamedQuery(name = "Content.findByIntervalShow", query = "SELECT c FROM Content c WHERE c.intervalShow = :intervalShow"),
    @NamedQuery(name = "Content.findByWidth", query = "SELECT c FROM Content c WHERE c.width = :width"),
    @NamedQuery(name = "Content.findByHeight", query = "SELECT c FROM Content c WHERE c.height = :height"),
    @NamedQuery(name = "Content.findByProtocol", query = "SELECT c FROM Content c WHERE c.protocol = :protocol"),
    @NamedQuery(name = "Content.findByContentUri", query = "SELECT c FROM Content c WHERE c.contentUri = :contentUri"),
    @NamedQuery(name = "Content.findByExtWebUrl", query = "SELECT c FROM Content c WHERE c.extWebUrl = :extWebUrl")})
public class Content implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ACTIVE")
    private short isActive;
    @Size(max = 20)
    @Column(name = "CONTENT_TYPE")
    private String contentType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTERVAL_SHOW")
    private int intervalShow;
    @Column(name = "WIDTH")
    private Integer width;
    @Column(name = "HEIGHT")
    private Integer height;
    @Size(max = 15)
    @Column(name = "PROTOCOL")
    private String protocol;
    @Size(max = 150)
    @Column(name = "CONTENT_URI")
    private String contentUri;
    @Size(max = 400)
    @Column(name = "EXT_WEB_URL")
    private String extWebUrl;

    public Content() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getIsActive() {
        return isActive;
    }

    public void setIsActive(short isActive) {
        this.isActive = isActive;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public int getIntervalShow() {
        return intervalShow;
    }

    public void setIntervalShow(int intervalShow) {
        this.intervalShow = intervalShow;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getContentUri() {
        return contentUri;
    }

    public void setContentUri(String contentUri) {
        this.contentUri = contentUri;
    }

    public String getExtWebUrl() {
        return extWebUrl;
    }

    public void setExtWebUrl(String extWebUrl) {
        this.extWebUrl = extWebUrl;
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
        if (!(object instanceof Content)) {
            return false;
        }
        Content other = (Content) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.friedli.infosystem.entity.Content[ id=" + id + " ]";
    }
    
}
