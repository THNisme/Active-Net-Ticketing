/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.thn1105;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class Place {

    private int placeID;
    private String placeName;
    private String address;
    private String seatMapURL;
    private String description;
    private int statusID;

    public Place() {
    }

    public Place(int placeID, String placeName, String address, String seatMapURL, String description, int statusID) {
        this.placeID = placeID;
        this.placeName = placeName;
        this.address = address;
        this.seatMapURL = seatMapURL;
        this.description = description;
        this.statusID = statusID;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
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

    public String getSeatMapURL() {
        return seatMapURL;
    }

    public void setSeatMapURL(String seatMapURL) {
        this.seatMapURL = seatMapURL;
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
