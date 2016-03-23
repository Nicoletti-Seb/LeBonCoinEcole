/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.Collection;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modele.Announcement;
import modele.Category;

/**
 *
 * @author LeoPaul
 */
@Stateless
public class AnnouncementsManager {
    
    @PersistenceContext
    private EntityManager em;
        
    public void createAnnouncementsTest() {
        createAnnouncement("A", "Text 1");
        createAnnouncement("B", "Text 2");
        createAnnouncement("C", "Text 3");
        createAnnouncement("D", "Text 4");
    }

    public Announcement createAnnouncement(String title, String description) {
        Announcement a = new Announcement(title, description);
        em.persist(a);
        return a;
    }
    
    public Collection<Announcement> getAllAnnouncements() {
        Query q = em.createQuery("select a from Announcement a");
        return q.getResultList();
    }
    
    public int deleteAnnouncement(int id) {
        Query q = em.createQuery("delete from Announcement a where a.id=:id");
        q.setParameter("id", id);
        return q.executeUpdate();
    }
}