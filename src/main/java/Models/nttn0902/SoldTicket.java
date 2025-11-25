/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nttn0902;

import java.util.Date;

/**
 *
 * @author NGUYEN
 */
public class SoldTicket {

    private int orderId;
    private int ticketId;
    private String serialNumber;
    private String typeName;
    private Date orderDate;
    private double unitPrice;
    private int statusId;

    public SoldTicket() {
    }

    public SoldTicket(int orderId, int ticketId, String serialNumber,
            String typeName, Date orderDate,
            double unitPrice, int statusId) {
        this.orderId = orderId;
        this.ticketId = ticketId;
        this.serialNumber = serialNumber;
        this.typeName = typeName;
        this.orderDate = orderDate;
        this.unitPrice = unitPrice;
        this.statusId = statusId;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "SoldTicket{"
                + "orderId=" + orderId
                + ", ticketId=" + ticketId
                + ", serialNumber='" + serialNumber + '\''
                + ", typeName='" + typeName + '\''
                + ", orderDate=" + orderDate
                + ", unitPrice=" + unitPrice
                + ", statusId=" + statusId
                + '}';
    }
}
