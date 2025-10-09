/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.thn1105;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class Zone {

    private int zoneID;
    private int placeID;
    private String zoneName;

    public Zone() {
    }

    public Zone(int zoneID, int placeID, String zoneName) {
        this.zoneID = zoneID;
        this.placeID = placeID;
        this.zoneName = zoneName;
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

    
}
