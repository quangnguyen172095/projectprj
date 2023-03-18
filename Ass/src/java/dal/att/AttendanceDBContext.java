/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.att;


import java.io.Serializable;
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

/**
 *
 * @author sonnt
 */
public class AttendanceDBContext implements Serializable {

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
        try {

            
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
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return atts;

    }
    

}
