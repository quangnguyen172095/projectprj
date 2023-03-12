/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Account;

import Utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class AccountDAO implements Serializable{
    public AccountDTO getAccount(String username, String password) throws SQLException, ClassNotFoundException{
        String SQL = "SELECT * FROM Acc WHERE Username = ? AND Password = ?";
        AccountDTO account = null;
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet res = null;
        try {
            con = DBUtils.makeConnection();
            pre = con.prepareStatement(SQL);
            pre.setString(1, username);
            pre.setString(2, password);
            res = pre.executeQuery();
            while (res.next()) {
            boolean role = res.getBoolean("Role");
            String campus = res.getString("Campus");
            account = new AccountDTO(username, password, role, campus);
            }
        } finally {
            if (con != null) {
                con.close();
            }

            if (pre != null) {
                pre.close();
            }

            if (res != null) {
                res.close();
            }

        }
        return account;
    }
}
