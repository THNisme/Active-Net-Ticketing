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
public class OrderDetail {

    private int orderId;

    // Buyer info
    private String contactFullname;
    private String contactEmail;
    private String contactPhone;
    private Date createdDate;

    // Event info
    private String eventName;
    private String categoryName;
    private Date startDate;
    private Date endDate;

    // Place info
    private String placeName;
    private String placeAddress;

    // Ticket info
    private String serialNumber;
    private String ticketTypeName;
    private String zoneName;
    private String rowLabel;
    private int seatNumber;

    public OrderDetail() {
    }

    // FULL CONSTRUCTOR (tiện nếu bạn muốn khởi tạo nhanh)
    public OrderDetail(int orderId, String contactFullname, String contactEmail, String contactPhone,
            Date createdDate, String eventName, String categoryName, Date startDate,
            Date endDate, String placeName, String placeAddress, String serialNumber,
            String ticketTypeName, String zoneName, String rowLabel, int seatNumber) {

        this.orderId = orderId;
        this.contactFullname = contactFullname;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.createdDate = createdDate;

        this.eventName = eventName;
        this.categoryName = categoryName;
        this.startDate = startDate;
        this.endDate = endDate;

        this.placeName = placeName;
        this.placeAddress = placeAddress;

        this.serialNumber = serialNumber;
        this.ticketTypeName = ticketTypeName;
        this.zoneName = zoneName;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
    }

    // --- GETTERS & SETTERS ---
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getContactFullname() {
        return contactFullname;
    }

    public void setContactFullname(String contactFullname) {
        this.contactFullname = contactFullname;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
