package ch.friedli.infosystem.business.impl;

import ch.friedli.infosystem.entity.Breakingnews;
import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsDetail;
import ch.friedli.secureremoteinterfaceinfomonitor.BreakingNewsLoaderRemote;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mfrie_000
 */
@Stateless
public class BreakingNewsLoaderImpl implements BreakingNewsLoaderRemote {
   
    @PersistenceContext
    private EntityManager em;

    @Override
    public BreakingNewsDetail loadBreakingNews() {
         List<Breakingnews> news = em.createNamedQuery("Breakingnews.findAll").getResultList();
         for (Breakingnews breakingNews : news) {
             if (breakingNews.getIsActive() == 1) {
                 BreakingNewsDetail detail = new BreakingNewsDetail();
                 detail.setAuthor(breakingNews.getAuthor());
                 DateFormat df = new SimpleDateFormat("dd.MM.yyyy");                 
                 detail.setDateString(df.format(breakingNews.getDate()));
                 detail.setText(breakingNews.getText());
                 return detail;
             }
         }
         return null;
    }
}
