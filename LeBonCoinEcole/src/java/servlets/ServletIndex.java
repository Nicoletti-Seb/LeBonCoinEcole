/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.AnnouncementsManager;
import manager.CategoriesManager;
import modele.Announcement;

@WebServlet(name = "ServletIndex", urlPatterns = {"", "/index"})
public class ServletIndex extends HttpServlet {

    private static final int NB_MAX_ANNOUNCEMENT = 10;

    @EJB
    private CategoriesManager cm;

    @EJB
    private AnnouncementsManager am;

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
        HttpSession session = request.getSession();
        if( session.getAttribute("categoriesSelected") == null ){
            session.setAttribute("categoriesSelected", new ArrayList<>());
        }
        updateParametersToSend(request);
        RequestDispatcher dp = request.getRequestDispatcher("index.jsp");
        dp.forward(request, response);
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

        if ("updateCategories".equals(request.getParameter("action"))) {
            updateCategoriesSelected(request.getSession(), request.getParameter("category"));
        }
        
        response.sendRedirect(request.getContextPath());
    }

    /**
     * Update parameters to send for display categories selected and
     * announcements
     */
    private void updateParametersToSend(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> categoriesSelected = (List<String>)session.getAttribute("categoriesSelected");

        //Categories
        request.setAttribute("categories", cm.getAllCategories());
        request.setAttribute("categoriesSelected", categoriesSelected);

        //Announcements
        Collection<Announcement> announcements = am.searchAnnouncements(0,
                NB_MAX_ANNOUNCEMENT, false, "", "", "", 0, 0, categoriesSelected);
        request.setAttribute("announcements", announcements);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Return the index page.";
    }// </editor-fold>

    /**
     * Update the list contains all categories selected by user.
     *
     * @param categoryName
     */

    private void updateCategoriesSelected(HttpSession session, String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return;
        }

        List<String> categoriesSelected = (List <String>)session.getAttribute("categoriesSelected");
        if (categoriesSelected.contains(categoryName)) {
            categoriesSelected.remove(categoryName);
        } else {
            categoriesSelected.add(categoryName);
        }
    }
}
