/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Utils;
   
import java.util.UUID;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class TicketUtils {
    public static String generateSerialNumber() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
    
    public static void main(String[] args) {
        String serial = TicketUtils.generateSerialNumber();
        System.out.println("SerialNumber: " + serial);
    }
    
}
