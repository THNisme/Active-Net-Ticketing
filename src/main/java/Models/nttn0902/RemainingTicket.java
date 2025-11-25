/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nttn0902;

/**
 *
 * @author NGUYEN
 */
public class RemainingTicket {
    private int orderId;

    private int ticketId;
    private String serialNumber;
    private String typeName;
    private String zoneName;
    private String seat;   // ✅ đã gộp RowLabel + SeatNumber như DAO
    private double price;

    public RemainingTicket() {
    }

    public RemainingTicket(int ticketId, String serialNumber, String typeName,
            String zoneName, String seat, double price) {
        
        this.ticketId = ticketId;
        this.serialNumber = serialNumber;
        this.typeName = typeName;
        this.zoneName = zoneName;
        this.seat = seat;
        this.price = price;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RemainingTicket{"
                + "ticketId=" + ticketId
                + ", serialNumber='" + serialNumber + '\''
                + ", typeName='" + typeName + '\''
                + ", zoneName='" + zoneName + '\''
                + ", seat='" + seat + '\''
                + ", price=" + price
                + '}';
    }
}
