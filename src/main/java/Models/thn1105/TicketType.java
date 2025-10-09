/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.thn1105;

import java.math.BigDecimal;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class TicketType {

    private int ticketTypeID;
    private int eventID;
    private int zoneID;
    private String typeName;
    private BigDecimal price;

    public TicketType() {
    }

    public TicketType(int ticketTypeID, int eventID, int zoneID, String typeName, BigDecimal price) {
        this.ticketTypeID = ticketTypeID;
        this.eventID = eventID;
        this.zoneID = zoneID;
        this.typeName = typeName;
        this.price = price;
    }

    public int getTicketTypeID() {
        return ticketTypeID;
    }

    public void setTicketTypeID(int ticketTypeID) {
        this.ticketTypeID = ticketTypeID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    
}
