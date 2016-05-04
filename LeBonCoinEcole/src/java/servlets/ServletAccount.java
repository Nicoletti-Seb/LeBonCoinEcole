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
import utils.AccountBean;
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
        AccountBean ab;
        try{
            ab = readParameters(request);
        }catch( IOException | ServletException e){
            return null;
        }

        // identifier si le student n'existe pas deja
        Student lookingByUsername = um.lookingByUsername(ab.getUsername());

        // s'il existe deja, on annule la creation et on redirige sur l'index
        if(lookingByUsername != null) {
            return null;
        }

        // school
        School school = sm.getSchool(ab.getIdSchool());

        // createStudent
        return um.createStudent(ab.getLastname(), ab.getFirstname(), ab.getUsername(),
                ab.getPassword(), school, ab.getPhones(), ab.getEmails(), ab.getImage()); 
    }
    
    /**
     * Update a student account
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private boolean updateAccount(HttpServletRequest request){
        AccountBean ab;
        try{
            ab = readParameters(request);
        }catch( IOException | ServletException e){
            return false;
        }
        
        // identifier le student qui modifie
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        String username = student.getUsername();

         // school
        School school = sm.getSchool(ab.getIdSchool());
        
        // updateStudent
        Student updateStudent = um.updateStudent(ab.getLastname(), ab.getFirstname(), username,
                ab.getPassword(), school, ab.getPhones(), ab.getEmails(), ab.getImage());

        // updateSession
        session.setAttribute("student", updateStudent);
        return true;
    }
    
    
    private AccountBean readParameters( HttpServletRequest request ) 
            throws IOException, ServletException {
    
        AccountBean accountBean = new AccountBean();
        
        List<PhoneNumber> phones = new ArrayList<>();
        List<Email> emails = new ArrayList<>();
        
        for( Part part : request.getParts() ){
            if( "lastname".equals(part.getName()) ){
                accountBean.setLastname(ReaderParts.readString(part));
            }
            else if( "firstname".equals(part.getName()) ){
                accountBean.setFirstname(ReaderParts.readString(part));
            }
            else if( "password".equals(part.getName()) ){
                accountBean.setPassword(ReaderParts.readString(part));
            }
            else if( "phone".equals(part.getName()) ){
                phones.add( new PhoneNumber( ReaderParts.readString(part) ));
            }
            else if( "email".equals(part.getName()) ){
                emails.add( new Email( ReaderParts.readString(part) ));
            }
            else if( "image".equals(part.getName()) ){
                accountBean.setImage(ReaderParts.readByteArray(part));
            }
            else if( "username".equals(part.getName()) ){
                accountBean.setUsername(ReaderParts.readString(part));
            }
            else if( "school".equals(part.getName()) ){
                accountBean.setIdSchool( (int) ReaderParts.readNumbers(part));
            }
        }
        
        accountBean.setPhones(phones);
        accountBean.setEmails(emails);
        return accountBean;
    }
}
