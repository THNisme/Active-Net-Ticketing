/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs.nttn0902;

import Models.nttn0902.Event;
import Models.nttn0902.RemainingTicket;
import Models.nttn0902.SoldTicket;
import java.util.List;

/**
 *
 * @author NGUYEN
 */
public interface TicketManagementDAO {

    List<SoldTicket> getSoldTickets(int eventId, String keyword);

    List<RemainingTicket> getRemainingTickets(int eventId, String keyword);
    
    String getEventNameById(int eventId);

    List<Event> getOtherEvents(int currentEventId);
    
    boolean updateSoldTicketStatus(int ticketId, int status);
    
    boolean updateRemainingTicketStatus(int ticketId, int status);
    
    RemainingTicket getRemainingTicketById(int ticketId);
    
    boolean updateRemainingTicketPrice(int ticketId, double price);

    List<RemainingTicket> searchRemainingTickets(int eventId, String keyword);
    
    List<SoldTicket> searchSoldTickets(int eventId, String keyword);

}
