/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modele.Administrator;
import modele.Student;

/**
 *
 * @author LeoPaul
 */
@Stateless
public class AdminManager {
    
    @PersistenceContext
    private EntityManager em;

    public void createAdministratorTest() {
        createAdministrator("admin", "pass");
    }

    public Administrator createAdministrator(String username, String password) {
        Administrator a = new Administrator(username, password);
        em.persist(a);
        return a;
    }
    
    public Administrator lookingByUsername(String username) {
        Query q = em.createQuery("select a from Administrator a where a.username=:username");
        q.setParameter("username", username);

        try {
            return (Administrator) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public boolean isAdmin(String username, String password) {
        Administrator a = lookingByUsername(username);
        if (a == null) {
            return false;
        } else {
            return a.getPassword().equals(password);
        }
    }
    
}
