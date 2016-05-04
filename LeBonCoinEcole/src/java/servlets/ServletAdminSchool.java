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
import manager.SchoolsManager;
import modele.Address;
import modele.School;

/**
 *
 * @author LeoPaul
 */
@WebServlet(name = "ServletAdminSchool", urlPatterns = {"/admin/schools"})
public class ServletAdminSchool extends HttpServlet {

    private static final int NB_MAX_SCHOOL = 15;
    
    @EJB
    SchoolsManager sm;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        if (session.getAttribute("administrator") != null) {
            
            String action = request.getParameter("action");
            if("edit".equals(action)) {
                String idString = request.getParameter("id");
                int id = -1;
                if(!idString.isEmpty()) {
                    id = Integer.parseInt(idString);
                }
                
                School school = sm.getSchool(id);
                request.setAttribute("school", school);
                
                RequestDispatcher dp = request.getRequestDispatcher("/adminSchools.jsp");
                dp.forward(request, response);
            } else {
                int page = 0;
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1;
                }
                int off = (page - 1) * NB_MAX_SCHOOL;

                Collection<School> allSchools = sm.getAllSchools(off, NB_MAX_SCHOOL);
                request.setAttribute("allSchools", allSchools);

                long nbElement = sm.countSchools();

                //Nb pages
                int nbPages = (int) nbElement / NB_MAX_SCHOOL;
                if (nbElement % NB_MAX_SCHOOL > 0) {
                    nbPages++;
                }
                request.setAttribute("nbPages", nbPages);

                RequestDispatcher dp = request.getRequestDispatcher("/adminSchools.jsp?page="+page);            
                dp.forward(request, response);
            }
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
            float longitude = Float.parseFloat(request.getParameter("longitude"));
            float latitude = Float.parseFloat(request.getParameter("latitude"));

            Address address = new Address(adrName, areaCode, city, country, longitude, latitude);
            sm.createSchool(name, address, link);
            
            response.sendRedirect(request.getContextPath() + "/admin/schools?action=create&success=true&name="+name);
        } else if("update".equals(action)) {
            String idString = request.getParameter("id");
            int id = -1;
            if(!idString.isEmpty()) {
                id = Integer.parseInt(idString);
            }
            String name = request.getParameter("name");
            String link = request.getParameter("link");
            String adrName = request.getParameter("address");
            String areaCode = request.getParameter("areaCode");
            String city = request.getParameter("city");
            String country = request.getParameter("country");
            float longitude = Float.parseFloat(request.getParameter("longitude"));
            float latitude = Float.parseFloat(request.getParameter("latitude"));
            
            Address address = new Address(adrName, areaCode, city, country, longitude, latitude);
            sm.updateSchool(id, name, address, link);
            
            School oldSchool = sm.getSchool(id);
            name = oldSchool.getName();
            response.sendRedirect(request.getContextPath() + "/admin/schools?action=update&success=true&name="+name);
        } else if("delete".equals(action)) {
            String idString = request.getParameter("id");
            int id = -1;
            if(idString != null && !idString.isEmpty()) {
                id = Integer.parseInt(idString);
                School school = sm.getSchool(id);
                String name = school.getName();
                int res = sm.deleteSchool(id);
                
                if(res > 0) {
                    response.sendRedirect(request.getContextPath() + "/admin/schools?action=delete&success=true&name="+name);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/schools?action=delete&error=true&name="+name);
                }
            }
        }
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
