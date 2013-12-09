package ch.friedli.infosystem.business.impl;

import ch.friedli.infosystem.entity.Content;
import ch.friedli.secureremoteinterfaceinfomonitor.ContentDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.ContentLoaderRemote;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mfrie_000
 */
@Stateless
public class ContentLoaderImpl implements ContentLoaderRemote {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void createContentEntityItem(ContentDetail detail) {
        Content entity = new Content();
        entity.setContentUri(detail.getContentUri());
        entity.setContentType(detail.getContentType());
        entity.setCreationTime(new Date());
        entity.setHeight(detail.getHeight());
        entity.setWidth(detail.getWidth());
        entity.setIntervalShow(detail.getShowInterval());
        entity.setIsActive(detail.isIsActive() ? (short) 1 : 0);
        entity.setProtocol(detail.getProtocol());
        entity.setExtWebUrl(detail.getExternalWebUrl());
        this.em.persist(entity);
    }
    
    @Override
    public List<ContentDetail> loadContentDetails() {
        List<ContentDetail> contentDetailItems = new ArrayList<>();
        List<Content> contentItems
                = em.createNamedQuery("Content.findByIsActive").setParameter("isActive", 1).getResultList();
        Collections.sort(contentItems, new PublishDateComparator());
        for (Content item : contentItems) {
            ContentDetail detail = new ContentDetail();
            detail.setContentUri(item.getContentUri());
            detail.setContentType(item.getContentType());
            detail.setHeight(item.getHeight());
            detail.setWidth(item.getWidth());
            detail.setShowInterval(item.getIntervalShow());
            detail.setIsActive(true);
            detail.setProtocol(item.getProtocol());
            detail.setExternalWebUrl(item.getExtWebUrl());
            contentDetailItems.add(detail);
        }
        return contentDetailItems;
    }
    
    class PublishDateComparator implements Comparator<Content> {

        @Override
        public int compare(Content o1, Content o2) {
            return o1.getCreationTime().compareTo(o2.getCreationTime());
        }
    }
}
