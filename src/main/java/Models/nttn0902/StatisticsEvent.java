/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nttn0902;

/**
 *
 * @author NGUYEN
 */
public class StatisticsEvent {

    private int eventID;
    private String eventName;
    private String ticketTypeName;
    
    private int totalTickets;
    private int soldTickets;
    private double totalRevenue;

    public StatisticsEvent() {
    }

    public StatisticsEvent(String eventName, String ticketTypeName, int totalTickets, int soldTickets, double totalRevenue) {
        this.eventName = eventName;
        this.ticketTypeName = ticketTypeName;
        this.totalTickets = totalTickets;
        this.soldTickets = soldTickets;
        this.totalRevenue = totalRevenue;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
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

    @Override
    public String toString() {
        return "StatisticsOffAllEvent{"
                + "eventID=" + eventID
                + "eventName='" + eventName + '\''
                + ", ticketTypeName='" + ticketTypeName + '\''
                + ", totalTickets=" + totalTickets
                + ", soldTickets=" + soldTickets
                + ", totalRevenue=" + totalRevenue
                + '}';
    }
}
