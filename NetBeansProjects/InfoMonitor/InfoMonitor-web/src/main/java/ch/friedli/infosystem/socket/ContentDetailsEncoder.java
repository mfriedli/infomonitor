package ch.friedli.infosystem.socket;

import ch.friedli.secureremoteinterfaceinfomonitor.ContentDetail;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Michael Friedli
 */
public class ContentDetailsEncoder implements Encoder.Text<ContentDetail> {

    @Override
    public String encode(ContentDetail detail) throws EncodeException {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        //builder.add("contentData",detail.createHtml());
        builder.add("isActive", detail.isIsActive())
                .add("contentType", detail.getContentType())
                .add("contentUri", detail.getContentUri()==null?"":detail.getContentUri())
                .add("interval", detail.getShowInterval())
                .add("protocol", detail.getProtocol()==null?"":detail.getProtocol())
                .add("externalWebUrl", detail.getExternalWebUrl()==null?"":detail.getExternalWebUrl())
                .add("width", detail.getWidth())
                .add("height", detail.getHeight());
        JsonObject detailsJson = builder.build();
        return detailsJson.toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
