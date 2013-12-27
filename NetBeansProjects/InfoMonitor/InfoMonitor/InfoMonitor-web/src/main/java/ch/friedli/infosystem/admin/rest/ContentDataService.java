package ch.friedli.infosystem.admin.rest;

import ch.friedli.secureremoteinterfaceinfomonitor.ContentDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.ContentLoaderRemote;
import java.math.BigDecimal;
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
public class ContentDataService {

    private static final Logger LOGGER = Logger.getLogger(ContentDataService.class.getName());

    @EJB
    ContentLoaderRemote contentLoader;

    @DELETE
    @Path("/deleteItem/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteItem(@PathParam("id") Integer itemId) {
        this.contentLoader.deleteContentEntityItem(itemId);
    }
    
    @PUT
    @Path("/updateItem/id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateItem(ContentItem item) {
        LOGGER.log(Level.FINE, "update called");
        ContentDetail detail = new ContentDetail();
        detail.setExternalWebUrl(item.getExternalWebUrl());
        detail.setWidth(item.getWidth());
        detail.setContentUri(item.getContentUri());
        detail.setHeight(item.getHeight());
        detail.setIsActive(item.isIsActive());
        detail.setShowInterval(item.getInterval());
        detail.setId(item.getId());
        detail.setSortOrder(item.getSortOrder());
        this.contentLoader.updateContentEntityItem(detail);
    }
    
    @GET
    @Path("/contentItem/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getContentItemById(@PathParam("id") Integer itemId) {
        ContentDetail detail = this.contentLoader.loadContentDetailById(itemId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("id", detail.getId())
            .add("isActive", detail.isIsActive())
            .add("contentType", detail.getContentType())
            .add("contentUri", detail.getContentUri() == null ? "" : detail.getContentUri())
            .add("interval", detail.getShowInterval())
            .add("protocol", detail.getProtocol() == null ? "" : detail.getProtocol())
            .add("externalWebUrl", detail.getExternalWebUrl() == null ? "" : detail.getExternalWebUrl())
            .add("width", detail.getWidth() == null ? 1300 : detail.getWidth())
            .add("height", detail.getHeight() == null ? 950 : detail.getHeight())
            .add("date", detail.getCreateDateString() == null ? "" : detail.getCreateDateString())            
            .add("sortOrder", detail.getSortOrder());
            
            return builder.build().toString();
    }
    
    @GET
    @Path("/contentItems")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllContentItems() {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (ContentDetail detail : this.contentLoader.loadAllContentDetails()) {
            builder.add(
                    Json.createObjectBuilder()
                    .add("id", detail.getId())
                    .add("isActive", detail.isIsActive())
                    .add("contentType", detail.getContentType())
                    .add("contentUri", detail.getContentUri() == null ? "" : detail.getContentUri())
                    .add("interval", detail.getShowInterval())
                    .add("protocol", detail.getProtocol() == null ? "" : detail.getProtocol())
                    .add("externalWebUrl", detail.getExternalWebUrl() == null ? "" : detail.getExternalWebUrl())
                    .add("width", detail.getWidth() == null ? 1300 : detail.getWidth())
                    .add("height", detail.getHeight() == null ? 950 : detail.getHeight())
                    .add("date", detail.getCreateDateString() == null ? "" : detail.getCreateDateString())
                    .add("sortOrder", detail.getSortOrder())
                    
            );
        }
        JsonArray detailsJson = builder.build();
        return detailsJson.toString();
    }
}
