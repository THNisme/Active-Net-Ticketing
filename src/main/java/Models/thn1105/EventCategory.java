/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.thn1105;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class EventCategory {
    private int categoryID;
    private String categoryName;
    private String description;
    private int statusID;
    
    public EventCategory() {
    }

    public EventCategory(int categoryID, String categoryName, String description, int statusID) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.description = description;
        this.statusID = statusID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

   
    
}
