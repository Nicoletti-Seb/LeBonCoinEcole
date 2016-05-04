/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modele.Email;
import modele.PhoneNumber;
import modele.School;
import modele.Student;
import utils.RandomBuild;

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

    public void createStudentsMiageTest() {
        createStudent("Bernard", "Alexandre");
        createStudent("Dechamps", "Aurore");
        createStudent("Pomme", "Thomas");
        createStudent("Aydogan", "Mehmet");
        createStudent("Rabadan", "Andy");
        createStudent("Guery", "Steven");
        createStudent("Nagy", "Niko");
        createStudent("Silvestro", "Benoit");
        createStudent("Delas", "Romain");
        createStudent("Lantier", "Sebastien");
        createStudent("Ines", "Houara");
        createStudent("Chauvet", "Clemence");
        createStudent("Pinet", "Jeremy");
        createStudent("Pagh Birk", "Christian");
        createStudent("Spugna", "Lorris");
        createStudent("Barbe", "Brenda");
        createStudent("Rhazadi", "Walid");
        createStudent("Micaleff", "David");
        createStudent("Oualid", "Manai");
        createStudent("Nicoletti", "Sebastien");
        createStudent("Mota", "Thais");
        createStudent("Kaeffer", "Rémi");
        createStudent("Cochin", "Sacha");
        createStudent("Cojocaru", "Dragos");
        createStudent("Lamrani", "Rime");
        createStudent("Aglif", "Fatima");
        createStudent("Bellaiche", "Julian");
        createStudent("Bocarra", "David");
        createStudent("Constantin", "Yannis");
        createStudent("Deschanels", "Isaline");
        createStudent("Elsaesser", "Luc");
        createStudent("Maurisset", "Jason");
        createStudent("Abid", "Anna");
        createStudent("Diarra", "Ibrahima");
        createStudent("Chazarra", "Max");
        createStudent("Dupont", "Kenzo");
        createStudent("Azzaoui", "Reda");
        createStudent("Comba", "Florian");
        createStudent("Mvouma", "Michel");
        createStudent("Puybonnieux", "Pierre");
        createStudent("Rouis", "Myriam");
        createStudent("Eme", "Nag");
        createStudent("Begyn", "Mélissa");
        createStudent("Benyas", "Nour");
        createStudent("Amira", "Mimouna");
    }
    
    public Student createStudentTest(School school) {
        List<PhoneNumber> listPhones = new ArrayList<>();
        listPhones.add(new PhoneNumber("123456789"));
        listPhones.add(new PhoneNumber("987654321"));
        
        List<Email> listEmails = new ArrayList<>();
        listEmails.add(new Email("toto1@toto.fr"));
        listEmails.add(new Email("toto2@toto.fr"));
        Student u = createStudent("nom", "prenom", "username", "pass", school, listPhones, listEmails, null);
        
        return u;
    }
    
    public Student createStudent(String lastname, String firstname) {
        Student u = new Student(lastname, firstname);
        
        List<PhoneNumber> listPhones = new ArrayList<>();
        listPhones.add(new PhoneNumber(RandomBuild.telNumber()));
        listPhones.add(new PhoneNumber(RandomBuild.telNumber()));
        for (PhoneNumber phone : listPhones) {
            em.persist(phone);
        }
        u.setPhoneNumbers(listPhones);
        
        List<Email> listEmails = new ArrayList<>();
        Email email = new Email(firstname + "." + lastname + "@hotmail.fr");
        em.persist(email);
        listEmails.add(email);
        
        u.setEmails(listEmails);
        em.persist(u);
        return u;
    }

    public Student createStudent(String lastname, String firstname, String username, String password,
            School school, List<PhoneNumber> phoneNumbers,
            List<Email> emails, byte[] image) {

        for (PhoneNumber phone : phoneNumbers) {
            em.persist(phone);
        }

        for (Email email : emails) {
            em.persist(email);
        }

        Student u = new Student(lastname, firstname, username, password, school,
                phoneNumbers, emails, image);
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
            createStudent(RandomBuild.name(), RandomBuild.name());
        }
    }

     public Collection<Student> getAllStudents() {  
        Query q = em.createQuery("select s from Student s");
        return q.getResultList();
     }
    
    // selection de X(10) utilisateur depuis le numéro Y(OFF)
    public Collection<Student> getAllStudents(int off, int count) {
        if (off < 0) {
            off = 0;
        }
        // Exécution d'une requête équivalente à un select *  
        Query q = em.createQuery("select s from Student s");
        q.setMaxResults(count);
        q.setFirstResult(off);
        return q.getResultList();
    }
    
    public long countStudents() {
        Query q = em.createQuery("select count(s) from Student s");
        return (long) q.getSingleResult();
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

    public Student updateStudent(String lastname, String firstname, String username, String password,
            School school, List<PhoneNumber> phoneNumbers,
            List<Email> emails, byte[] image) {
        Student s = lookingByUsername(username);
        if (s == null) {
            return null;
        }
        if (!lastname.isEmpty()) {
            s.setLastname(lastname);
        }
        if (!firstname.isEmpty()) {
            s.setFirstname(firstname);
        }
        if (!password.isEmpty()) {
            s.setPassword(password);
        }
        
        if (school != null) {
            s.setSchool(school);
        }

        if (phoneNumbers != null) {
            for (PhoneNumber phone : phoneNumbers) {
                em.persist(phone);
            }
            s.setPhoneNumbers(phoneNumbers);
        }

        if (emails != null) {
            for (Email email : emails) {
                em.persist(email);
            }
            s.setEmails(emails);
        }

        if( image != null ){
            s.setImage(image);
        }
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

    // methode pour associer une école à un étudiant
    public Student addSchool(School school, int idStudent) {
        Student s = em.find(Student.class, idStudent);
        if (s == null) {
            return null;
        }
        s.setSchool(school);
        return s;
    }
    
    public Student synchronised(Student student){
        Student studentUpdate = em.merge(student);
        em.refresh(studentUpdate);
        return studentUpdate;
    }
    
    public void sendToDB(){
        em.flush();
    }

//    public int addSchool(int idSchool, int idStudent) {
//        Query q = em.createQuery("update student set school_id="+idSchool
//                + " where id="+idStudent);
//        return q.executeUpdate();
//    }

    public void associateSchool(Collection<School> allSchools) {
        Iterator<Student> iterStudent = getAllStudents().iterator();
        Iterator<School> iterSchool = allSchools.iterator();
                
        while(iterStudent.hasNext()) {
            Student nextStudent = iterStudent.next();
            if(iterSchool.hasNext()) {
                School nextSchool = iterSchool.next();
                nextStudent.setSchool(nextSchool);
            } else {
                iterSchool = allSchools.iterator();
            }
        }
    }
}
