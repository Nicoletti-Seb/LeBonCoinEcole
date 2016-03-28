/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modele.Category;
import utils.RandomMot;

/**
 *
 * @author LeoPaul
 */
@Stateless
public class CategoriesManager {
    
    @PersistenceContext
    private EntityManager em;
        
    public void createCategoriesTest() {
        for( int i = 0; i < 20; i++){
            createCategory(RandomMot.build());
        }
    }

    public Category createCategory(String name) {
        Category c = new Category(name);
        em.persist(c);
        return c;
    }
    
    public Collection<Category> getAllCategories() {
        Query q = em.createQuery("select c from Category c order by c.name ASC");
        return q.getResultList();
    }
    
    public int deleteCategory(String name) {
        Query q = em.createQuery("delete from Category c where c.name=:name");
        q.setParameter("name", name);
        return q.executeUpdate();
    }
}
