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
import modele.Address;
import modele.School;

/**
 *
 * @author LeoPaul
 */
@Stateless
public class SchoolsManager {

    @PersistenceContext
    private EntityManager em;

    public void createSchoolsTest() {
        createSchool("A", new Address(10, "rue de la boustifaille", "06600", "Nice", "France"));
    }

    public School createSchool(String name, Address address) {
        em.persist(address);
        School s = new School(name, address);
        em.persist(s);
        return s;
    }

    public Collection<School> getAllSchools() {
        Query q = em.createQuery("select s from School s");
        return q.getResultList();
    }

    public int deleteSchool(String name) {
        Query q = em.createQuery("delete from School s where s.name=:name");
        q.setParameter("name", name);
        return q.executeUpdate();
    }
}
