/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.AdminManager;
import manager.SchoolsManager;
import manager.UsersManager;
import modele.Administrator;
import modele.Student;

/**
 *
 * @author Seb
 */
@WebServlet(name = "ServletUser", urlPatterns = {"/connexion"})
public class ServletUser extends HttpServlet {

    @EJB
    private UsersManager um;
    
    @EJB
    AdminManager adm;

    @EJB
    private SchoolsManager sm;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath());
    }

    /*
     * 4 acitons :
     * - connexion
     * - deconnexion
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("connexion".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (session.getAttribute("student") == null &&
                    session.getAttribute("administrator") == null) {
                if (um.isStudent(username, password)) {
                    // connexion en tant que user
                    Student s = um.lookingByUsername(username);
                    session.setAttribute("student", s);
                    session.setMaxInactiveInterval(1800);

                    // redirection apres la connection
                    response.sendRedirect(request.getContextPath());
                } else if(adm.isAdmin(username, password)){
                    // connexion en tant qu'admin
                    Administrator a = adm.lookingByUsername(username);
                    session.setAttribute("administrator", a);
                    session.setMaxInactiveInterval(1800);
                    
                    // redirection apres la connection
                    response.sendRedirect(request.getContextPath());
                } else {
                    // fail de la connexion
                    response.sendRedirect(request.getContextPath() + "?alert=error");
                }
            }
        } else if ("deconnexion".equals(action)) {
            session.invalidate();

            // redirection apres la deconnexion
            response.sendRedirect(request.getContextPath() + "?alert=deconnexion");
        }
    }
}
