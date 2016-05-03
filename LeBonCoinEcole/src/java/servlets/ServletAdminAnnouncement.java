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
import manager.AnnouncementsManager;
import modele.Announcement;

/**
 *
 * @author LeoPaul
 */
@WebServlet(name = "ServletAdminAnnouncement", urlPatterns = {"/admin/announcements"})
public class ServletAdminAnnouncement extends HttpServlet {

    private static final int NB_MAX_ANNOUNCEMENT = 25;
    
    @EJB
    private AnnouncementsManager am;
   
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
            int off = (page - 1) * NB_MAX_ANNOUNCEMENT;
            
            Collection<Announcement> allAnnouncements = am.getAnnouncements(off, NB_MAX_ANNOUNCEMENT);
            request.setAttribute("allAnnouncements", allAnnouncements);
            
            long nbElement = am.countAnnouncements();
            
            //Nb pages
            int nbPages = (int) nbElement / NB_MAX_ANNOUNCEMENT;
            if (nbElement % NB_MAX_ANNOUNCEMENT > 0) {
                nbPages++;
            }
            request.setAttribute("nbPages", nbPages);
            
            RequestDispatcher dp = request.getRequestDispatcher("/adminAnnouncements.jsp?page="+page);
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
            String idString = request.getParameter("id");
            int id = -1;
            if(idString != null && !idString.isEmpty()) {
                id = Integer.parseInt(idString);
                
                am.deleteAnnouncement(id);
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/announcements");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
