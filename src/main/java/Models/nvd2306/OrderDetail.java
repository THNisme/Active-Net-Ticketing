/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nvd2306;

/**
 *
 * @author NguyenDuc
 */
public class OrderDetail {

    private int orderDetailID;
    private int orderID;
    private int ticketID;
    private double unitPrice;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(int orderDetailID, int orderID, int ticketID, double unitPrice, int quantity) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.ticketID = ticketID;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
