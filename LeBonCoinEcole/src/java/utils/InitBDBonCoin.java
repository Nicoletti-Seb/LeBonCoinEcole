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
import manager.AnnouncementsManager;
import manager.CategoriesManager;
import manager.SchoolsManager;
import manager.UsersManager;
import modele.Address;
import modele.Announcement;
import modele.Email;
import modele.PhoneNumber;
//import manager.StudentManager;

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
        
        um.createStudentsTest(5);
        System.out.println("Initialisation de la base de donnée : Students");
        
        am.createAnnouncementsTest();
        System.out.println("Initialisation de la base de donnée : Annoucements");
        
        Address addr1 = new Address(1, "rue 1", "00001", "Nice", "France");
        Address addr2 = new Address(2, "rue 2", "00002", "Nice", "France");
        Address addr3 = new Address(3, "rue 3", "00003", "Nice", "France");
        List<Address> listAddress = new ArrayList<Address>();
        listAddress.add(addr1);
        listAddress.add(addr2);
        listAddress.add(addr3);
        
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
        
        um.createStudent("nom", "prenom", "username", "pass", null, listAddress, listPhones, listEmails, null);
    }
}
