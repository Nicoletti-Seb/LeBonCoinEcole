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
import modele.Category;

/**
 *
 * @author LeoPaul
 */
@Stateless
public class CategoriesManager {
    
    @PersistenceContext
    private EntityManager em;
        
    public void createCategoriesTest() {
        createCategory("Offre d'emploi");
        createCategory("Voitures");
        createCategory("Motos");
        createCategory("Caravaning");
        createCategory("Utilitaires");
        createCategory("Equipement Auto");
        createCategory("Equipement Moto");
        createCategory("Equipement Caravaning");
        createCategory("Nautisme");
        createCategory("Equipement Nautisme");
        createCategory("Ventes immobilières");
        createCategory("Locations");
        createCategory("Colocations");
        createCategory("Bureaux & Commerces");
        createCategory("Locations & Gîtes");
        createCategory("Chambres d'hôtes");
        createCategory("Campings");
        createCategory("Hôtels");
        createCategory("Hébergements insolites");
        createCategory("Informatique");
        createCategory("Consoles & Jeux vidéo");
        createCategory("Image & Son");
        createCategory("Téléphonie");
        createCategory("DVD / Films");
        createCategory("CD / Musique");
        createCategory("Livres");
        createCategory("Animaux");
        createCategory("Vélos");
        createCategory("Sports & Hobbies");
        createCategory("Instruments de musique");
        createCategory("Collection");
        createCategory("Jeux & Jouets");
        createCategory("Vins & Gastronomie");
        createCategory("Matériel Agricole");
        createCategory("Transport - Manutention");
        createCategory("Outillage");
        createCategory("Equipements Industriels");
        createCategory("Restauration - Hôtellerie");
        createCategory("Fournitures de Bureau");
        createCategory("Commerces & Marchés");
        createCategory("Matériel Médical");
        createCategory("Prestations de services");
        createCategory("Billetterie");
        createCategory("Evénements");
        createCategory("Cours particuliers");
        createCategory("Covoiturage");
        createCategory("Ameublement");
        createCategory("Electroménager");
        createCategory("Arts de la table");
        createCategory("Décoration");
        createCategory("Linge de maison");
        createCategory("Bricolage");
        createCategory("Jardinage");
        createCategory("Vêtements");
        createCategory("Chaussures");
        createCategory("Accessoires & Bagagerie");
        createCategory("Montres & Bijoux");
        createCategory("Equipement bébé");
        createCategory("Vêtements bébé");
        createCategory("Autres");
    }

    public Category createCategory(String name) {
        Category c = new Category(name);
        em.persist(c);
        em.flush();
        return c;
    }
    
    public Collection<Category> getAllCategories() {
        Query q = em.createQuery("select c from Category c order by c.name ASC");
        return q.getResultList();
    }
    
    public int deleteCategory(String name) {
        Query q = em.createQuery("delete from Category c where c.name=:name");
        q.setParameter("name", name);
        return q.executeUpdate();
    }
    
    public Category getCategory(String name) {
        Query q = em.createQuery("select c from Category c WHERE c.name=:name");
        q.setParameter("name", name);
        try {
            return (Category)q.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
}
