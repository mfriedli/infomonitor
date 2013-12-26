package ch.friedli.infosystem.admin.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mfrie_000
 */
@XmlRootElement(name="BreakingNewsItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class BreakingNewsItem {
    @XmlElement
    private Integer id;
    @XmlElement
    private String text;
    @XmlElement
    private String author;
    @XmlElement
    private boolean isActive;
    @XmlElement
    private boolean isBlinking;
    @XmlElement
    private String dateString;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsBlinking() {
        return isBlinking;
    }

    public void setIsBlinking(boolean isBlinking) {
        this.isBlinking = isBlinking;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public String toString() {
        return "BreakingNewsItem{" + "id=" + id + ", text=" + text + ", author=" + author + ", isActive=" + isActive + ", isBlinking=" + isBlinking + ", dateString=" + dateString + '}';
    }

}
