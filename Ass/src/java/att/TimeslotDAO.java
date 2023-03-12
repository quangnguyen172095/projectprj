/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package att;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.att.Timeslot;

/**
 *
 * @author ADMIN
 */
public class TimeslotDAO implements Serializable {
    public ArrayList<Timeslot> all() {
        ArrayList<Timeslot> slots = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            String sql = "SELECT [tid]\n"
                    + "      ,[description]\n"
                    + "  FROM [TimeSlot]";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Timeslot d = new Timeslot();
                d.setId(rs.getInt("tid"));
                d.setName(rs.getString("description"));
                slots.add(d);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TimeslotDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stm.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TimeslotDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return slots;
    }
}
