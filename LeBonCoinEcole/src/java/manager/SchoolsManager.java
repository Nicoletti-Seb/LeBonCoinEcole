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
        createSchool("Ecole nationale supérieure de la nature et du paysage", new Address(9, "rue de la Chocolaterie", "41029", "Blois", "France"), "http://www.onisep.fr/http/redirection/etablissement/identifiant/193");
        createSchool("IXAD Ecole des Avocats Nord-Ouest", new Address(1, "place Déliot", "59024", "Lille", "France"), "http://www.onisep.fr/http/redirection/etablissement/identifiant/295");
        createSchool("Institut national des techniques économiques et comptables", new Address(8, "boulevard Louis XIV", "59024", "Lille", "France"), "http://www.onisep.fr/http/redirection/etablissement/identifiant/603");
        createSchool("Médiaquitaine - Centre régional de formation aux carrières des bibliothèques", new Address(10, "A avenue d'Aquitaine", "33172", "Gradignan", "France"), "http://www.onisep.fr/http/redirection/etablissement/identifiant/1267");
    }

    public School createSchool(String name, Address address, String link) {
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
