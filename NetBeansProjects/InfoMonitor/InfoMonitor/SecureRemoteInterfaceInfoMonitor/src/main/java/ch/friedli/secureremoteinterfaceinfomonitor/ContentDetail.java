package ch.friedli.secureremoteinterfaceinfomonitor;

/**
 *
 * @author Michael Frieldi
 */
public class ContentDetail {
    private Integer id;
    private Integer width;
    private Integer height;
    private int showInterval;
    private String contentUri;
    private String contentType;
    private String protocol;
    private boolean isActive;
    private String externalWebUrl;
    private String createDateString;
    private Integer sortOrder;

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getCreateDateString() {
        return createDateString;
    }
    
    public void setCreateDateString(String createDateString) {
        this.createDateString = createDateString;
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

    public int getShowInterval() {
        return showInterval;
    }

    public void setShowInterval(int showInterval) {
        this.showInterval = showInterval;
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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
