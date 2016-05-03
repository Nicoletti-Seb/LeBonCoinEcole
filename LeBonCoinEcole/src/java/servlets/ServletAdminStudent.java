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
 * @author LeoPaul
 */
@WebServlet(name = "ServletAdminStudent", urlPatterns = {"/admin/students"})
public class ServletAdminStudent extends HttpServlet {
    
    private static final int NB_MAX_STUDENT = 20;
    
    @EJB
    UsersManager um;
    
    @EJB
    private SchoolsManager sm;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        if (session.getAttribute("administrator") != null) {
            int page = 0;
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
            int off = (page - 1) * NB_MAX_STUDENT;
            
            Collection<Student> allStudents = um.getAllStudents(off, NB_MAX_STUDENT);
            request.setAttribute("allStudents", allStudents);
            
            long nbElement = um.countStudents();
            
            //Nb pages
            int nbPages = (int) nbElement / NB_MAX_STUDENT;
            if (nbElement % NB_MAX_STUDENT > 0) {
                nbPages++;
            }
            request.setAttribute("nbPages", nbPages);
            
            Collection<School> allSchools = sm.getAllSchools();
            request.setAttribute("allSchools", allSchools);
            
            RequestDispatcher dp = request.getRequestDispatcher("/adminStudents.jsp?page="+page);
            dp.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if("delete".equals(action)) {
            String username = request.getParameter("username");
            
            um.deleteStudent(username);
        }
        
        response.sendRedirect(request.getContextPath()+"/admin/students");
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
