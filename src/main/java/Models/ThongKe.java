/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author NGUYEN
 */
public class ThongKe {
    private String typeName;
    private double price;
    private int soldTickets;
    private int totalTickets;
    private double totalRevenue;

    public ThongKe() {}

    public ThongKe(String typeName, double price, int soldTickets, int totalTickets, double totalRevenue) {
        this.typeName = typeName;
        this.price = price;
        this.soldTickets = soldTickets;
        this.totalTickets = totalTickets;
        this.totalRevenue = totalRevenue;
    }

    public ThongKe(String typeName, int soldTickets, int totalTickets, double totalRevenue) {
        this.typeName = typeName;
        this.soldTickets = soldTickets;
        this.totalTickets = totalTickets;
        this.totalRevenue = totalRevenue;
    }

 
    

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
