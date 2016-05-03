/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import manager.AdminManager;
import manager.AnnouncementsManager;
import manager.CategoriesManager;
import manager.SchoolsManager;
import manager.UsersManager;
import modele.Address;
import modele.Email;
import modele.PhoneNumber;
import modele.School;
import modele.Student;

/**
 * Pour initialiser la base de données au déploiement
 * 
 * 1) Ajouter au projet un SessionBean InitBD dans le package Utils -> type Singleton
 * 2) Ajouter l'annotation @Startup sous @Singleton
 * 3) Ajouter une méthode public void init() {gu.creerDonneesDeTest() }
 * 
 * @author LeoPaul
 */
@Singleton // un seul de créé
@Startup // Indique qu'il sera crée au démarrage de l'application
public class InitBDBonCoin {

    @EJB
    UsersManager um;
    
    @EJB
    AdminManager adm;
    
    @EJB
    CategoriesManager cm;
    
    @EJB
    SchoolsManager sm;
    
    @EJB
    AnnouncementsManager am;
    
    @PostConstruct // IMPORTANT
    public void init() {
        cm.createCategoriesTest();
        System.out.println("Initialisation de la base de donnée : Category");
        
        sm.createSchoolsTest();
        System.out.println("Initialisation de la base de donnée : School");
        
        um.createStudentsTest(25);
        System.out.println("Initialisation de la base de donnée : Students");
        
        adm.createAdministratorTest();
        
        am.createAnnouncementsTest();
        System.out.println("Initialisation de la base de donnée : Annoucements");
        
        PhoneNumber phone1 = new PhoneNumber("123456789");
        PhoneNumber phone2 = new PhoneNumber("987654321");
        List<PhoneNumber> listPhones = new ArrayList<PhoneNumber>();
        listPhones.add(phone1);
        listPhones.add(phone2);
        
        Email email1 = new Email("toto1@toto.fr");
        Email email2 = new Email("toto2@toto.fr");
        List<Email> listEmails = new ArrayList<Email>();
        listEmails.add(email1);
        listEmails.add(email2);
        
        School school = sm.createSchool("B", new Address(30, "rue de la boustifaille", "06600", "Nice", "France"), "www.google.fr");
        
        Student student = um.createStudent("nom", "prenom", "username", "pass", null, listPhones, listEmails, null);
    }
}
