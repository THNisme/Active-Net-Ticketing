/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nvd2306;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author NguyenDuc
 */
public class Event {

    private int eventID;
    private int categoryID;
    private String eventName;
    private String description;
    private String imageURL;
    private Timestamp startDate;
    private Timestamp endDate;
    private int placeID;
    private int statusID;

    public Event() {
    }

    public Event(int eventID, int categoryID, String eventName, String description,
            String imageURL, Timestamp startDate, Timestamp endDate,
            int placeID, int statusID) {
        this.eventID = eventID;
        this.categoryID = categoryID;
        this.eventName = eventName;
        this.description = description;
        this.imageURL = imageURL;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeID = placeID;
        this.statusID = statusID;
    }

    // Getters & Setters
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
    
}
