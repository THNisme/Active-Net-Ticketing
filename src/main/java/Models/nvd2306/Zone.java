/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nvd2306;

/**
 *
 * @author NguyenDuc
 */
public class Zone {

    private int zoneID;
    private int placeID;
    private String zoneName;
    private int statusID;   // map với StatusID trong DB (nếu bạn muốn dùng)

    public Zone() {
    }

    public Zone(int zoneID, int placeID, String zoneName, int statusID) {
        this.zoneID = zoneID;
        this.placeID = placeID;
        this.zoneName = zoneName;
        this.statusID = statusID;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
}