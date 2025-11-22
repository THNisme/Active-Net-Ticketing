/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nvd2306;

/**
 *
 * @author NguyenDuc
 */
public class TicketFullInfo {
      public String eventName;
    public String placeName;
    public String time;
    public String typeName;
    public String serial;

    public TicketFullInfo(String eventName, String placeName, String time, String typeName, String serial) {
        this.eventName = eventName;
        this.placeName = placeName;
        this.time = time;
        this.typeName = typeName;
        this.serial = serial;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    
}
