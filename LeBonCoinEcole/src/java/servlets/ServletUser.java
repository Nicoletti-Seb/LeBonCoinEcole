/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.SchoolsManager;
import manager.UsersManager;
import modele.School;
import modele.Student;

/**
 *
 * @author Seb
 */
@WebServlet(name = "ServletUser", urlPatterns = {"/MonCompte"})
public class ServletUser extends HttpServlet {

    @EJB
    private UsersManager um;
    
    @EJB
    private SchoolsManager sm;
    
    /*
    * 3 pages possibles :
    * - Création de compte
    * - Afficher les informations du comptes
    * - Modification des informations du comptes
    *
    * Sinon redirection vers la page de connexion/création de compte
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verifier session avant d'autoriser le changement de page et d'action
        HttpSession session = request.getSession();
        if (session.getAttribute("student") == null) {
            request.setAttribute("action", "formCreationComtpe");
            
            Collection<School> allSchools = sm.getAllSchools();
            request.setAttribute("allSchools", allSchools);
        } else {
            request.setAttribute("action", "displayInfoCompte");
            
            Student student = (Student) session.getAttribute("student");
            request.setAttribute("student", student);
        }
        
        RequestDispatcher dp = request.getRequestDispatcher("moncompte.jsp");
        dp.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
        String action = request.getParameter("action");
        
        if("connexion".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            HttpSession session = request.getSession();
            if(session.getAttributeNames() != null) {
                if(um.isStudent(username, password)) {
                    Student s = um.lookingByUsername(username);
                    session.setAttribute("student", s);

                    // redirection apres la connection
                    response.sendRedirect(request.getContextPath());
                } else {
                    // redirection apres la connection
                    response.sendRedirect(request.getContextPath() + "?alert=error");
                }
            }
        } else if ("deconnexion".equals(action)) { 
            request.getSession().invalidate();
            
            // redirection apres la deconnexion
            response.sendRedirect(request.getContextPath());
        }
    }

}
