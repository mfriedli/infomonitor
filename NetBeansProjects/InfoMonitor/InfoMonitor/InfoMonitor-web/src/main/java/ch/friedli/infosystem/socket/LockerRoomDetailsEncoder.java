package ch.friedli.infosystem.socket;


import ch.friedli.secureremoteinterfaceinfomonitor.LockerRoomDetail;
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
public class LockerRoomDetailsEncoder implements Encoder.Text<List<LockerRoomDetail>> {

    @Override
    public String encode(List<LockerRoomDetail> details) throws EncodeException {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (LockerRoomDetail detail : details) {
            builder.add(
                    Json.createObjectBuilder()
                        .add("Garderobe", detail.getRoomNumber())
                        .add("Datum", detail.getDateString())
                        .add("Zeit", detail.getTimeString())
                        .add("Team", detail.getTeamName())
                        .add("Liga", detail.getLeague())
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
