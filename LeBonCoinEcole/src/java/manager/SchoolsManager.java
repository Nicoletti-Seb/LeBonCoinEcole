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
        createFrenchSchool("Ecole nationale supérieure de la nature et du paysage", new Address("9 rue de la Chocolaterie", "41029", "Blois", "France", 1.3254551f, 47.5838942f), "193");
        createFrenchSchool("IXAD Ecole des Avocats Nord-Ouest", new Address("1 place Déliot", "59024", "Lille", "France", 3.0686492f, 50.6197142f), "295");
        createFrenchSchool("Institut national des techniques économiques et comptables", new Address("8 boulevard Louis XIV", "59024", "Lille", "France", 0.129004f, 49.4958179f), "603");
        createFrenchSchool("Médiaquitaine - Centre régional de formation aux carrières des bibliothèques", new Address("10 A avenue d'Aquitaine", "33172", "Gradignan", "France", -0.5561339f, 44.8298632f), "1267");
        createFrenchSchool("ESPÉ - École supérieure du professorat et de l'éducation Célestin Freinet (site de Nice, George V)", new Address("89 avenue Georges V", "06088", "Nice", "France", 5.890045f, 43.101422f), "5074");
        createFrenchSchool("Institut supérieur d'économie et de management", new Address("24 avenue des Diables bleus", "06088", "Nice", "France", 7.2903508f, 43.7083101f), "23627");
        createFrenchSchool("IUP Tourisme", new Address("Villa Arcadie", "06088", "Nice", "France", 7.2483479f, 43.6942128f), "22607");
        createFrenchSchool("Faculté de lettres, arts et sciences humaines - Campus St Jean d'angely", new Address("24 avenue des Diables bleus", "06088", "Nice", "France", 7.2884677f, 43.7082791f), "22446");
        createFrenchSchool("IUP Miage", new Address("1645 route des Lucioles", "06018", "Biot", "France", 7.0637145f, 43.6166598f), "22606");
        createFrenchSchool("Faculté de chirurgie dentaire", new Address("24 avenue des Diables Bleus", "06088", "Nice", "France", 7.2884677f, 43.7082791f), "18060");
        createFrenchSchool("Institut d'administration des entreprises", new Address("Pôle universitaire St Jean d'Angely", "06018", "Nice", "France", 7.2476801f, 43.6978459f), "16870");
        createFrenchSchool("UFR3 de sciences humaines et sciences de l'environnement", new Address("Route de Mende", "34172", "Montpellier", "France", 3.8663644f, 43.6368173f), "16923");
        createFrenchSchool("UFR de droit et science politique", new Address("39 rue de l'université","34172", "Montpellier", "France", 3.8772542f, 43.614061f), "16973");
        createFrenchSchool("UFR de lettres, langues et sciences humaines", new Address("29 boulevard Gergovia", "63113", "Clermont-Ferrand", "France", 3.0867279f, 45.7707363f), "17121");
        createFrenchSchool("Ecole d'économie", new Address("41 boulevard François Mitterrand", "63113", "Clermont-Ferrand", "France", 3.0863503f, 45.7707643f), "17445");
        createFrenchSchool("Faculté de médecine - Enseignements des techniques de réadaptation", new Address("133 route de Narbonne", "31555", "Toulouse", "France", 1.4650897f, 43.5571642f), "18004");
        createFrenchSchool("UFR de physique et ingénierie", new Address("3-5 rue de l'Université", "67482", "Strasbourg", "France"), null);
    }
    
    public School createFrenchSchool(String name, Address address, String idlink) {
        School s = new School(name, address, "www.onisep.fr/http/redirection/etablissement/identifiant/"+idlink);
        em.merge(s);
        return s;
    }

    public School createSchool(String name, Address address, String link) {
        em.persist(address);
        School s = new School(name, address, link);
        em.persist(s);
        return s;
    }

    public Collection<School> getAllSchools() {
        Query q = em.createQuery("select s from School s");
        return q.getResultList();
    }
    
    public Collection<School> getAllSchools(int off, int count) {
        if (off < 0) {
            off = 0;
        }
        Query q = em.createQuery("select s from School s order by s.name ASC");
        
        q.setFirstResult(off);
        q.setMaxResults(count);
        return q.getResultList();
    }
    
    public long countSchools() {
        Query q = em.createQuery("select count(s) from School s");
        return (long) q.getSingleResult();
    }

    public School getSchool(int id) {
        return em.find(School.class, id);
    }
    
    public School updateSchool(int id, String name, Address address, String link) {
        School s = getSchool(id);
        if (s == null) {
            return null;
        }
        if (!name.isEmpty()) {
            s.setName(name);
        }
        if (address != s.getAddress()) {
            em.persist(address);
            s.setAddress(address);
        }
        
        s.setLink(link);
        
        return s;
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
