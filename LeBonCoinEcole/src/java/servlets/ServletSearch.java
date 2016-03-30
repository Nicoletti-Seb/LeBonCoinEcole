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
import manager.AnnouncementsManager;
import manager.CategoriesManager;
import manager.SchoolsManager;
import modele.Announcement;
import modele.Category;
import modele.School;

/**
 *
 * @author Seb
 */
@WebServlet(name = "ServletSearch", urlPatterns = {"/search"})
public class ServletSearch extends HttpServlet {
    
    private static final int NB_MAX_ANNOUNCEMENT = 10;
    
    @EJB
    private CategoriesManager cm;

    @EJB
    private AnnouncementsManager am;
    
    @EJB
    private SchoolsManager sm;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Collection<Category> categories = cm.getAllCategories();
        request.setAttribute("categories", categories);
        
        int page = ( request.getParameter("page") != null )? Integer.parseInt(request.getParameter("page")) : 1;
        Collection<Announcement> announcements = am.getAnnouncements((page-1) * NB_MAX_ANNOUNCEMENT, NB_MAX_ANNOUNCEMENT);
        long nbElement = am.countAnnouncements();
        int nbPages = (int) nbElement / NB_MAX_ANNOUNCEMENT;
        if( nbElement % NB_MAX_ANNOUNCEMENT > 0){
            nbPages++;
        }
        request.setAttribute("announcements", announcements);
        request.setAttribute("nbPages", nbPages);
        
        Collection<School> schools = sm.getAllSchools();
        request.setAttribute("schools", schools);
        
        RequestDispatcher dp = request.getRequestDispatcher("search.jsp?page=" + page);
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
