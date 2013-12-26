package ch.friedli.infosystem.socket;

import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsDetail;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Michael Friedli
 */
public class BreakingNewsDetailsEncoder implements Encoder.Text<BreakingNewsDetail> {

    @Override
    public String encode(BreakingNewsDetail detail) throws EncodeException {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        builder.add(
                Json.createObjectBuilder()
                .add("Text", detail.getText())
                .add("Datum", detail.getDateString())
                .add("Author", detail.getAuthor())
        );
        JsonArray detailsJson = builder.build();
        return detailsJson.toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
