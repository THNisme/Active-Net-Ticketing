/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nvd2306;

import java.sql.Timestamp;

/**
 *
 * @author NguyenDuc
 */
public class Ticket {

    private int ticketID;
    private int ticketTypeID;
    private Integer seatID;       // Có thể null
    private String serialNumber;
    private int statusID;
    private Timestamp issuedAt;

    // Constructor mặc định
    public Ticket() {
    }

    // Constructor đầy đủ
    public Ticket(int ticketID, int ticketTypeID, Integer seatID, String serialNumber, int statusID, Timestamp issuedAt) {
        this.ticketID = ticketID;
        this.ticketTypeID = ticketTypeID;
        this.seatID = seatID;
        this.serialNumber = serialNumber;
        this.statusID = statusID;
        this.issuedAt = issuedAt;
    }

    // Getter & Setter
    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public void setTicketTypeID(int ticketTypeID) {
        this.ticketTypeID = ticketTypeID;
    }

    public Integer getSeatID() {
        return seatID;
    }

    public void setSeatID(Integer seatID) {
        this.seatID = seatID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public Timestamp getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Timestamp issuedAt) {
        this.issuedAt = issuedAt;
    }
}
