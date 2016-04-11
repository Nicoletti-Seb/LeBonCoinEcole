/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
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
import modele.Category;

/**
 *
 * @author Seb
 */
@WebServlet(name = "ServletAddAnnouncement", urlPatterns = {"/addAnnouncement"})
public class ServletAddAnnouncement extends HttpServlet {
    
    private final List<Category> categories = new ArrayList<>();
    private String title = new String();
    private String description = new String();
    private float price;
    private byte[] image;
    
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
       
        request.setAttribute("categories", cm.getAllCategories());
        
        if( "create".equals(request.getParameter("action"))){
            update(request);
            am.createAnnouncement(this.title, this.description, this. price, this.categories, this.image);
            response.sendRedirect(request.getContextPath());
        }else{
            RequestDispatcher dp = request.getRequestDispatcher("addAnnouncement.jsp");
            dp.forward(request, response);
        }
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

    private void update(HttpServletRequest request){
        
        if( request.getParameter("title") != null ){
            this.title = request.getParameter("title");
        }
        
        if( request.getParameter("description") != null ){
            this.description = request.getParameter("description");
        }
        
        if( request.getParameter("price") != null ){
            try{
                this.price = Float.parseFloat(request.getParameter("price"));
            }catch(NumberFormatException e){
                this.price = 0f;
            }
        }
        
        if( request.getParameterValues("categories") != null ){
            this.categories.clear();
            
            for( String name : request.getParameterValues("categories") ){
                this.categories.add(cm.getCategory(name));
            }
        }
        
        if( request.getParameter("image") != null ){
            // TODO : 
        }
    }
}
