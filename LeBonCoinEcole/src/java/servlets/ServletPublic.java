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
import manager.CategoriesManager;
import manager.SchoolsManager;
import manager.UsersManager;
import modele.Category;
import modele.School;
import modele.Student;

/**
 *
 * @author Seb
 */
@WebServlet(name = "ServletPublic", urlPatterns = {"/ServletPublic"})
public class ServletPublic extends HttpServlet {

    @EJB
    private CategoriesManager cm;
    
    @EJB
    private UsersManager um;
    
    @EJB
    private SchoolsManager sm;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Collection<Category> allCategories = cm.getAllCategories();
        request.setAttribute("allCategories", allCategories);

        Collection<Student> allStudents = um.getAllStudents(0);
        request.setAttribute("allStudents", allStudents);

        Collection<School> allSchools = sm.getAllSchools();
        request.setAttribute("allSchools", allSchools);
        
        RequestDispatcher dp = request.getRequestDispatcher("index.jsp");
        dp.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
