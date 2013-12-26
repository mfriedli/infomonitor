package ch.friedli.infosystem.admin.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mfrie_000
 */
@XmlRootElement(name="ContentItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContentItem {
    @XmlElement
    private Integer id;
    @XmlElement
    private Integer width;
    @XmlElement
    private Integer height;
    @XmlElement
    private int interval;
    @XmlElement
    private String contentUri;
    @XmlElement
    private String contentType;
    @XmlElement
    private String protocol;
    @XmlElement
    private boolean isActive;
    @XmlElement
    private String externalWebUrl;
    @XmlElement
    private String createDateString;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int showInterval) {
        this.interval = showInterval;
    }

    public String getContentUri() {
        return contentUri;
    }

    public void setContentUri(String contentUri) {
        this.contentUri = contentUri;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getExternalWebUrl() {
        return externalWebUrl;
    }

    public void setExternalWebUrl(String externalWebUrl) {
        this.externalWebUrl = externalWebUrl;
    }

    public String getCreateDateString() {
        return createDateString;
    }

    public void setCreateDateString(String createDateString) {
        this.createDateString = createDateString;
    }

    @Override
    public String toString() {
        return "ContentItem{" + "id=" + id + ", width=" + width + ", height=" + height + ", interval=" + interval + ", contentUri=" + contentUri + ", contentType=" + contentType + ", protocol=" + protocol + ", isActive=" + isActive + ", externalWebUrl=" + externalWebUrl + ", createDateString=" + createDateString + '}';
    }
    
}
