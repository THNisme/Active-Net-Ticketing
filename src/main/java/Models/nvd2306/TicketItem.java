package Models.nvd2306;

public class TicketItem {

    private String name;
    private int qty;
    private double price;
    private double total;

    public TicketItem(String name, int qty, double price, double total) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return total;
    }
}
