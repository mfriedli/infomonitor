package ch.friedli.infosystem.admin.rest;

import ch.friedli.secureremoteinterfaceinfomonitor.ConfigurationLoaderRemote;
import ch.friedli.secureremoteinterfaceinfomonitor.LeagueDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.SeasonDetail;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mfrie_000
 */
@Stateless
@Path("/")
public class ConfigurationDataService {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationDataService.class.getName());

    @EJB
    ConfigurationLoaderRemote configurationLoader;

    @DELETE
    @Path("/deleteLeagueItem/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteLeagueItem(@PathParam("id") Integer itemId) {
        this.configurationLoader.deleteLeagueEntityItem(itemId);
    }
    
    @DELETE
    @Path("/deleteSeasonItem/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteSeasonItem(@PathParam("id") Integer itemId) {
        this.configurationLoader.deleteSeasonEntityItem(itemId);
    }
    
    @PUT
    @Path("/updateSeasonItem/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateSeasonItem(SeasonItem item) {
        LOGGER.log(Level.FINE, "update season called");
        SeasonDetail detail = new SeasonDetail();
        detail.setSeasonId(item.getSeasonId());
        detail.setSeasonName(item.getSeasonName());
        detail.setIsActive(item.isIsActive());
        detail.setId(item.getId());
        this.configurationLoader.updateSeasonEntityItem(detail);
    }
     
    @PUT
    @Path("/updateLeagueItem/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateLeagueItem(LeagueItem item) {
        LOGGER.log(Level.FINE, "update league called");
        LeagueDetail detail = new LeagueDetail();
        detail.setLeagueId(item.getLeagueId());
        detail.setLeagueName(item.getLeagueName());
        detail.setLeagueShortName(item.getLeagueShortName());
        detail.setId(item.getId());
        this.configurationLoader.updateLeagueEntityItem(detail);
    }
    
    @POST
    @Path("/addSeasonItem")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addSeasonItem(SeasonItem item) {
        LOGGER.log(Level.FINE, "add season item called");
        SeasonDetail detail = new SeasonDetail();
        detail.setSeasonId(item.getSeasonId());
        detail.setSeasonName(item.getSeasonName());
        detail.setIsActive(item.isIsActive());
        this.configurationLoader.createSeasonEntityItem(detail);
    }
    
    @POST
    @Path("/addLeagueItem")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addLeagueItem(LeagueItem item) {
        LOGGER.log(Level.FINE, "add league item called");
        LeagueDetail detail = new LeagueDetail();
        detail.setLeagueId(item.getLeagueId());
        detail.setLeagueName(item.getLeagueName());
        detail.setLeagueShortName(item.getLeagueShortName());
        this.configurationLoader.createLeagueEntityItem(detail);
    }
    
    @GET
    @Path("/leagueItem/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLeagueItemById(@PathParam("id") Integer itemId) {
        LeagueDetail detail = this.configurationLoader.loadLeagueDetailById(itemId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", detail.getId())
            .add("leagueId", detail.getLeagueId())
            .add("leagueName", detail.getLeagueName()== null ? "" : detail.getLeagueName())
            .add("leagueShortName", detail.getLeagueShortName() == null ?"" : detail.getLeagueShortName());
            
            return builder.build().toString();
    }
    
    @GET
    @Path("/seasonItem/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSeasonItemById(@PathParam("id") Integer itemId) {
        SeasonDetail detail = this.configurationLoader.loadSeasonDetailById(itemId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", detail.getId())
            .add("seasonId", detail.getSeasonId())
            .add("seasonName", detail.getSeasonName() == null ? "" : detail.getSeasonName())
            .add("isActive", detail.isIsActive());
            
            return builder.build().toString();
    }
    
    @GET
    @Path("/leagueItems")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllLeagueItems() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (LeagueDetail detail : this.configurationLoader.loadAllLeagues()) {
            builder.add(
                    Json.createObjectBuilder()
                    .add("id", detail.getId())
                    .add("leagueId", detail.getLeagueId())
                    .add("leagueName", detail.getLeagueName()== null ? "" : detail.getLeagueName())
                    .add("leagueShortName", detail.getLeagueShortName() == null ? "" : detail.getLeagueShortName())
            );
        }
        JsonArray detailsJson = builder.build();
        return detailsJson.toString();
    }
    
    @GET
    @Path("/seasonItems")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllSeasonItems() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (SeasonDetail detail : this.configurationLoader.loadAllSeasons()) {
            builder.add(
                    Json.createObjectBuilder()
                    .add("id", detail.getId())
                    .add("seasonId", detail.getSeasonId())
                    .add("seasonName", detail.getSeasonName()== null ? "" : detail.getSeasonName())
                    .add("isActive", detail.isIsActive())
            );
        }
        JsonArray detailsJson = builder.build();
        return detailsJson.toString();
    }
}
