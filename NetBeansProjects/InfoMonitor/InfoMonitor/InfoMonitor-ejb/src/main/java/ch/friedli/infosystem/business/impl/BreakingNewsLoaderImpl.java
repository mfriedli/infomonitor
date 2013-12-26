package ch.friedli.infosystem.business.impl;

import ch.friedli.infosystem.entity.Breakingnews;
import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsLoaderRemote;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class BreakingNewsLoaderImpl implements BreakingNewsLoaderRemote {
   
    @PersistenceContext
    private EntityManager em;

    /**
     * Only returns the first active breaking news item found...only one breaking news
     * item will be shown on the monitor.
     * 
     * @return the first active breaking news item found
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public BreakingNewsDetail loadBreakingNews() {
         List<Breakingnews> news = em.createNamedQuery("Breakingnews.findAll").getResultList();
         for (Breakingnews breakingNews : news) {
             if (breakingNews.getIsActive() == 1) {
                 return convertBreakingNewsDetail(breakingNews);
             }
         }
         return null;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<BreakingNewsDetail> loadAllBreakingNews() {
         List<Breakingnews> news = em.createNamedQuery("Breakingnews.findAll").getResultList();
         List<BreakingNewsDetail> result = new ArrayList<>();
         for (Breakingnews breakingNews : news) {
             result.add(convertBreakingNewsDetail(breakingNews));            
         }
         return result;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteBreakingNewsEntityItem(Integer itemId) {
        Query query = this.em.createNamedQuery("Breakingnews.deleteById").setParameter("id", itemId);
        return query.executeUpdate();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateBreakingNewsEntityItem(BreakingNewsDetail detail) {
        Breakingnews itemToBeUpdated = (Breakingnews) this.em.createNamedQuery("Breakingnews.findById").setParameter("id", detail.getId()).getSingleResult();
        if (itemToBeUpdated != null) {
            itemToBeUpdated.setAuthor(detail.getAuthor());
            itemToBeUpdated.setDate(new Date());
            itemToBeUpdated.setText(detail.getText());
            itemToBeUpdated.setIsActive(detail.isIsActive() == true ? (short)1 : (short)0);
            itemToBeUpdated.setIsBlinking(detail.isIsBlinking() == true ? (short)1 : (short)0);
            this.em.persist(itemToBeUpdated);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public BreakingNewsDetail loadBreakingNewsDetailById(Integer itemId) {
        Breakingnews item = (Breakingnews) this.em.createNamedQuery("Breakingnews.findById").setParameter("id", itemId).getSingleResult();
        return convertBreakingNewsDetail(item);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createBreakingNewsEntityItem(BreakingNewsDetail detail) {
        Breakingnews entity = new Breakingnews();        
        entity.setDate(new Date());       
        entity.setIsActive(detail.isIsActive() ? (short) 1 : 0);
        entity.setIsBlinking(detail.isIsBlinking() ? (short) 1 : 0);
        entity.setAuthor(detail.getAuthor());
        entity.setText(detail.getText());
        this.em.persist(entity);
    }

    private BreakingNewsDetail convertBreakingNewsDetail(Breakingnews item) {
        BreakingNewsDetail detail = new BreakingNewsDetail();
        detail.setId(item.getId());
        detail.setText(item.getText());
        detail.setAuthor(item.getAuthor());
        SimpleDateFormat df = new SimpleDateFormat("dd. MMM YYYY");
        detail.setDateString(df.format(item.getDate()));
        detail.setIsActive((item.getIsActive() != 0));
        detail.setIsBlinking((item.getIsBlinking() != 0));
        return detail;
    }
}
