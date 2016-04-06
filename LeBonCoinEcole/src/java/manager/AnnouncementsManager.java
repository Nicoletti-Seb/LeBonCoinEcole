/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modele.Announcement;
import modele.Student;

/**
 *
 * @author LeoPaul
 */
@Stateless
public class AnnouncementsManager {
    
    private final List<String> categoriesSelected = new ArrayList<>();
    
    @PersistenceContext
    private EntityManager em;
        
    public void createAnnouncementsTest() {
        for( int i = 0; i < 25; i++ ) {
            createAnnouncement("A", "Text 1");
            createAnnouncement("B", "Text 2");
            createAnnouncement("C", "Text 3");
            createAnnouncement("D", "Text 4");
        }
    }

    public Announcement createAnnouncement(String title, String description) {
        Announcement a = new Announcement(title, description);
        em.persist(a);
        return a;
    }
    
    public Announcement createAnnouncement(String title, String description, Student student) {
        Announcement a = new Announcement(title, description, student);
        em.persist(a);
        return a;
    }
    
    public Collection<Announcement> getAllAnnouncements() {
        Query q = em.createQuery("select a from Announcement a ORDER BY a.startDate DESC");
        return q.getResultList();
    }
    
    public Collection<Announcement> getAnnouncements(int off, int end) {
        Query q = em.createQuery("select a from Announcement a ORDER BY a.startDate DESC");
        q.setFirstResult(off);
        q.setMaxResults(end);
        return q.getResultList();
    }
    
    public Long countAnnouncements() {
        Query q = em.createQuery("select COUNT(a) from Announcement a");
        return (Long) q.getSingleResult();
    }
    
    public int deleteAnnouncement(int id) {
        Query q = em.createQuery("delete from Announcement a where a.id=:id");
        q.setParameter("id", id);
        return q.executeUpdate();
    }
    
    public void updateCategoriesSelected(String category){
        if( category == null || category.isEmpty() ){
            return;
        }
        if( categoriesSelected.contains(category) ){
            categoriesSelected.remove(category);
        }
        else{
            categoriesSelected.add(category);
        }
    }

    public List<String> getCategoriesSelected() {
        return categoriesSelected;
    }
    
    
}
