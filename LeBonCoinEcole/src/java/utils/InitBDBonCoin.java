/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import manager.CategoriesManager;
import manager.SchoolsManager;
import manager.UsersManager;
import modele.Address;
import modele.School;
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
    
    @PostConstruct // IMPORTANT
    public void init() {
        cm.createCategoriesTest();
        System.out.println("Initialisation de la base de donnée : Category");
        
        sm.createSchoolsTest();
        System.out.println("Initialisation de la base de donnée : School");
        
        um.createStudent("toto", "toto", "toto");
        um.createStudentsTest(5);
        System.out.println("Initialisation de la base de donnée : Students");
    }
}
