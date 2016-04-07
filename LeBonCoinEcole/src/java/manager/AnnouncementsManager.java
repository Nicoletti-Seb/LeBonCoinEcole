/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modele.Announcement;
import modele.Student;
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
    
    public Collection<Announcement> searchAnnouncements(int off, int end, String key, String nameSchool,
                String areaCode, int minPrice, int maxPrice, List<Category> categories) {
        String where = constructWhereRequest(key, nameSchool, areaCode, minPrice, maxPrice, categories);
        Query q = null;
        if( where.isEmpty() ){
            q = em.createQuery("select a from Announcement a ORDER BY a.startDate DESC");
        }
        else{
            q = em.createQuery("select a from Announcement a WHERE " + where + " ORDER BY a.startDate DESC");
            
            if( !categories.isEmpty() ){
                q.setParameter("categories", categories);
            }
        }
        
        q.setFirstResult(off);
        q.setMaxResults(end);
        
        return q.getResultList();
    }
    
    
    private String constructWhereRequest(String key, String nameSchool,
                String areaCode, int minPrice, int maxPrice, List<Category> categories){
        StringBuilder where = new StringBuilder();
        
        //KEY WORD
        if( !key.isEmpty() ){
           where.append(" (a.title LIKE '%").append(key).append("%' OR a.description LIKE '%").append(key).append("%') ");
        }
        
        //SCHOOL
        if( !nameSchool.isEmpty() ){
            if( ! where.toString().isEmpty() ){
                where.append(" AND ");
            }
            where.append(" a.student.school.name = '").append(nameSchool).append("'");
        }
        
        //AREA CODE
        if( !areaCode.isEmpty() ){
             if( ! where.toString().isEmpty() ){
                where.append(" AND ");
            }
            where.append(" a.student.school.address.codeArea = '").append(areaCode).append("'");;
        }
        
        //CATEGORIES
        if( !categories.isEmpty() ){
             if( ! where.toString().isEmpty() ){
                where.append(" AND ");
            }
            where.append(" a.categories IN :categories ");
        }
        
        //PRICE
        if( minPrice > 0 ){
            if( ! where.toString().isEmpty() ){
                where.append(" AND ");
            }
            where.append(minPrice).append(" <= a.price ");
        }
        
        if( maxPrice > 0 ){
            if( ! where.toString().isEmpty() ){
                where.append(" AND ");
            }
            where.append("a.price <= ").append(maxPrice);
        }
        
        return where.toString();
    }
    
    public long countSearchAnnouncements( String key, String nameSchool,
                String areaCode, int minPrice, int maxPrice, List<Category> categories) {
        String where = constructWhereRequest(key, nameSchool, areaCode, minPrice, maxPrice, categories);
        Query q = null;
        if( where.isEmpty() ){
            q = em.createQuery("select COUNT(a) from Announcement a");
        }
        else{
            q = em.createQuery("select COUNT(a) from Announcement a WHERE " + where);
            
            if( !categories.isEmpty() ){
                q.setParameter("categories", categories);
            }
        }
        return (Long)q.getSingleResult();
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
}
