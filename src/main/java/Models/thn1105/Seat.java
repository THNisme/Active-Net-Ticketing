/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.thn1105;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class Seat {

    private int seatID;
    private int zoneID;
    private String rowLabel;
    private int seatNumber;
    private int statusID;

    public Seat() {
    }
    
    public Seat(int seatID, int zoneID, String rowLabel, int seatNumber, int statusID) {
        this.seatID = seatID;
        this.zoneID = zoneID;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.statusID = statusID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
}
