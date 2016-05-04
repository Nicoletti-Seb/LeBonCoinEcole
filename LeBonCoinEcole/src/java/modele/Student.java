/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.DatatypeConverter;
import utils.RandomBuild;

@Entity
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String lastname;

    private String firstname;

    private String username;

    private String password;

    @ManyToOne
    private School school;

    @OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<PhoneNumber> phoneNumbers;

    @OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Email> emails;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @OneToMany(mappedBy = "student", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Announcement> announcements;

    public Student() {

    }

    public Student(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = "" + lastname.charAt(0) + firstname.charAt(0) + RandomBuild.number(5);
        this.password = "pass";
    }

    public Student(String lastname, String firstname, String username, String password,
            School school, List<PhoneNumber> phoneNumbers,
            List<Email> emails, byte[] image) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.password = password;
        this.school = school;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
    public List<Announcement> getAnnouncements(int off, int nb, boolean isAnnouncement) {
        List<Announcement> result = new ArrayList<>();
        for( int i = off; i < announcements.size() && result.size() < nb; i++ ){
        
            if( announcements.get(i).isAnnoucement() == isAnnouncement ){
                result.add(announcements.get(i));
            }
        }
        return result;
    }
    
    public int countAnnouncement(boolean isAnnouncement){
        int count = 0;
        
        for( Announcement a : announcements ){
            if( a.isAnnoucement() == isAnnouncement ){
                count++;
            }
        }
        
        return count;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
    
    public String getUrl(){
        if( image == null || image.length == 0){
            return "//static.leboncoin.fr/img/no-picture.png";
        }
        
        // link : pour remplacer Base64
        // http://stackoverflow.com/questions/14413169/which-java-library-provides-base64-encoding-decoding
        return "data:image/png;base64," + DatatypeConverter.printBase64Binary(image);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "modele.users.User[ id=" + id + " ]";
    }

}
