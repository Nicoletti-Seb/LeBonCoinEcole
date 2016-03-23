/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modele.School;
import modele.Student;
import utils.RandomMot;

/**
 *
 * @author Seb
 */
@Stateless
public class UsersManager {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;
    
    private static final int NB_USER = 10;
    
    public void createStudentsTest() {
        createStudent("John", "Lennon", "jlennon");
        createStudent("Paul", "Mac Cartney", "pmc");
        createStudent("Ringo", "Starr", "rstarr");
        createStudent("Georges", "Harisson", "georgesH");
    }

    public Student createStudent(String lastname, String firstname, String username) {
        Student u = new Student(lastname, firstname, username);
        em.persist(u);
        return u;
    }
    
    public Student createStudent(String lastname, String firstname, String username, String password, School school) {
        Student u = new Student(lastname, firstname, username, password, school);
        em.persist(u);
        return u;
    }

    // créer X utilisateur d'un coup
    // utils.RandomMot
    public void createStudentsTest(int nb) {
        if (nb < 0) {
            nb = 0;
        }
        for (int i = 0; i < nb; i++) {
            createStudent(RandomMot.build(), RandomMot.build(), RandomMot.build());
        }
    }

    // selection de X(10) utilisateur depuis le numéro Y(OFF)
    public Collection<Student> getAllStudents(int off) {
        if (off < 0) {
            off = 0;
        }
        // Exécution d'une requête équivalente à un select *  
        Query q = em.createQuery("select s from Student s");
        q.setMaxResults(NB_USER);
        q.setFirstResult(off);
        return q.getResultList();
    }
    
    public Student lookingByUsername(String username) {
        Query q = em.createQuery("select s from Student s where s.username=:username");
        q.setParameter("username", username);

        try {
            return (Student) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Student updateStudent(String lastname, String firstname, String username) {
        Student s = lookingByUsername(username);
        if (s == null) {
            return null;
        }
        s.setLastname(lastname);
        s.setFirstname(firstname);
        return s;
    }

    public int deleteStudent(String username) {
        Query q = em.createQuery("delete from Student s where s.username=:username");
        q.setParameter("username", username);
        return q.executeUpdate();
    }

    // méthode pour vérifier la connection
    public boolean isStudent(String username, String password) {
        Student s = lookingByUsername(username);
        if (s == null) {
            return false;
        } else {
            return s.getPassword().equals(password);
        }
    }
}
