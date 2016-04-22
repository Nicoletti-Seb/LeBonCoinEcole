/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import modele.Category;
import modele.Student;
import utils.FormAddAnnouncementBean;

@WebServlet(name = "ServletAddAnnouncement", urlPatterns = {"/addAnnouncement"})
@MultipartConfig
public class ServletAddAnnouncement extends HttpServlet {

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
        if (session.getAttribute("formAddAnnouncementBean") == null) {
            session.setAttribute("formAddAnnouncementBean", new FormAddAnnouncementBean());
        }
        
        request.setAttribute("categories", cm.getAllCategories());
        RequestDispatcher dp = request.getRequestDispatcher("addAnnouncement.jsp");
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

        StringBuilder params = new StringBuilder();
        String action = "";
        
        if( request.getPart("action") != null ){
            Scanner scanf = new Scanner(request.getPart("action").getInputStream());
            action = scanf.nextLine();
        }
        
        if ("create".equals(action)) {
            HttpSession session = request.getSession();
            FormAddAnnouncementBean faab = (FormAddAnnouncementBean) session.getAttribute("formAddAnnouncementBean");
            Student student = (Student) session.getAttribute("student");
            
            updateFormValue(request);
            
            if (am.createAnnouncement(student, faab.getTitle(), faab.getDescription(),
                    faab.getPrice(), faab.getCategories(), faab.getImage()) != null) {
                request.setAttribute("success", true);
                params.append("?success=true");
            } else {
                request.setAttribute("error", true);
                params.append("?error=true");
            }

            params.append("&title=").append(faab.getTitle());
        }

        response.sendRedirect(request.getContextPath() + "/addAnnouncement" + params);
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
    private void updateFormValue(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        FormAddAnnouncementBean faab = (FormAddAnnouncementBean) session.getAttribute("formAddAnnouncementBean");
        Scanner scanf;

        scanf = new Scanner(request.getPart("title").getInputStream());
        faab.setTitle(scanf.nextLine());
        
        scanf = new Scanner(request.getPart("description").getInputStream());
        faab.setDescription(scanf.nextLine());

        scanf = new Scanner(request.getPart("price").getInputStream());
        try {
            faab.setPrice(scanf.nextFloat());
        } catch (InputMismatchException e) {
            faab.setPrice(0f);
        }
        
        if (request.getPart("categories") != null) {
            List<Category> categories = new ArrayList<>();
            scanf = new Scanner(request.getPart("categories").getInputStream());

            while( scanf.hasNextLine() ){
                categories.add(cm.getCategory(scanf.nextLine()));
            }
            faab.setCategories(categories);
        } else {
            faab.setCategories(null);
        }

        if (request.getPart("image") != null) {
            faab.setImage(readImage(request.getPart("image").getInputStream()));
        }
    }
    
    private byte[] readImage(InputStream is) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        
        while( is.available() > 0 ){
            int nb = is.read(buff);
            baos.write(buff, 0, nb);
        }
        
        return baos.toByteArray();
    }
}
