package ch.friedli.infosystem.socket;

import ch.friedli.secureremoteinterfaceinfomonitor.TotomatDetail;
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
public class TotomatDetailsEncoder implements Encoder.Text<List<TotomatDetail>> {

    @Override
    public String encode(List<TotomatDetail> details) throws EncodeException {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (TotomatDetail detail : details) {
            builder.add(
                    Json.createObjectBuilder()
                    .add("Liga", detail.getLeague())
                    .add("Datum", detail.getDateString())
                    .add("Heim", detail.getHomeTeam())
                    .add("Gast", detail.getGuestTeam())
                    .add("Resultat", detail.getResult().isEmpty()?"-:-":detail.getResult())
                    .add("Resultat1", detail.getResultFirstHalf().isEmpty()?"-:-":detail.getResultFirstHalf())
                    .add("Resultat2", detail.getResultSecondHalf().isEmpty()?"-:-":detail.getResultSecondHalf())
                    .add("Suffix", detail.getScoreSuffix() == null ? "" : detail.getScoreSuffix())
                    .add("IsRunning", detail.isIsRunning())
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
