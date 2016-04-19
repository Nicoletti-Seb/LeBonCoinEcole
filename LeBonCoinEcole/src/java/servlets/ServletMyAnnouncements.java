/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modele.Announcement;
import modele.Student;

/**
 *
 * @author LeoPaul
 */
@WebServlet(name = "ServletMyAnnouncements", urlPatterns = {"/myAnnouncements"})
public class ServletMyAnnouncements extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        HttpSession session = request.getSession();
        if (session.getAttribute("student") != null) {
            Student student = (Student) session.getAttribute("student");
            
            List<Announcement> announcements = student.getAnnouncements();
            request.setAttribute("toto", student);
            //request.setAttribute("announcements", announcements);
        }
        
        RequestDispatcher dp = request.getRequestDispatcher("myAnnouncements.jsp");
        dp.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
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
