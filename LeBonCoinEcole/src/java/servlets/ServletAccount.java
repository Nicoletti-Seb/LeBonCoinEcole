/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
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
import manager.SchoolsManager;
import manager.UsersManager;
import modele.Email;
import modele.PhoneNumber;
import modele.School;
import modele.Student;
import utils.ReaderParts;

/**
 *
 * @author LeoPaul
 */
@WebServlet(name = "ServletAccount", urlPatterns = {"/account"})
@MultipartConfig
public class ServletAccount extends HttpServlet {

    @EJB
    private UsersManager um;

    @EJB
    private SchoolsManager sm;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        // Verifier session avant d'autoriser le changement de page et d'action
        HttpSession session = request.getSession();
        if (session.getAttribute("student") == null) {
            request.setAttribute("action", "formCreationComtpe");

            Collection<School> allSchools = sm.getAllSchools();
            request.setAttribute("allSchools", allSchools);
        } else if ("modify".equals(action)) {
            request.setAttribute("action", "formModifierComtpe");

            Student student = (Student) session.getAttribute("student");
            request.setAttribute("student", student);

            Collection<School> allSchools = sm.getAllSchools();
            request.setAttribute("allSchools", allSchools);
        } else {
            request.setAttribute("action", "displayInfoCompte");

            Student student = (Student) session.getAttribute("student");
            request.setAttribute("student", student);
        }

        RequestDispatcher dp = request.getRequestDispatcher("/account.jsp");
        dp.forward(request, response);
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Part part = request.getPart("action");
        String action = new Scanner(part.getInputStream()).nextLine();
        if ("create".equals(action)) {
            createAccount(request);
            response.sendRedirect(request.getContextPath());
        } else if ("update".equals(action)) {
            updateAccount(request);
            response.sendRedirect(request.getContextPath() + "/account");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Create a student account
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private Student createAccount(HttpServletRequest request){
        try{
            // identifier si le student n'existe pas deja
            String username = ReaderParts.readString(request.getPart("username"));
            Student lookingByUsername = um.lookingByUsername(username);

            // s'il existe deja, on annule la creation et on redirige sur l'index
            if(lookingByUsername != null) {
                return null;
            }

            // school
            String idSchoolString = ReaderParts.readString(request.getPart("school"));
            int idSchool = -1;
            if (!idSchoolString.isEmpty()) {
                idSchool = Integer.parseInt(idSchoolString);
            }
            School school = sm.getSchool(idSchool);

            // profil information
            String lastname = ReaderParts.readString(request.getPart("lastname"));
            String firstname = ReaderParts.readString(request.getPart("firstname"));
            String password = ReaderParts.readString(request.getPart("password"));
            List<PhoneNumber> listPhones = ReaderParts.readPhonesNumbers(request.getPart("phone"));
            List<Email> listEmails = ReaderParts.readEmails(request.getPart("email"));
            byte[] image = ReaderParts.readByteArray(request.getPart("image"));

            // createStudent
            return um.createStudent(lastname, firstname, username, password, school, listPhones, listEmails, image);
        
        }catch( IOException | ServletException e){
            return null;
        }
    }
    
    /**
     * Update a student account
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private boolean updateAccount(HttpServletRequest request){
        try{
            // identifier le student qui modifie
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute("student");
            String username = student.getUsername();

             // school
            String idSchoolString = ReaderParts.readString(request.getPart("school"));
            int idSchool = -1;
            if (!idSchoolString.isEmpty()) {
                idSchool = Integer.parseInt(idSchoolString);
            }
            School school = sm.getSchool(idSchool);

            // profil information
            String lastname = ReaderParts.readString(request.getPart("lastname"));
            String firstname = ReaderParts.readString(request.getPart("firstname"));
            String password = ReaderParts.readString(request.getPart("password"));
            List<PhoneNumber> listPhones = ReaderParts.readPhonesNumbers(request.getPart("phone"));
            List<Email> listEmails = ReaderParts.readEmails(request.getPart("email"));
            byte[] image = ReaderParts.readByteArray(request.getPart("image"));

            // updateStudent
            Student updateStudent = um.updateStudent(lastname, firstname, username, password, school, listPhones, listEmails, image);

            // updateSession
            session.setAttribute("student", updateStudent);
        }catch( IOException | ServletException e){
            return false;
        }
        return true;
    }
    
}
