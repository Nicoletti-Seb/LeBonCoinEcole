/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.List;
import modele.Email;
import modele.PhoneNumber;

public class AccountBean {
    
    private String username;
    private String lastname;
    private String firstname;
    private String password;
    private List<PhoneNumber> phones;
    private List<Email> emails;
    private int idSchool;
    private byte[] image;

    public AccountBean() {
        phones = new ArrayList<>();
        emails = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = ( username != null )? username : "";
    }

    public int getIdSchool() {
        return idSchool;
    }

    public void setIdSchool(int idSchool) {
        this.idSchool = idSchool;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = ( lastname != null )? lastname : "";
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = ( firstname != null )? firstname : "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = ( password != null )? password : "";
    }

    public List<PhoneNumber> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneNumber> phones) {
        this.phones = ( phones != null )? phones : new ArrayList<>();
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = ( emails != null )? emails : new ArrayList<>();
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
