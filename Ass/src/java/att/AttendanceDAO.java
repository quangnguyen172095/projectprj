/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package att;

import Utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.att.Attendance;
import model.att.Course;
import model.att.Group;
import model.att.Lecturer;
import model.att.Room;
import model.att.Session;
import model.att.Student;
import model.att.Timeslot;

/**
 *
 * @author sonnt
 */
public class AttendanceDAO implements Serializable{

    public ArrayList<Attendance> getAttendancesBySession(int sessionid) {
        String sql = "SELECT s.sid,s.sname,\n"
                + "a.aid,\n"
                + "ISNULL(a.status,0) as [status],\n"
                + "ISNULL(a.description,'') as [description]\n"
                + "FROM Student s LEFT JOIN [Student_Group] sg ON s.sid = sg.sid\n"
                + "						LEFT JOIN [Group] g ON g.gid = sg.gid\n"
                + "						LEFT JOIN [Session] ses ON ses.gid = g.gid\n"
                + "						LEFT JOIN [Attendance] a ON ses.sessionid = a.sessionid AND s.sid = a.sid\n"
                + "						WHERE ses.sessionid = ?";
        ArrayList<Attendance> atts = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            
            stm = con.prepareStatement(sql);
            stm.setInt(1, sessionid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                a.setId(rs.getInt("aid"));
                a.setStatus(rs.getBoolean("status"));
                a.setDescription(rs.getString("description"));
                Student s = new Student();
                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("sname"));
                a.setStudent(s);
                atts.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(attDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stm.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(attDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return atts;

    }

    public void update(ArrayList<Attendance> atts, int sessionid) {
        try {
            Connection con = null;
            con.setAutoCommit(false);
            String sql_update_session = "UPDATE [Session]\n"
                    + "   SET [status] = 1\n"
                    + " WHERE [gid] = ?";
            PreparedStatement stm_update_session = con.prepareStatement(sql_update_session);
            stm_update_session.setInt(1, sessionid);
            stm_update_session.executeUpdate();
            for (Attendance a : atts) {
                if (a.getId() == 0) {
                    //INSERT
                    String sql_insert_att = "INSERT INTO [Attendance]\n"
                            + "           ([sid]\n"
                            + "           ,[sessionid]\n"
                            + "           ,[status]\n"
                            + "           ,[description])\n"
                            + "     VALUES\n"
                            + "           (?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?)";
                    PreparedStatement stm_insert_att = con.prepareStatement(sql_insert_att);
                    stm_insert_att.setInt(1, a.getStudent().getId());
                    stm_insert_att.setInt(2, a.getSession().getId());
                    stm_insert_att.setBoolean(3, a.isStatus());
                    stm_insert_att.setString(4, a.getDescription());
                    stm_insert_att.executeUpdate();
                } else {
                    //UPDATE
                    String sql_update_att = "UPDATE [Attendance]\n"
                            + "   SET \n"
                            + "	   [status] = ?\n"
                            + "      ,[description] = ?\n"
                            + " WHERE [aid] = ?";
                    PreparedStatement stm_update_att = con.prepareStatement(sql_update_att);
                    stm_update_att.setBoolean(1, a.isStatus());
                    stm_update_att.setString(2, a.getDescription());
                    stm_update_att.setInt(3, a.getId());
                    stm_update_att.executeUpdate();
                }
            }

            con.commit();
        } catch (SQLException ex) {
            
    }


}
    }
