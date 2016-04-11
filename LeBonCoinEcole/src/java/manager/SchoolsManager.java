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
        createSchool("A", new Address(10, "rue de la boustifaille", "06600", "Nice", "France"), "www.google.fr");
        createSchool("C", new Address(), "c");
        createSchool("D", new Address(), "d");
        createSchool("E", new Address(), "e");
    }

    public School createSchool(String name, Address address, String link) {
        em.persist(address);
        School s = new School(name, address, link);
        em.merge(s);
        return s;
    }

    public Collection<School> getAllSchools() {
        Query q = em.createQuery("select s from School s");
        return q.getResultList();
    }

    public School getSchool(int id) {
        return em.find(School.class, id);
    }
    
    public int deleteSchool(int id) {
        Query q = em.createQuery("delete from School s where s.id=:id");
        q.setParameter("id", id);
        return q.executeUpdate();
    }
    
    public School getSchool(String name) {
        Query q = em.createQuery("select s from School s where s.name=:name");
        q.setParameter("name", name);
        return (School)q.getSingleResult();
    }
}
