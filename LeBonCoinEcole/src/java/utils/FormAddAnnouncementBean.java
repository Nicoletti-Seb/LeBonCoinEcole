/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.List;
import modele.Category;

public class FormAddAnnouncementBean {
    
    private List<Category> categories;
    private String title;
    private String description;
    private float price;
    private byte[] image;

    public FormAddAnnouncementBean() {
        categories = new ArrayList<>();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = ( categories != null )? categories : new ArrayList<Category>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = ( title != null )? title : "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = ( description != null )? description : "";
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = ( price > 0)? price : 0;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
