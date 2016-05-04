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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import manager.AnnouncementsManager;
import manager.CategoriesManager;
import manager.UsersManager;
import modele.Announcement;
import modele.Category;
import modele.Student;
import utils.FormAddAnnouncementBean;
import utils.ReaderParts;

@WebServlet(name = "ServletAddAnnouncement", urlPatterns = {"/addAnnouncement"})
@MultipartConfig
public class ServletAddAnnouncement extends HttpServlet {

    @EJB
    private CategoriesManager cm;
    
    @EJB
    private AnnouncementsManager am;
    
    @EJB
    private UsersManager um;
    

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
        if (session.getAttribute("formAddAnnouncementBean") == null) {
            session.setAttribute("formAddAnnouncementBean", new FormAddAnnouncementBean());
        }
        request.setAttribute("categories", cm.getAllCategories());
        
        //Update Announcement
        String param = "";  
        if( "update".equals(request.getParameter("action")) ){
            int id = Integer.parseInt(request.getParameter("id"));
            Announcement announcement = am.getAnnouncement(id);

            //Value to fill out the form
            FormAddAnnouncementBean faab = new FormAddAnnouncementBean();
            faab.setCategories(announcement.getCategories());
            faab.setTitle(announcement.getTitle());
            faab.setDescription(announcement.getDescription());
            faab.setPrice(announcement.getPrice());
            faab.setImage(announcement.getImage());
            request.setAttribute("vf", faab);
            
            param = "?action=update&id=" + id;  
        }
        
        RequestDispatcher dp = request.getRequestDispatcher("addAnnouncement.jsp"+param);
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

        String action = ReaderParts.readString(request.getPart("action"));
        
        if ("create".equals(action)) {
            createAnnouncement(request, response);
        }
        else if ("update".equals(action)) {
            updateAnnouncement(request, response);
        }
    }
    
    /**
     * Send the page to create an announcement
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void createAnnouncement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        StringBuilder params = new StringBuilder();
        HttpSession session = request.getSession();
        Student student = um.synchronised((Student) session.getAttribute("student"));

        FormAddAnnouncementBean faab = updateFormValue(request);

        if (am.createAnnouncement(student, faab.getTitle(), faab.getDescription(),
                faab.getPrice(), faab.getCategories(), faab.getImage()) != null) {
            request.setAttribute("success", true);
            params.append("?success=true");
        } else {
            request.setAttribute("error", true);
            params.append("?error=true");
        }

        params.append("&title=").append(faab.getTitle());
        response.sendRedirect(request.getContextPath() + "/addAnnouncement" + params);
    }
    
    /**
     * Update an announcement
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void updateAnnouncement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();
        
        //Get new values
        FormAddAnnouncementBean faab = updateFormValue(request);
        
        //Update Announcement
        int idAnnouncement = Integer.parseInt(request.getParameter("id"));
        am.update(idAnnouncement, faab.getTitle(), faab.getDescription(), 
                faab.getPrice(), faab.getCategories(), faab.getImage());
        
        response.sendRedirect(request.getContextPath() + "/announcement?id=" + idAnnouncement);
    }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Return a form to add an announcement";
    }// </editor-fold>

    /**
     * Update form values send by the user.
     *
     * @param request
     */
    private FormAddAnnouncementBean updateFormValue(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        FormAddAnnouncementBean faab = (FormAddAnnouncementBean) session.getAttribute("formAddAnnouncementBean");
       
        List<Category> categoryList = new ArrayList<Category>();
        for( Part part : request.getParts() ){
            
            if( "title".equals(part.getName()) ){
                faab.setTitle(ReaderParts.readString(part));
            }
            else if( "categories".equals(part.getName()) ){
                categoryList.add(cm.getCategory(ReaderParts.readString(part)));
            }
            else if( "description".equals(part.getName()) ){
                faab.setDescription(ReaderParts.readString(part));
            }
            else if( "price".equals(part.getName()) ){
                faab.setPrice(ReaderParts.readNumbers(part));
            }
            else if( "image".equals(part.getName()) ){
                faab.setImage(ReaderParts.readByteArray(part));
            }
        }
        
        faab.setCategories(categoryList);
        
        return faab;
    }
}
