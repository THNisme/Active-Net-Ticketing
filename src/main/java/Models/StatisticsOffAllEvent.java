/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 
 * @author NGUYEN
 */
public class StatisticsOffAllEvent {
    private String eventName;       // Tên sự kiện
    private int totalTickets;       // Tổng số vé phát hành
    private int soldTickets;        // Tổng số vé đã bán
    private double totalRevenue;    // Tổng doanh thu (số tiền bán được)

    // Các chỉ số tính toán thêm
    private double revenuePercent;  // % doanh thu (so với tổng doanh thu tất cả sự kiện)
    private double soldPercent;     // % vé bán (so với tổng vé tất cả sự kiện)
    private double eventSellPercent; // % vé bán trong từng sự kiện

    public StatisticsOffAllEvent() {
    }

    public StatisticsOffAllEvent(String eventName, int totalTickets, int soldTickets, double totalRevenue) {
        this.eventName = eventName;
        this.totalTickets = totalTickets;
        this.soldTickets = soldTickets;
        this.totalRevenue = totalRevenue;
    }

    // Getter & Setter
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getRevenuePercent() {
        return revenuePercent;
    }

    public void setRevenuePercent(double revenuePercent) {
        this.revenuePercent = revenuePercent;
    }

    public double getSoldPercent() {
        return soldPercent;
    }

    public void setSoldPercent(double soldPercent) {
        this.soldPercent = soldPercent;
    }

    public double getEventSellPercent() {
        return eventSellPercent;
    }

    public void setEventSellPercent(double eventSellPercent) {
        this.eventSellPercent = eventSellPercent;
    }

    @Override
    public String toString() {
        return "ThongKe{" +
                "eventName='" + eventName + '\'' +
                ", totalTickets=" + totalTickets +
                ", soldTickets=" + soldTickets +
                ", totalRevenue=" + totalRevenue +
                ", revenuePercent=" + revenuePercent +
                ", soldPercent=" + soldPercent +
                ", eventSellPercent=" + eventSellPercent +
                '}';
    }
}
