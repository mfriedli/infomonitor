package ch.friedli.infosystem.business.impl;

import ch.friedli.infosystem.entity.Content;
import ch.friedli.secureremoteinterfaceinfomonitor.ContentDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.ContentLoaderRemote;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mfrie_000
 */
@Stateless
public class ContentLoaderImpl implements ContentLoaderRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteContentEntityItem(Integer itemId) {
        Query query = this.em.createNamedQuery("Content.deleteById").setParameter("id", itemId);
        return query.executeUpdate();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateContentEntityItem(ContentDetail detail) {
        Content itemToBeUpdated = (Content) this.em.createNamedQuery("Content.findById").setParameter("id", detail.getId()).getSingleResult();
        if (itemToBeUpdated != null) {
            itemToBeUpdated.setExtWebUrl(detail.getExternalWebUrl());
            itemToBeUpdated.setWidth(detail.getWidth());
            itemToBeUpdated.setHeight(detail.getHeight());
            itemToBeUpdated.setIsActive(detail.isIsActive() == true ? Short.parseShort("1") : Short.parseShort("0"));
            itemToBeUpdated.setIntervalShow(detail.getShowInterval());
            itemToBeUpdated.setCreationTime(new Date());
            itemToBeUpdated.setSortOrder(detail.getSortOrder());
            this.em.persist(itemToBeUpdated);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContentDetail loadContentDetailById(Integer itemId) {
        Content item = (Content) this.em.createNamedQuery("Content.findById").setParameter("id", itemId).getSingleResult();
        return convertConvertDetail(item);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
        entity.setSortOrder(detail.getSortOrder());
        this.em.persist(entity);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContentDetail> loadAllContentDetails() {
        List<ContentDetail> contentDetailItems = new ArrayList<>();
        List<Content> contentItems
                = em.createNamedQuery("Content.findAll").getResultList();
        Collections.sort(contentItems, new SortOrderComparator());
        for (Content item : contentItems) {
            contentDetailItems.add(convertConvertDetail(item));
        }
        return contentDetailItems;
    }    

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContentDetail> loadActiveContentDetails() {
        List<ContentDetail> contentDetailItems = new ArrayList<>();
        List<Content> contentItems
                = em.createNamedQuery("Content.findByIsActive").setParameter("isActive", 1).getResultList();
        Collections.sort(contentItems, new SortOrderComparator());
        for (Content item : contentItems) {
            contentDetailItems.add(convertConvertDetail(item));
        }
        return contentDetailItems;
    }

    class PublishDateComparator implements Comparator<Content> {

        @Override
        public int compare(Content o1, Content o2) {
            return o1.getCreationTime().compareTo(o2.getCreationTime());
        }
    }
    class SortOrderComparator implements Comparator<Content> {

        @Override
        public int compare(Content o1, Content o2) {
            return o1.getSortOrder().compareTo(o2.getSortOrder());
        }
    }
    
    private ContentDetail convertConvertDetail(Content item) {
        ContentDetail detail = new ContentDetail();
        detail.setId(item.getId());
        detail.setContentUri(item.getContentUri());
        detail.setContentType(item.getContentType());
        detail.setHeight(item.getHeight());
        detail.setWidth(item.getWidth());
        detail.setShowInterval(item.getIntervalShow());
        detail.setIsActive((item.getIsActive()==1));
        detail.setProtocol(item.getProtocol());
        detail.setExternalWebUrl(item.getExtWebUrl());
        SimpleDateFormat df = new SimpleDateFormat("dd. MMM YYYY HH:mm:ss");
        detail.setCreateDateString(df.format(item.getCreationTime()));
        detail.setSortOrder(item.getSortOrder());
        return detail;
    }
}
