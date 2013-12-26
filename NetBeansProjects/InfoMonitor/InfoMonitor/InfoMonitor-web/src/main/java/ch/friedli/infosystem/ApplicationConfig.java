package ch.friedli.infosystem;

import ch.friedli.infosystem.admin.rest.ContentDataService;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.filter.LoggingFilter;
 
@ApplicationPath("/rest")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        // Add your resources.
        resources.add(ContentDataService.class);

        // Add additional features such as support for Multipart.
        //resources.add(MultiPartFeature.class);
        // Add LoggingFilter.
        resources.add(LoggingFilter.class);
        return resources;
    }
}
