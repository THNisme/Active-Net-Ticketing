package Models.nttn0902;

public class StatisticsEvents {

    private int eventID;
    private String eventName;
    private int totalTickets;
    private int soldTickets;
    private double totalRevenue;

   private double revenuePercent;
 private double soldPercent;
  private double eventSellPercent;

    public StatisticsEvents() {
    }

    public StatisticsEvents(String eventName, int totalTickets, int soldTickets, double totalRevenue) {
        this.eventName = eventName;
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

    public int getEventId() {
        return eventID;
    }

    public void setEventId(int eventId) {
        this.eventID = eventId;
    }

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

//    public double getRevenuePercent() {
//        return revenuePercent;
//    }
//
//    public void setRevenuePercent(double revenuePercent) {
//        this.revenuePercent = revenuePercent;
//    }
//
//    public double getSoldPercent() {
//        return soldPercent;
//    }
//
//    public void setSoldPercent(double soldPercent) {
//        this.soldPercent = soldPercent;
//    }
//
//    public double getEventSellPercent() {
//        return eventSellPercent;
//    }
//
//    public void setEventSellPercent(double eventSellPercent) {
//        this.eventSellPercent = eventSellPercent;
//    }

    @Override
    public String toString() {
        return "ThongKe{"
                + "eventID=" + eventID
                + ", eventName='" + eventName + '\''
                + ", totalTickets=" + totalTickets
                + ", soldTickets=" + soldTickets
                + ", totalRevenue=" + totalRevenue
//                + ", revenuePercent=" + revenuePercent
//                + ", soldPercent=" + soldPercent
//                + ", eventSellPercent=" + eventSellPercent
                + '}';
    }
}
