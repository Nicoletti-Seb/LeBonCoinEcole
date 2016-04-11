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
import modele.Address;
import modele.Email;
import modele.PhoneNumber;
import modele.School;
import modele.Student;

/**
 *
 * @author Seb
 */
@WebServlet(name = "ServletUser", urlPatterns = {"/MonCompte"})
public class ServletUser extends HttpServlet {

    @EJB
    private UsersManager um;

    @EJB
    private SchoolsManager sm;

    /*
     * 3 pages :
     * - Création de compte
     * - Afficher les informations du comptes
     * - Modification des informations du comptes
     *
     * Sinon redirection vers la page de connexion/création de compte
     */
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

        RequestDispatcher dp = request.getRequestDispatcher("moncompte.jsp");
        dp.forward(request, response);
    }

    /*
     * 4 acitons :
     * - connexion
     * - deconnexion
     * - createStudent
     * - updateStudent
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("connexion".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (session.getAttributeNames() != null) {
                if (um.isStudent(username, password)) {
                    Student s = um.lookingByUsername(username);
                    session.setAttribute("student", s);

                    // redirection apres la connection
                    response.sendRedirect(request.getContextPath());
                } else {
                    // redirection apres la connection
                    response.sendRedirect(request.getContextPath() + "?alert=error");
                }
            }
        } else if ("deconnexion".equals(action)) {
            request.getSession().invalidate();

            // redirection apres la deconnexion
            response.sendRedirect(request.getContextPath());
        } else if ("create".equals(action)) {
            // profil information
            String lastname = request.getParameter("lastname");
            String firstname = request.getParameter("firstname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // school
            String idSchoolString = request.getParameter("school");
            int idSchool = -1;
            if (!idSchoolString.isEmpty()) {
                idSchool = Integer.parseInt(idSchoolString);
            }
            School school = sm.getSchool(idSchool);

            // address
            List<Address> listAddress = new ArrayList<Address>();
            String numberString = request.getParameter("addr-number");
            int number = 0;
            if (!numberString.isEmpty()) {
                number = Integer.parseInt(numberString);
            }
            String name = request.getParameter("addr-name");
            String areaCode = request.getParameter("addr-areaCode");
            String city = request.getParameter("addr-city");
            String country = request.getParameter("addr-country");
            if (!name.isEmpty() && !areaCode.isEmpty() && !city.isEmpty() && !country.isEmpty()) {
                Address address = new Address(number, name, areaCode, city, country);
                listAddress.add(address);
            }

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
            um.createStudent(lastname, firstname, username, password, school, listAddress, listPhones, listEmails, image);

            response.sendRedirect(request.getContextPath());
        } else if ("update".equals(action)) {
            // profil information
            String lastname = request.getParameter("lastname");
            String firstname = request.getParameter("firstname");
            Student student = (Student) session.getAttribute("student");
            String username = student.getUsername();
            String password = request.getParameter("password");

            // school
            String idSchoolString = request.getParameter("school");
            int idSchool = -1;
            if (!idSchoolString.isEmpty()) {
                idSchool = Integer.parseInt(idSchoolString);
            }
            School school = sm.getSchool(idSchool);

            // address
            List<Address> listAddress = new ArrayList<Address>();
            String[] numbers = request.getParameterValues("addr-number");
            String[] names = request.getParameterValues("addr-name");
            String[] areaCodes = request.getParameterValues("addr-areaCode");
            String[] cities = request.getParameterValues("addr-city");
            String[] countries = request.getParameterValues("addr-country");

            int nbAddress = names.length;
            for (int i = 0; i < nbAddress; i++) {
                if(names[i].isEmpty()) continue;
                
                int number = 0;
                if (!numbers[i].isEmpty()) {
                    number = Integer.parseInt(numbers[i]);
                }
                Address address = new Address(number, names[i], areaCodes[i], cities[i], countries[i]);
                listAddress.add(address);
            }

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
            Student updateStudent = um.updateStudent(lastname, firstname, username, password, school, listAddress, listPhones, listEmails, null);

            // updateSession
            session.setAttribute("student", updateStudent);

            response.sendRedirect(request.getContextPath() + "/MonCompte");
        }
    }

}
