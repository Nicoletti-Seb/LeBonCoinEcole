/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.UsersManager;
import modele.Announcement;
import modele.Student;

@WebServlet(name = "ServletMyAnnouncements", urlPatterns = {"/myAnnouncements"})
public class ServletMyAnnouncements extends HttpServlet {
    
    private static final int NB_MAX_ANNOUNCEMENT = 10; 
    
    @EJB
    private UsersManager um;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // the user not connected
        if( session.getAttribute("student") == null ){
            RequestDispatcher dp = request.getRequestDispatcher("index.jsp");
            dp.forward(request, response);
            return;
        }
        
        //the page ask
        int page = 1;
        if( request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }

        // Search user's announcement
        Student student = um.synchronised( (Student) session.getAttribute("student") ) ;
        
        List<Announcement> announcements = student.getAnnouncements( (page-1) * NB_MAX_ANNOUNCEMENT, NB_MAX_ANNOUNCEMENT);
        request.setAttribute("announcements", announcements);
        
        //Nb pages
        int nbAnnoucements = student.getAnnouncements().size() ;
        int nbPage = nbAnnoucements / NB_MAX_ANNOUNCEMENT;
        if( nbAnnoucements % NB_MAX_ANNOUNCEMENT >  0){
            nbPage++;
        }
        request.setAttribute("nbPages", nbPage);
        
        //Send
        RequestDispatcher dp = request.getRequestDispatcher("myAnnouncements.jsp?page="+page);
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
        return "Return user's announcements";
    }// </editor-fold>

}
