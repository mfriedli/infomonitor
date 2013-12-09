package ch.friedli.secureremoteinterfaceinfomonitor;


import java.util.List;
import javax.ejb.Remote;

/**
 * The remote EJB interfaces to be used by the front-end.
 * 
 * @author Michael Friedli
 */
@Remote
public interface ContentLoaderRemote {
     public List<ContentDetail> loadContentDetails();
     public void createContentEntityItem(ContentDetail contentDetail);
}
