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
import manager.AnnouncementsManager;
import manager.CategoriesManager;
import modele.Announcement;
import modele.Category;

/**
 *
 * @author Seb
 */
@WebServlet(name = "ServletIndex", urlPatterns = {"", "/index"})
public class ServletIndex extends HttpServlet {
    
    private static final int NB_MAX_ANNOUNCEMENT = 10;
    
    private final List<Category> categoriesSelected = new ArrayList<>();
    
    @EJB
    private CategoriesManager cm;

    @EJB
    private AnnouncementsManager am;
    
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
        
        updateCategoriesSelected(request.getParameter("category"));
        request.setAttribute("categoriesSelected", categoriesSelected);
        
        Collection<Category> categories = cm.getAllCategories();
        request.setAttribute("categories", categories);
        
        Collection<Announcement> announcements = am.getAnnouncements(0, NB_MAX_ANNOUNCEMENT);
        request.setAttribute("announcements", announcements);
        
        
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
    
    public void updateCategoriesSelected(String categoryName){
        if( categoryName == null || categoryName.isEmpty() ){
            return;
        }
        
        Category category = cm.getCategory(categoryName);
        if( category == null ){
            return;
        }
        
        if( categoriesSelected.contains(category) ){
            categoriesSelected.remove(category);
        }
        else{
            categoriesSelected.add(category);
        }
    }
}
