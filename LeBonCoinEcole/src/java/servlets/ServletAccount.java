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
import manager.SchoolsManager;
import manager.UsersManager;
import modele.Email;
import modele.PhoneNumber;
import modele.School;
import modele.Student;

/**
 *
 * @author LeoPaul
 */
@WebServlet(name = "ServletAccount", urlPatterns = {"/account"})
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
        
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // identifier si le student n'existe pas deja
            String username = request.getParameter("username");
            Student lookingByUsername = um.lookingByUsername(username);
            
            // s'il existe deja, on annule la creation et on redirige sur l'index
            if(lookingByUsername != null) {
                response.sendRedirect(request.getContextPath());
                return;
            }
            
            // profil information
            String lastname = request.getParameter("lastname");
            String firstname = request.getParameter("firstname");
            String password = request.getParameter("password");

            // school
            String idSchoolString = request.getParameter("school");
            int idSchool = -1;
            if (!idSchoolString.isEmpty()) {
                idSchool = Integer.parseInt(idSchoolString);
            }
            School school = sm.getSchool(idSchool);

            // phone
            String phoneString = request.getParameter("phone");
            List<PhoneNumber> listPhones = new ArrayList<PhoneNumber>();
            if (phoneString != null && !phoneString.isEmpty()) {
                PhoneNumber phoneNumber = new PhoneNumber(phoneString);
                listPhones.add(phoneNumber);
            }

            // email
            String emailString = request.getParameter("email");
            List<Email> listEmails = new ArrayList<Email>();
            if (emailString != null && !emailString.isEmpty()) {
                Email email = new Email(emailString);
                listEmails.add(email);
            }
            byte[] image = null;
            
            // createStudent
            um.createStudent(lastname, firstname, username, password, school, listPhones, listEmails, image);

            response.sendRedirect(request.getContextPath());
        } else if ("update".equals(action)) {
            // identifier le student qui modifie
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute("student");
            String username = student.getUsername();
            
            // profil information
            String lastname = request.getParameter("lastname");
            String firstname = request.getParameter("firstname");
            String password = request.getParameter("password");

            // school
            String idSchoolString = request.getParameter("school");
            int idSchool = -1;
            if (!idSchoolString.isEmpty()) {
                idSchool = Integer.parseInt(idSchoolString);
            }
            School school = sm.getSchool(idSchool);

            // phone
            String[] phoneStringTab = request.getParameterValues("phone");
            List<PhoneNumber> listPhones = new ArrayList<PhoneNumber>();
            if (phoneStringTab != null) {
                for (String phoneString : phoneStringTab) {
                    if (!phoneString.isEmpty()) {
                        PhoneNumber phoneNumber = new PhoneNumber(phoneString);
                        listPhones.add(phoneNumber);
                    }
                }
            }

            // email
            String[] emailStringTab = request.getParameterValues("email");
            List<Email> listEmails = new ArrayList<Email>();
            if (emailStringTab != null) {
                for (String emailString : emailStringTab) {
                    if (!emailString.isEmpty()) {
                        Email email = new Email(emailString);
                        listEmails.add(email);
                    }
                }
            }
            byte[] image = null;

            // updateStudent
            Student updateStudent = um.updateStudent(lastname, firstname, username, password, school, listPhones, listEmails, null);

            // updateSession
            session.setAttribute("student", updateStudent);

            response.sendRedirect(request.getContextPath() + "/account");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
