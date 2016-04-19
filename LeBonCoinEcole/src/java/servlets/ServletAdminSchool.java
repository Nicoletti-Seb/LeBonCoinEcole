/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.SchoolsManager;
import modele.Address;
import modele.School;

/**
 *
 * @author LeoPaul
 */
@WebServlet(name = "ServletAdminSchool", urlPatterns = {"/admin/schools"})
public class ServletAdminSchool extends HttpServlet {

    @EJB
    SchoolsManager sm;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("administrator") != null) {
            Collection<School> allSchools = sm.getAllSchools();
            request.setAttribute("allSchools", allSchools);

            RequestDispatcher dp = request.getRequestDispatcher("/adminSchools.jsp");
            dp.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String name = request.getParameter("name");
            String link = request.getParameter("link");
            String adrName = request.getParameter("address");
            String areaCode = request.getParameter("areaCode");
            String city = request.getParameter("city");
            String country = request.getParameter("country");

            Address address = new Address(0, adrName, areaCode, city, country);
            sm.createSchool(name, address, link);
        } else if("delete".equals(action)) {
            String idString = request.getParameter("id");
            int id = -1;
            if(idString != null && !idString.isEmpty()) {
                id = Integer.parseInt(idString);
                
                sm.deleteSchool(id);
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/schools");
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
