/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.UsersManager;
import modele.Student;

/**
 *
 * @author LeoPaul
 */
@WebServlet(name = "ServletAdminStudent", urlPatterns = {"/admin/students"})
public class ServletAdminStudent extends HttpServlet {
    
    @EJB
    UsersManager um;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        if (session.getAttribute("administrator") != null) {
            Collection<Student> allStudents = um.getAllStudents(0);
            request.setAttribute("allStudents", allStudents);
            
            RequestDispatcher dp = request.getRequestDispatcher("/adminStudents.jsp");
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
