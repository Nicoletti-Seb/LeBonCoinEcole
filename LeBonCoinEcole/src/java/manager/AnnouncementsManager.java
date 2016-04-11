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
import modele.Category;
import modele.Student;

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
    
    public Announcement getAnnouncement (int id ){
        return em.find(Announcement.class, id);
    }

    public Announcement createAnnouncement(String title, String description) {
        Announcement a = new Announcement(title, description);
        em.merge(a);
        return a;
    }
    
     public Announcement createAnnouncement(Student student, String title, String description,
             float price, List<Category> categories, byte[] image ) {
        Announcement a = new Announcement(student, title, description, price, categories, image);
        return em.merge(a);
    }
    
    public Collection<Announcement> getAllAnnouncements() {
        Query q = em.createQuery("select a from Announcement a ORDER BY a.startDate DESC");
        return q.getResultList();
    }
    
    public Collection<Announcement> searchAnnouncements(int off, int end, String key, String nameSchool,
                String areaCode, int minPrice, int maxPrice, List<String> categories) {
        String where = constructSearchWhereRequest(key, nameSchool, areaCode, minPrice, maxPrice, categories);
        Query q = null;
        if( where.isEmpty() ){
            q = em.createQuery("select a from Announcement a order by a.startDate desc");
        }
        else{
            System.out.println("Request : " + "select a from Announcement a where " + where + " order by a.startDate desc");
            q = em.createQuery("select distinct a from Announcement a where " + where + " order by a.startDate desc");
            
            
           if( !categories.isEmpty() ){
               q.setParameter("categories", categories);
            }
        }
        
        q.setFirstResult(off);
        q.setMaxResults(end);
        
        return q.getResultList();
    }
    
    
    private String constructSearchWhereRequest(String key, String nameSchool,
                String areaCode, int minPrice, int maxPrice, List<String> categories){
        StringBuilder where = new StringBuilder();
        
        //KEY WORD
        if( !key.isEmpty() ){
           where.append(" (a.title like '%").append(key).append("%' or a.description like '%").append(key).append("%') ");
        }
        
        //SCHOOL
        if( !nameSchool.isEmpty() ){
            if( ! where.toString().isEmpty() ){
                where.append(" and ");
            }
            where.append(" a.student.school.name = '").append(nameSchool).append("'");
        }
        
        //AREA CODE
        if( !areaCode.isEmpty() ){
             if( ! where.toString().isEmpty() ){
                where.append(" and ");
            }
            where.append(" a.student.school.address.codeArea = '").append(areaCode).append("'");;
        }
        
        //CATEGORIES
        if( !categories.isEmpty() ){
            if( ! where.toString().isEmpty() ){
                where.append(" and ");
            }
            where.append( " a.categories in ( select c from Category c where c.name in :categories ) ");
        }
        
        //PRICE
        if( minPrice > 0 ){
            if( ! where.toString().isEmpty() ){
                where.append(" and ");
            }
            where.append(minPrice).append(" <= a.price ");
        }
        
        if( maxPrice > 0 ){
            if( ! where.toString().isEmpty() ){
                where.append(" and ");
            }
            where.append("a.price <= ").append(maxPrice);
        }
        
        return where.toString();
    }
    
    public long countSearchAnnouncements( String key, String nameSchool,
                String areaCode, int minPrice, int maxPrice, List<String> categories) {
        String where = constructSearchWhereRequest(key, nameSchool, areaCode, minPrice, maxPrice, categories);
        Query q = null;
        if( where.isEmpty() ){
            q = em.createQuery("select count(a) from Announcement a");
        }
        else{
            q = em.createQuery("select distinct count(a) from Announcement a WHERE " + where);
            
            if( !categories.isEmpty() ){
               q.setParameter("categories", categories);
            }
        }
        return (Long)q.getSingleResult();
    }
    
    public Collection<Announcement> getAnnouncements(int off, int end) {
        Query q = em.createQuery("select a from Announcement a order by a.startDate desc");
        q.setFirstResult(off);
        q.setMaxResults(end);
        return q.getResultList();
    }
    
    public Long countAnnouncements() {
        Query q = em.createQuery("select count(a) from Announcement a");
        return (Long) q.getSingleResult();
    }
    
    public int deleteAnnouncement(int id) {
        Query q = em.createQuery("delete from Announcement a where a.id=:id");
        q.setParameter("id", id);
        return q.executeUpdate();
    }   
}
