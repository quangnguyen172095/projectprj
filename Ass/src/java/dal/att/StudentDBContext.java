/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.att;


import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.att.Course;
import model.att.Group;
import model.att.Lecturer;
import model.att.Room;
import model.att.Session;
import model.att.Student;
import model.att.TimeSlot;

/**
 *
 * @author sonnt
 */
public class StudentDBContext implements Serializable {

    public Student getTimeTable(int sid, Date from, Date to) {
        Student student = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT s.sid,s.sname,ses.sessionid,ses.date,ses.status\n"
                    + "	,g.gid,g.gname,c.cid,c.cname,r.rid,r.rname,l.lid,l.lname,t.tid,t.description		\n"
                    + "FROM Student s INNER JOIN [Student_Group]  sg ON s.sid = sg.sid\n"
                    + "						INNER JOIN [Group] g ON g.gid = sg.gid\n"
                    + "						INNER JOIN [Course] c ON g.cid = c.cid\n"
                    + "						INNER JOIN [Session] ses ON g.gid = ses.gid\n"
                    + "						INNER JOIN [TimeSlot] t ON t.tid = ses.tid\n"
                    + "						INNER JOIN [Room] r ON r.rid = ses.rid\n"
                    + "						INNER JOIN [Lecturer] l ON l.lid = ses.lid\n"
                    + "WHERE s.sid = ? AND ses.date >= ? AND ses.date <= ? ORDER BY s.sid,g.gid";
            stm.setInt(1, sid);
            stm.setDate(2, from);
            stm.setDate(3, to);
            rs = stm.executeQuery();
            Group currentGroup = new Group();
            currentGroup.setId(-1);
            while (rs.next()) {
                if(student == null)
                {
                    student = new Student();
                    student.setId(rs.getInt("sid"));
                    student.setName(rs.getString("sname"));
                }
                int gid = rs.getInt("gid");
                if(gid != currentGroup.getId())
                {
                    currentGroup = new Group();
                    currentGroup.setId(rs.getInt("gid"));
                    currentGroup.setName(rs.getString("gname"));
                    Course c = new Course();
                    c.setId(rs.getInt("cid"));
                    c.setName(rs.getString("cname"));
                    currentGroup.setCourse(c);
                    student.getGroups().add(currentGroup);
                }
                Session ses = new Session();
                ses.setId(rs.getInt("sessionid"));
                ses.setDate(rs.getDate("date"));
                ses.setStatus(rs.getBoolean("status"));
                ses.setGroup(currentGroup);
                
                Lecturer l = new Lecturer();
                l.setId(rs.getInt("lid"));
                l.setName(rs.getString("lname"));
                ses.setLecturer(l);
                
                
                Room r = new Room();
                r.setId(rs.getInt("rid"));
                r.setName(rs.getString("rname"));
                ses.setRoom(r);
                
               
                
                currentGroup.getSessions().add(ses);

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
        return student;
    }


}
