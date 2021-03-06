package ch.friedli.secureremoteinterfaceinfomonitor;

/**
 *
 * @author Michael Frieldi
 */
public class ContentDetail {
    private Integer width;
    private Integer height;
    private int showInterval;
    private String contentUri;
    private String contentType;
    private String protocol;
    private boolean isActive;
    private String externalWebUrl;
    
    private final static String WEB_SITE = "WEB";
    private final static String PICTURE_JPG = "JPG";
    
    public String createHtml() {
        StringBuilder builder = new StringBuilder();
        if (contentType != null && WEB_SITE.equals(contentType)){
            builder.append("<iframe src=");
            builder.append("\"");
            builder.append(externalWebUrl);
            builder.append("style=\"border:solid 1px #777\" width=1300 height=800 frameborder=0 scrolling=no></iframe>");
        } else if (contentType != null && PICTURE_JPG.equals(contentType)){
            builder.append("<img src=");
            builder.append("\"");
            builder.append(contentUri);
            builder.append("/>");
        }
        return builder.toString();
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
}
