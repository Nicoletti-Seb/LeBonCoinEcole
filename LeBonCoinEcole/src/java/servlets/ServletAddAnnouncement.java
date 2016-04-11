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
import javax.servlet.http.HttpSession;
import manager.AnnouncementsManager;
import manager.CategoriesManager;
import modele.Category;
import modele.Student;
import utils.FormAddAnnouncementBean;

@WebServlet(name = "ServletAddAnnouncement", urlPatterns = {"/addAnnouncement"})
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
        
        if ("create".equals(request.getParameter("action"))) {
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
    private void updateFormValue(HttpServletRequest request) {
        HttpSession session = request.getSession();
        FormAddAnnouncementBean faab = (FormAddAnnouncementBean) session.getAttribute("formAddAnnouncementBean");

        faab.setTitle(request.getParameter("title"));
        faab.setDescription(request.getParameter("description"));

        if (request.getParameter("price") != null) {
            try {
                faab.setPrice(Float.parseFloat(request.getParameter("price")));
            } catch (NumberFormatException e) {
                faab.setPrice(0f);
            }
        }

        if (request.getParameterValues("categories") != null) {
            List<Category> categories = new ArrayList<>();

            for (String name : request.getParameterValues("categories")) {
                categories.add(cm.getCategory(name));
            }
            faab.setCategories(categories);
        } else {
            faab.setCategories(null);
        }

        if (request.getParameter("image") != null) {
            //TODO
        }
    }
}
