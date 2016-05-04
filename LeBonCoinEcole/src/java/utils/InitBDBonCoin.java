/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Collection;
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
import modele.Category;
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
        
        um.createStudentsMiageTest();
        um.createStudentsTest(500);
        System.out.println("Initialisation de la base de donnée : Students");
        
        Collection<School> allSchools = sm.getAllSchools();
        um.associateSchool(allSchools);
        System.out.println("Initialisation de la base de donnée : Link Students - School");
        
        adm.createAdministratorTest();
        System.out.println("Initialisation de la base de donnée : Administrateurs");
        
        School school = sm.createSchool("B", new Address("46 rue de la boustifaille", "06600", "Nice", "France"), "www.google.fr");
        um.createStudentTest(school);
        
        /*Collection<Category> allCategories = cm.getAllCategories();
        am.createAnnouncementsTest(1500, allCategories);
        System.out.println("Initialisation de la base de donnée : Annoucements");
        
        Collection<Student> allStudents = um.getAllStudents();
        am.associateStudent(allStudents);
        System.out.println("Initialisation de la base de donnée : Link Students - School");*/
    }
}
