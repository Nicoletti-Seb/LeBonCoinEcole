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
import manager.CategoriesManager;
import modele.Category;

/**
 *
 * @author LeoPaul
 */
@WebServlet(name = "ServletAdminCategory", urlPatterns = {"/admin/categories"})
public class ServletAdminCategory extends HttpServlet {

    @EJB
    CategoriesManager cm;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        if (session.getAttribute("administrator") != null) {
            Collection<Category> allCategories = cm.getAllCategories();
            request.setAttribute("allCategories", allCategories);

            RequestDispatcher dp = request.getRequestDispatcher("/adminCategories.jsp");
            dp.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String forwardTo = "";
        
        if ("create".equals(action)) {
            String name = request.getParameter("name");

            Category category = cm.getCategory(name);

            if (category == null) {
                cm.createCategory(name);
                
                forwardTo = "?action=create&success=true&name="+name;
            } else {
                forwardTo = "?action=create&error=true&name="+name;
            }
        } else if ("delete".equals(action)) {
            String name = request.getParameter("name");

            int res = cm.deleteCategory(name);
            if(res > 0) {
                forwardTo = "?action=delete&success=true&name="+name;
            } else {
                forwardTo = "?action=delete&error=true&name="+name;
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/categories" + forwardTo);
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
