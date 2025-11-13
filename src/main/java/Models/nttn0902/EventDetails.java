/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nttn0902;

import java.sql.Date;

/**
 *
 * @author NGUYEN
 */
public class EventDetails {
    private int eventID;
    private String eventName;
    private String description;
    private String imageURL;
    private Date startDate;
    private String placeName;
    private String address;
    private double lowestPrice;

    public EventDetails() {
    }

    public EventDetails(int eventID, String eventName, String description, String imageURL, Date startDate, String placeName, String address, double lowestPrice) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.description = description;
        this.imageURL = imageURL;
        this.startDate = startDate;
        this.placeName = placeName;
        this.address = address;
        this.lowestPrice = lowestPrice;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }
}