package ch.friedli.infosystem.socket;

import ch.friedli.secureremoteinterfaceinfomonitor.LatestResultDetail;
import java.util.List;
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
public class LatestResultDetailsEncoder implements Encoder.Text<List<LatestResultDetail>> {

    @Override
    public String encode(List<LatestResultDetail> details) throws EncodeException {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (LatestResultDetail detail : details) {
            builder.add(
                    Json.createObjectBuilder()
                    .add("Liga", detail.getLeague())
                    .add("Datum", detail.getDateString())
                    .add("Heim", detail.getHomeTeam())
                    .add("Gast", detail.getGuestTeam())
                    .add("Resultat", detail.getResult())
                    .add("Resultat1", detail.getResultFirstHalf())
                    .add("Resultat2", detail.getResultSecondHalf())
                    .add("Suffix", detail.getScoreSuffix()==null?"":detail.getScoreSuffix())
            );
        }
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
