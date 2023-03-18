/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.att;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.att.TimeSlot;

/**
 *
 * @author sonnt
 */
public class TimeSlotDBContext  {

   

    
    public ArrayList<TimeSlot> all() {
        ArrayList<TimeSlot> slots = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [tid]\n"
                    + "      ,[description]\n"
                    + "  FROM [TimeSlot]";
            rs = stm.executeQuery();
            while (rs.next()) {
                TimeSlot d = new TimeSlot();
                slots.add(d);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(TimeSlotDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return slots;
    }

}
