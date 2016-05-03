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
import manager.SchoolsManager;
import modele.Announcement;
import utils.SearchFormBean;

@WebServlet(name = "ServletSearch", urlPatterns = {"/search"})
public class ServletSearch extends HttpServlet {

    private static final int NB_MAX_ANNOUNCEMENT = 10;

    @EJB
    private CategoriesManager cm;

    @EJB
    private AnnouncementsManager am;

    @EJB
    private SchoolsManager sm;

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

        if (session.getAttribute("categoriesSelected") == null) {
            session.setAttribute("categoriesSelected", new ArrayList<>());
        }

        if (session.getAttribute("searchFormBean") == null) {
            session.setAttribute("searchFormBean", new SearchFormBean());
        }

        if( ! "true".equals(request.getParameter("updateCategories")) ){
            updateSearchValue(request);
        }
        
        updateParametersToSend(request);
        SearchFormBean searchFormBean = (SearchFormBean) session.getAttribute("searchFormBean");
        RequestDispatcher dp = request.getRequestDispatcher("search.jsp?page=" + searchFormBean.getPage());
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
        
        HttpSession session = request.getSession();

        if ("updateCategories".equals(request.getParameter("action"))) {
            updateCategoriesSelected(session, request.getParameter("category"));
        }

        response.sendRedirect(response.encodeRedirectURL("search?updateCategories=true"));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Return the search page";
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

        List<String> categoriesSelected = (List<String>) session.getAttribute("categoriesSelected");
        if (categoriesSelected.contains(categoryName)) {
            categoriesSelected.remove(categoryName);
        } else {
            categoriesSelected.add(categoryName);
        }
    }

    /**
     * Update parameters to send for display categories selected and
     * announcements
     */
    private void updateParametersToSend(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> categoriesSelected = (List<String>) session.getAttribute("categoriesSelected");
        SearchFormBean sfb = (SearchFormBean) session.getAttribute("searchFormBean");

        //Search announcements
        int off = (sfb.getPage() - 1) * NB_MAX_ANNOUNCEMENT;

        Collection<Announcement> announcements = am.searchAnnouncements(off,
                NB_MAX_ANNOUNCEMENT, sfb.getKey(), sfb.getSchool(), sfb.getAreaCode(), sfb.getMinPrice(), sfb.getMaxPrice(), categoriesSelected);

        long nbElement = am.countSearchAnnouncements(sfb.getKey(), sfb.getSchool(), sfb.getAreaCode(),
                sfb.getMinPrice(), sfb.getMaxPrice(), categoriesSelected);

        //Nb pages
        int nbPages = (int) nbElement / NB_MAX_ANNOUNCEMENT;
        if (nbElement % NB_MAX_ANNOUNCEMENT > 0) {
            nbPages++;
        }

        //Set parameters
        request.setAttribute("announcements", announcements);
        request.setAttribute("nbPages", nbPages);
        request.setAttribute("categories", cm.getAllCategories());
        request.setAttribute("categoriesSelected", categoriesSelected);
        request.setAttribute("schools", sm.getAllSchools());
        request.setAttribute("pv", sfb);
    }

    /**
     * Update form values send by an user.
     *
     * @param request
     */
    private void updateSearchValue(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SearchFormBean sfb = (SearchFormBean) session.getAttribute("searchFormBean");
        sfb.setSchool(request.getParameter("school"));
        sfb.setAreaCode(request.getParameter("areaCode"));
        sfb.setKey(request.getParameter("key"));

        try {
            sfb.setMinPrice(Integer.parseInt(request.getParameter("minPrice")));
        } catch (NumberFormatException e) {
            sfb.setMinPrice(0);
        }

        try {
            sfb.setMaxPrice(Integer.parseInt(request.getParameter("maxPrice")));
        } catch (NumberFormatException e) {
            sfb.setMaxPrice(0);
        }
        
        try {
            sfb.setPage(Integer.parseInt(request.getParameter("page")));
        } catch (NumberFormatException e) {
            sfb.setPage(1);
        }
    }

    private void URI(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
