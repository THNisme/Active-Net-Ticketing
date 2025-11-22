package Models.nvd2306;

import java.math.BigDecimal;

public class TicketItem {

    private int ticketTypeId;
    private int ticketId;     // = TicketTypeID
    private String name;      // = TicketTypeName
    private int qty;          // = Quantity
    private double price;     // giữ lịch sử double
    private double total;
    private int orderStatus;

    public TicketItem() {
    }

    public TicketItem(int ticketId, String name, int qty, double price, double total) {
        this.ticketId = ticketId;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.total = total;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public double getPriceDouble() {
        return price;
    }

    public double getTotal() {
        return total;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setPriceDouble(double price) {
        this.price = price;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // bridge cho code mới
    public int getTicketTypeId() {
        return ticketId;
    }

    public String getTicketTypeName() {
        return name;
    }

    public int getQuantity() {
        return qty;
    }

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(price);
    }
}
