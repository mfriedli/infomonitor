package ch.friedli.infosystem.admin.rest;

import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsLoaderRemote;
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
public class BreakingNewsDataService {

    private static final Logger LOGGER = Logger.getLogger(BreakingNewsDataService.class.getName());

    @EJB
    BreakingNewsLoaderRemote breakingNewsLoader;

    @DELETE
    @Path("/deleteNewsItem/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteBreakingNewsItem(@PathParam("id") Integer itemId) {
        this.breakingNewsLoader.deleteBreakingNewsEntityItem(itemId);
    }
    
    @PUT
    @Path("/updateNewsItem/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateBreakingNewsItem(BreakingNewsItem item) {
        LOGGER.log(Level.FINE, "update breaking news called");
        BreakingNewsDetail detail = new BreakingNewsDetail();
        detail.setText(item.getText());
        detail.setAuthor(item.getAuthor());
        detail.setIsActive(item.isIsActive());
        detail.setIsBlinking(item.isIsBlinking());
        detail.setId(item.getId());
        this.breakingNewsLoader.updateBreakingNewsEntityItem(detail);
    }
        
    @POST
    @Path("/addNewsItem")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBreakingNewsItem(BreakingNewsItem item) {
        LOGGER.log(Level.FINE, "add breaking news item called");
        BreakingNewsDetail detail = new BreakingNewsDetail();
        detail.setText(item.getText());
        detail.setAuthor(item.getAuthor());
        detail.setIsActive(item.isIsActive());
        detail.setIsBlinking(item.isIsBlinking());
        this.breakingNewsLoader.createBreakingNewsEntityItem(detail);
    }
    
    @GET
    @Path("/newsItem/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBreakingNewsItemById(@PathParam("id") Integer itemId) {
        BreakingNewsDetail detail = this.breakingNewsLoader.loadBreakingNewsDetailById(itemId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("id", detail.getId())
            .add("text", detail.getText() == null ? "" : detail.getText())
            .add("author", detail.getAuthor() == null ? "" : detail.getAuthor())
            .add("isBlinking", detail.isIsBlinking())
            .add("isActive", detail.isIsActive())
            .add("date", detail.getDateString() == null ? "" : detail.getDateString());
            
            return builder.build().toString();
    }
    
    @GET
    @Path("/newsItems")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllBreakingNewsItems() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (BreakingNewsDetail detail : this.breakingNewsLoader.loadAllBreakingNews()) {
            builder.add(
                    Json.createObjectBuilder()
                    .add("id", detail.getId())
                    .add("text", detail.getText() == null ? "" : detail.getText())
                    .add("author", detail.getAuthor() == null ? "" : detail.getAuthor())
                    .add("isBlinking", detail.isIsBlinking())
                    .add("isActive", detail.isIsActive())
                    .add("date", detail.getDateString() == null ? "" : detail.getDateString())
            );
        }
        JsonArray detailsJson = builder.build();
        return detailsJson.toString();
    }
}
